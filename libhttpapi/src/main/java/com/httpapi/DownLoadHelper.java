package com.httpapi;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.security.MessageDigest;

import okhttp3.Call;
import okhttp3.Response;

// 文件下载辅助类
public class DownLoadHelper {
    // savePath 存储路径,url 下载地址
    // 断点下载的时候用，fileSize是之前已经下载过的那个文件在服务器上的总的大小，这个需要客户端保存，也可以在下载前重新请求文件总大小，但是麻烦不可取，最好第一次下的
    // 时候就保存文件总大小，如果不需要断点下载，此参数可以去掉，再修改下内部逻辑即可
    public static void downLoadFile(String url, final String savePath, final long fileSize, final int what, final OnDownModelListener listener) {
        final File file = new File(savePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        final long startsPoint = file.length(); // 已经下载过的文件大小，断点续传用

        QHttpApi.downLoadFile(url, startsPoint, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream is = null;//输入流
                try {
                    // 随机访问文件，可以指定断点续传的起始位置
                    RandomAccessFile fos = new RandomAccessFile(file, "rwd");
                    fos.seek(startsPoint); // 设置断点下载的位置

                    is = response.body().byteStream();//获取输入流
                    long total = response.body().contentLength();//获取文件大小
                    if (startsPoint != 0) // 断点下载的情况，剩余的不能代表全文件大小，进度会出错
                        total = fileSize;

                    if (listener != null)
                        listener.onDownModelStart(what, total);

                    if (listener != null)
                        listener.onProcess(what, 0, ((float) startsPoint / (float) total) * 100);

                    long current = startsPoint;
                    if (is != null) {
                        byte[] buf = new byte[2048];
                        int ch;
                        while ((ch = is.read(buf)) != -1) {
                            current += ch;
                            fos.write(buf, 0, ch);

                            if (listener != null) {
                                listener.onProcess(what, current, ((float) current / (float) total) * 100);
                            }
                        }
                    }

                    // 下载完成
                    if (fos != null) {
                        fos.close();
                    }

                    listener.onDownModelSuccess(what, savePath);
                } catch (Exception e) {
                    Log.d("downModel", e.toString());
                    if (listener != null)
                        listener.onDownModelFailed(what, "download failed", 0);
                } finally {
                    if (listener != null)
                        listener.onDownModelFinished(what);

                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    // 获取单个文件MD5值
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest;
        FileInputStream in;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        String result = bigInt.toString(16);
        while (result.length() < 32)
            result = "0" + result;
        return result;
    }

    public interface OnDownModelListener {
        void onDownModelStart(int what, long fileSize); // 开始下载

        void onProcess(int what, long current, float percent); // 下载百分比

        void onDownModelSuccess(int what, String savePath); // 下载成功

        void onDownModelFailed(int what, String obj, int type); // 下载失败

        void onDownModelFinished(int what); // 下载完成，不管失败与否
    }
}
