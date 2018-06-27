package com.clark.xlibrary;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Raw、assets 文件工具类
 * Created by clark on 2017/6/12 11:50.
 */
public class RawAndAssetsUtil {

    /**
     * 读取asset文件夹下fileName文件内容，返回字符串
     * 一行一行读
     * @param context
     * @param fileName
     * @return
     */
    public static String getStringFromAssets(Context context, String fileName){
        InputStream inputStream = null;
        if(context == null || TextUtils.isEmpty(fileName)){
            throw new IllegalArgumentException("context or fileName can not be null");
        }
        try {
            inputStream = context.getResources().getAssets().open(fileName);
        }catch (IOException e){
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
            return stringBuffer.toString();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(inputStream != null){
                    inputStream.close();
                }
                if(inputStreamReader != null){
                    inputStreamReader.close();
                }
                if(bufferedReader != null){
                    bufferedReader.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * 读取raw文件夹下的文件，返回字符串
     * 一行一行读
     * @param context
     * @param resId
     * @return
     */
    public static String getStringFromRaw(Context context, int resId){
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        if(context == null){
            throw new IllegalArgumentException("context can not be null");
        }
        try {
            inputStreamReader = new InputStreamReader(context.getResources().openRawResource(resId));
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
            return stringBuffer.toString();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                inputStreamReader.close();
                bufferedReader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 读取asset文件夹下fileName文件内容，返回list
     * 一行一行读
     * @param context 上下文
     * @param fileName  文件的名字
     * @return     返回资源下的文件
     */
    public static List<String> geFileToListFromAssets(Context context, String fileName) {
        if (context == null || TextUtils.isEmpty(fileName)) {
            throw new IllegalArgumentException("context or fileName can not be null");
        }

        List<String> fileContent = new ArrayList<String>();
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
            br.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取raw文件夹下的文件，返回list
     * 一行一行读
     * @param context  上下文
     * @param resId id
     * @return   raw下的文件
     */
    public static List<String> geFileToListFromRaw(Context context, int resId) {
        if (context == null) {
            throw new IllegalArgumentException("context can not be null");
        }

        List<String> fileContent = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
            reader = new BufferedReader(in);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
