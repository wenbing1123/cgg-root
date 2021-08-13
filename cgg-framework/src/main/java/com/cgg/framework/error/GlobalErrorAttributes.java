package com.cgg.framework.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes  extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable error = getError(request);
        if (error instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) error;
            Map<String, Object> errorAttributes = new LinkedHashMap<>();
            errorAttributes.put("ret_code", responseStatusException.getStatus().value());
            errorAttributes.put("ret_info", responseStatusException.getMessage());
            return errorAttributes;
        } else {
            Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);
            errorAttributes.put("ret_code", errorAttributes.getOrDefault("status", 404));
            errorAttributes.put("ret_info", error.getMessage());
            return errorAttributes;
        }
    }

}
