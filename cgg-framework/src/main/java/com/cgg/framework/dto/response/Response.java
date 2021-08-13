package com.cgg.framework.dto.response;

import com.cgg.framework.enums.PredefinedCode;
import com.cgg.framework.dto.Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "请求结果")
public class Response<T extends ResponseData> extends Dto {

    private String code;

    private String msg;

    private T data;

    public static <T extends ResponseData> Response<T> success () {
        return success(null);
    }

    public static <T extends ResponseData> Response<T> success (T data) {
        Response<T> response = new Response<>();
        response.setCode(PredefinedCode.SUCCESS.getCode());
        response.setMsg(PredefinedCode.SUCCESS.getMsg());
        response.setData(data);
        return response;
    }
    
    public static Response<None> error(PredefinedCode predefinedCode) {
        Response<None> response = new Response<>();
        response.setCode(predefinedCode.getCode());
        response.setMsg(predefinedCode.getMsg());
        return response;
    }

    public static Response<None> error(PredefinedCode predefinedCode, String msg) {
        Response<None> response = new Response<>();
        response.setCode(predefinedCode.getCode());
        response.setMsg(msg);
        return response;
    }

    public static Response<None> error(String code, String msg) {
        Response<None> response = new Response<>();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public static <T extends ResponseData> Response<T> error(PredefinedCode predefinedCode, T data) {
        Response<T> response = new Response<>();
        response.setCode(predefinedCode.getCode());
        response.setMsg(predefinedCode.getMsg());
        response.setData(data);
        return response;
    }

    public static <T extends ResponseData> Response<T> error(PredefinedCode predefinedCode, String msg,  T data) {
        Response<T> response = new Response<>();
        response.setCode(predefinedCode.getCode());
        response.setMsg(msg);
        response.setData(data);
        return response;
    }

    public static <T extends ResponseData> Response<T> error(String code, String msg, T data) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }

}
