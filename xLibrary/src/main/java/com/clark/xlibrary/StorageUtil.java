package com.clark.xlibrary;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 内部存储、内置sd卡存储、外置sd卡存贮 工具类
 *
 * Created by clark on 2017/6/12 10:50.
 */
public class StorageUtil {

    /****************************************
     * 内部存贮
     * data/data/包名/
     ***************************************/

    /**
     * 返回应用内部存贮 根目录
     * data/data/包名/files
     * @param context
     * @return
     */
    public static File getInternalStorageRoot(Context context){
        if(context != null){
            return context.getFilesDir();
        }
        return null;
    }

    /**
     * 如果应用内部存贮 data/data/包名/files/ 目录下已经存在该文件，则返回该文件的路径
     * 如果不存在，则创建该文件，并返回路径
     * @param context
     * @param fileName
     * @return data/data/包名/files/xxx
     */
    public static File createOrgetFile(Context context, String fileName){
        if(context == null || TextUtils.isEmpty(fileName)){
            return null;
        }
        return context.getDir(fileName, Context.MODE_PRIVATE);
    }

    /**
     * 删除 应用内部存贮 文件
     * @param context
     * @param fileName 文件名
     */
    public static void deleteInternalFile(Context context, String fileName){
        if(context !=null){
            context.deleteFile(fileName);
        }
    }

    /**
     * 应用内部存储 已创建的文件
     * @param context
     * @return
     */
    public static String[] getAllInternalFile(Context context){
        if(context != null){
            return context.fileList();
        }
        return null;
    }




    /****************************************
     * 外置sd卡存贮
     ***************************************/

    /**
     * 获得外置sd卡路径
     * @param mContext
     * @param is_removale
     *          true : 返回外置sd卡路径
     *          false : 返回内置sd卡路径
     * @return
     */
    public static String getInternalOrExternalSdPath(Context mContext, boolean is_removale) {

        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }




    /****************************************
     * 内置sd卡 存贮
     *
     * 分为两类：
     * ① 公有目录：storage/emulated/0/xxxxx 应用卸载后，文件夹不删除
     * ② 私有目录：storage/sdcard/Android/data/包名/files 应用卸载后删除
     *
     ***************************************/

    /**
     * 获得内置sd卡状态
     * @return
     */
    public static String getInternalSdState(){
        return Environment.getExternalStorageState();
    }

    /**
     * 内置SD卡是否可用
     * @return
     */
    public static boolean isInternalAvailable(){
        return Environment.MEDIA_MOUNTED.equals(getInternalSdState());
    }

    /**
     * 获得内置sd卡根目录
     * @return null:不存在sd卡
     */
    public static File getInternalSdRootDirectory(){
        return isInternalAvailable() ? Environment.getExternalStorageDirectory() : null;
    }

    /**
     * 获得内置sd卡根路径
     * @return
     */
    public static String getInternalSdRootPath(){
        if(getInternalSdRootDirectory() != null){
            return getInternalSdRootDirectory().getPath();
        }
        return null;
    }

    /**
     * 获得 内置sd卡 大小 单位(MB)
     * @return
     */
    public static long getInternalSdSize(){
        if (isInternalAvailable()) {
            StatFs fs = new StatFs(getInternalSdRootPath());
            long count = fs.getBlockCountLong();
            long size = fs.getBlockSizeLong();
            return count * size / 1024 / 1024;
        }
        return 0;
    }

    /**
     * 获得 内置sd卡 剩余空间大小 单位(MB)
     * @return
     */
    public static long getInternalSdFreeSize(){
        if (isInternalAvailable()) {
            StatFs fs = new StatFs(getInternalSdRootPath());
            long count = fs.getFreeBlocksLong();
            long size = fs.getBlockSizeLong();
            return count * size / 1024 / 1024;
        }
        return 0;
    }

    /**
     * 获得 内置sd卡 可用空间大小 单位(MB)
     * @return
     */
    public static long getInternalSdAvilableSize(){
        if (isInternalAvailable()) {
            StatFs fs = new StatFs(getInternalSdRootPath());
            long count = fs.getAvailableBlocksLong();
            long size = fs.getBlockSizeLong();
            return count * size / 1024 / 1024;
        }
        return 0;
    }

