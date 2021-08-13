package com.cgg.service.user.utils;

import com.cgg.framework.exception.SysException;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Slf4j
public class NameUtils {

    public static String randomNickName(int len) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < len; i++) {
            String str = null;
            int highPos, lowPos; // 定义高低位
            Random random = new Random();
            highPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
            byte[] b = new byte[2];
            b[0] = (Integer.valueOf(highPos).byteValue());
            b[1] = (Integer.valueOf(lowPos).byteValue());
            try {
                str = new String(b, "GBK"); // 转成中文
            } catch (UnsupportedEncodingException ex) {
                String msg = "编码不支持";
                log.error(msg, ex);
                throw new SysException(msg);
            }
            ret.append(str);
        }
        return ret.toString();
    }

    public static String randomUsername(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }

    public static void main(String[] args) {
        System.out.println(randomUsername(8));
        System.out.println(randomNickName(5));
    }
}
