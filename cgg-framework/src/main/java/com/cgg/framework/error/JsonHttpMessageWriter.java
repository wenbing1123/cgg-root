package com.cgg.framework.error;

import com.cgg.framework.utils.JacksonUtils;
import lombok.NonNull;
import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class JsonHttpMessageWriter  implements HttpMessageWriter<Map<String, Object>> {

    @NonNull
    @Override
    public List<MediaType> getWritableMediaTypes() {
        return Collections.singletonList(MediaType.APPLICATION_JSON);
    }


    @Override
    public boolean canWrite(ResolvableType elementType, MediaType mediaType) {
        return MediaType.APPLICATION_JSON.includes(mediaType);
    }

    @NonNull
    @Override
    public Mono<Void> write(Publisher<? extends Map<String, Object>> inputStream, ResolvableType elementType, MediaType mediaType, ReactiveHttpOutputMessage message, Map<String, Object> hints) {
        return Mono.from(inputStream).flatMap(m -> message.writeWith(Mono.just(message.bufferFactory().wrap(JacksonUtils.writeValueAsString(m).getBytes(StandardCharsets.UTF_8)))));
    }
}
