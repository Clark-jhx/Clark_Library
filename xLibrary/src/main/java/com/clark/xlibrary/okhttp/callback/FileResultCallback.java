package com.clark.xlibrary.okhttp.callback;

import com.clark.xlibrary.okhttp.OkUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * 将response保存成文件
 * Created by clark on 2017/8/3 16:50.
 */
public abstract class FileResultCallback extends ResultCallback<File> {
    /**
     * 要保存结果的 目录路径
     */
    private String directory;
    /**
     * 要保存结果的 文件名称
     */
    private String fileName;

    public FileResultCallback(String directory, String fileName){
        this.directory = directory;
        this.fileName = fileName;
    }

    /**
     * UI线程中
     * 回调 下载进度
     * @param already_read
     * @param total
     */
    public abstract void onProgress(long already_read, long total);


    @Override
    public File parseResponse(Response response) throws Exception {
        return saveResponse2File(response);
    }

    /**
     * 将结果保存到文件
     * @param response
     * @return 返回文件名路径
     */
    private File saveResponse2File(Response response) throws Exception {
        File file = new File(directory, fileName);
        if(!file.exists()){
            file.mkdirs();
        }
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        long already_read = 0;
        long temp = 0;
        try {
            final long total = response.body().contentLength();
            fileOutputStream = new FileOutputStream(file);
            inputStream = response.body().byteStream();

            byte[] buff = new byte[1024];
            while ((temp = inputStream.read(buff)) != -1){
                already_read += temp;
                fileOutputStream.write(buff);

                final long final_Already_read = already_read;
                OkUtils.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        // UI线程中 回调 下载进度
                        onProgress(final_Already_read, total);
                    }
                });
            }
            fileOutputStream.flush();
            return file;
        } catch (Exception e) {
            throw new Exception("save response to file  fail");
        } finally {
            try{
                response.body().close();
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e){
                throw new IOException("close inputStream fail");
            }
            try{
                if(fileOutputStream != null){
                    fileOutputStream.close();
                }
            } catch (IOException e){
                throw new IOException("close inputStream fail");
            }
        }
    }
}
