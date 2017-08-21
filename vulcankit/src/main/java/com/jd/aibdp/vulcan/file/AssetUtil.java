package com.jd.aibdp.vulcan.file;

import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Yat3s on 21/08/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class AssetUtil {

    /**
     * Copy files to target SD path from assets.
     *
     * @param assetManager     asset manager
     * @param assetPath        asset local path
     * @param targetSDCardPath target sd card path
     */
    public static void copyFilesFromAssets(AssetManager assetManager, String assetPath, String targetSDCardPath)
            throws IOException {
        String fileNames[] = assetManager.list(assetPath);
        if (fileNames.length > 0) {
            File file = new File(targetSDCardPath);
            if (!file.mkdirs()) {
                throw new IOException("Cannot make dir in SD card!");
            }
            for (String fileName : fileNames) {
                copyFilesFromAssets(assetManager, assetPath + "/" + fileName, targetSDCardPath + "/" + fileName);
            }
        } else {
            InputStream is = assetManager.open(assetPath);
            FileOutputStream fos = new FileOutputStream(new File(targetSDCardPath));
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            is.close();
            fos.close();
        }
    }
}
