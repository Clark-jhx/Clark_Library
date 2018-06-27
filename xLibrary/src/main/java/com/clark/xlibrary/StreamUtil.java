package com.clark.xlibrary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 流工具类
 * Created by clark on 2017/6/12 11:08.
 */
public class StreamUtil {

    /**
     * 流 转换成 字符串
     * @param inputStream 输入流
     * @return
     */
    public static String steam2String(InputStream inputStream){
        if(inputStream == null){
            throw new IllegalArgumentException("inputStream can not be null");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(buff)) != -1){
                byteArrayOutputStream.write(buff, 0, len);
                byteArrayOutputStream.flush();
            }
            return byteArrayOutputStream.toString();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(inputStream !=null){
                    inputStream.close();
                }
                if(byteArrayOutputStream != null){
                    byteArrayOutputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return "";
    }
}
