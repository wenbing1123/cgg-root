package com.cgg.framework.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class InfoLoggerFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (!event.getLevel().isGreaterOrEqual(Level.ERROR)) {
            if ("reactor.netty.http.server.AccessLog".equals(event.getLoggerName())) {
                return FilterReply.DENY;
            }
            return FilterReply.ACCEPT;
        }
        return FilterReply.DENY;
    }

}
