package com.example.administrator.weixinhookdemo;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/** Created by Administrator on 2018/4/19. */
public class ImageUtils {
    /** 保存图片至本地 */
    public static String saveImageToGallery(Bitmap bmp) {
        // 首先保存图片
        String storePath =
                Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator
                        + "vuctrl";
        Log.d("path", storePath);
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        boolean isSuccess = false;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            // 通过io流的方式来压缩保存图片
            isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Log.d("saveState", isSuccess + "");
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess ? file.getAbsolutePath() : "";
    }
}