    /**
     * 往内置SD卡的公有目录下保存文件
     * @param data
     * @param type 文件类型参见 Environment
     * @param fileName
     * @return
     */
    public static boolean saveFileToSDCardPublicDir(byte[] data, String type, String fileName) {
        BufferedOutputStream bos = null;
        if (isInternalAvailable()) {
            File file = Environment.getExternalStoragePublicDirectory(type);
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                bos.write(data);
                bos.flush();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if(bos != null){
                        bos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 往内置SD卡的自定义目录下保存文件
     * @param data
     * @param dir 目录名 如果目录名为 null,则直接在根目录下创建文件
     * @param fileName 定义的文件名
     * @return
     */
    public static boolean saveFileToSDCardCustomDir(byte[] data, String dir, String fileName) {
        BufferedOutputStream bos = null;
        if (isInternalAvailable()) {
            File file = null;
            if(dir != null){
                file = new File(getInternalSdRootPath() + File.separator + dir);
            }else{
                file = getInternalSdRootDirectory();
            }
            if (!file.exists()) {
                boolean create = file.mkdirs();// 递归创建自定义目录
                if(!create){
                    return false;
                }
            }

            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                bos.write(data);
                bos.flush();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if(bos != null){
                        bos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 往内置SD卡的私有Files目录下保存文件
     * @param data
     * @param type 文件类型参见 Environment
     * @param fileName
     * @param context
     * @return
     */
    public static boolean saveFileToSDCardPrivateFilesDir(byte[] data, String type, String fileName, Context context) {
        BufferedOutputStream bos = null;
        if (isInternalAvailable()) {
            File file = context.getExternalFilesDir(type);
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                bos.write(data);
                bos.flush();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if(bos != null){
                        bos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 往内置SD卡的私有Cache目录下保存文件
     * @param data
     * @param fileName
     * @param context
     * @return
     */
    public static boolean saveFileToSDCardPrivateCacheDir(byte[] data, String fileName, Context context) {
        BufferedOutputStream bos = null;
        if (isInternalAvailable()) {
            File file = context.getExternalCacheDir();
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                bos.write(data);
                bos.flush();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if(bos != null){
                        bos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 保存bitmap图片到内置SDCard的私有Cache目录
     * @param bitmap
     * @param fileName
     * @param context
     * @return
     */
    public static boolean saveBitmapToSDCardPrivateCacheDir(Bitmap bitmap, String fileName, Context context) {
        if (isInternalAvailable()) {
            BufferedOutputStream bos = null;
            // 获取私有的Cache缓存目录
            File file = context.getExternalCacheDir();
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                if (fileName != null && (fileName.contains(".png") || fileName.contains(".PNG"))) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                } else {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                }
                bos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        if(bos != null){
                            bos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 从内置SD中寻找指定目录下的文件，返回Bitmap
     * @param filePath 文件路径
     * @return
     */
    public Bitmap loadBitmapFromSDCard(String filePath) {
        byte[] data = loadFileFromSDCard(filePath);
        if (data != null) {
            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
            if (bm != null) {
                return bm;
            }
        }
        return null;
    }

    /**
     * 从内置SD卡获取文件
     * @param fileDir 文件路径
     * @return 返回字节数组
     */
    public static byte[] loadFileFromSDCard(String fileDir) {
        BufferedInputStream bis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            bis = new BufferedInputStream(new FileInputStream(new File(fileDir)));
            byte[] buffer = new byte[8 * 1024];
            int c = 0;
            while ((c = bis.read(buffer)) != -1) {
                baos.write(buffer, 0, c);
                baos.flush();
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(baos != null){
                    baos.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(bis != null){
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取内置SD卡公有目录的路径
     * @param type
     * @return
     */
    public static String getSDCardPublicDir(String type) {
        return Environment.getExternalStoragePublicDirectory(type).toString();
    }

    /**
     * 获取内置SD卡私有Cache目录的路径
     * @param context
     * @return
     */
    public static String getSDCardPrivateCacheDir(Context context) {
        return context.getExternalCacheDir().getAbsolutePath();
    }

    /**
     * 获取内置SD卡私有Files目录的路径
     * @param context
     * @param type
     * @return
     */
    public static String getSDCardPrivateFilesDir(Context context, String type) {
        return context.getExternalFilesDir(type).getAbsolutePath();
    }

    /**
     * 判断内置SD卡文件是否存在
     * @param filePath 文件路径
     * @return
     */
    public static boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return file.isFile();
    }

    /**
     * 从内置sdcard中删除文件
     * @param filePath 文件路径
     * @return
     */
    public static boolean removeFileFromSDCard(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                file.delete();
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

}
