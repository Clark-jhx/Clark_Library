package com.clark.xlibrary;

import android.text.TextUtils;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 文件工具类
 *
 * Created by clark on 2017/6/22 16:25.
 */
public class FilesUtil {


    /**
     * 文件是否存在
     * @param file
     * @return
     */
    public static boolean isExist(File file){
        if(file == null){
            return false;
        }

        return file.exists();
    }

    /**
     * 文件是否存在
     * @param path 文件路径
     * @return
     */
    public static boolean isExist(String path){
        if(path == null || "".equals(path)){
            return false;
        }
        File file = new File(path);
        return file.exists();
    }

    /**
     * 写文件
     * @param filePath 文件路径
     * @param content 要写的内容
     * @param append 是否追加写入。true：追加到文件内容后面；false：清除文件内容，然后再写入
     * @return 要写的内容是否为空
     * @throws RuntimeException 如果发生错误，抛出运行时异常
     */
    public static boolean writeFile(String filePath, String content, boolean append) throws RuntimeException{
        if(TextUtils.isEmpty(content)){
            return false;
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException happened : ", e);
        } finally {
            closeCloseable(fileWriter);
        }
    }

    /**
     * 写文件
     * @param filePath 文件路径
     * @param contentList 要写的内容
     * @param append 是否追加写入。true：追加到文件内容后面；false：清除文件内容，然后再写入
     * @return 要写的内容是否为空
     * @throws RuntimeException 如果发生错误，抛出运行时异常
     */
    public static boolean writeFile(String filePath, List<String> contentList, boolean append) throws RuntimeException{
        if(contentList == null || contentList.size() == 0){
            return false;
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath, append);
            for (String content : contentList){
                fileWriter.write("\r\n");
                fileWriter.write(content);
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException happened : ", e);
        } finally {
            closeCloseable(fileWriter);
        }
    }



    /**
     * 关闭流
     * @param closeable
     */
    private static void closeCloseable(Closeable closeable){
        if(closeable !=null){
            try {
                closeable.close();
            } catch (IOException e) {
                throw new RuntimeException("IOException happened : ", e);
            }
        }
    }
}
