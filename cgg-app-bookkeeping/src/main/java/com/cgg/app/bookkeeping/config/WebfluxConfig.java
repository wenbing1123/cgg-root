package com.cgg.app.bookkeeping.config;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryCustomizer;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyRouteProvider;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.boot.web.reactive.server.ConfigurableReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.ReactorNetty;
import reactor.netty.resources.LoopResources;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
public class WebfluxConfig extends ReactiveWebServerFactoryCustomizer implements WebFluxConfigurer {

    private static final int DEFAULT_IO_WORKER_COUNT = Integer.parseInt(System.getProperty(
            ReactorNetty.IO_WORKER_COUNT,
            "" + Math.max(Runtime.getRuntime()
                    .availableProcessors(), 4)));

    public static final Scheduler HTTP_WORKER = Schedulers.fromExecutorService(new ThreadPoolExecutor(
            30,
            200,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            new CustomizableThreadFactory("http-worker-"),
            new ThreadPoolExecutor.AbortPolicy()), "httpWorker");

    public WebfluxConfig(ServerProperties serverProperties) {
        super(serverProperties);
    }

    @Bean
    NettyReactiveWebServerFactory nettyReactiveWebServerFactory(ReactorResourceFactory resourceFactory,
                                                                ObjectProvider<NettyRouteProvider> routes, ObjectProvider<NettyServerCustomizer> serverCustomizers) {
        NettyReactiveWebServerFactory serverFactory = new NettyReactiveWebServerFactory();
        routes.orderedStream().forEach(serverFactory::addRouteProviders);
        serverFactory.getServerCustomizers().addAll(serverCustomizers.orderedStream().collect(Collectors.toList()));
        return serverFactory;
    }

    @Override
    public void customize(ConfigurableReactiveWebServerFactory factory) {
        super.customize(factory);
        NettyReactiveWebServerFactory nettyFactory = (NettyReactiveWebServerFactory) factory;
        nettyFactory.addServerCustomizers(httpServer -> httpServer
                .runOn(LoopResources.create("http", 1, DEFAULT_IO_WORKER_COUNT, true))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // 5s连接超时
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT) // 默认内存分配
                .option(ChannelOption.SO_REUSEADDR, true) // 地址可重用
                .option(ChannelOption.SO_BACKLOG, 1000) // 连接队列
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT) // 默认内存分配
                .childOption(ChannelOption.SO_RCVBUF, 1024 * 1024) // 接收缓冲区大小
                .childOption(ChannelOption.SO_SNDBUF, 1024 * 1024) // 发送缓冲区大小
                .childOption(ChannelOption.TCP_NODELAY, true));
    }
}
