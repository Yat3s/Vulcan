package com.jd.aibdp.vulcan.camera;

import android.graphics.Rect;
import android.graphics.YuvImage;
import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Yat3s on 18/08/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class YuvUtil {
    public static byte[] convertYUVToJPEG(@NonNull byte[] data, int width, int height, int format) {
        YuvImage yuv = new YuvImage(data, format, width, height, null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuv.compressToJpeg(new Rect(0, 0, width, height), 50, out);

        return out.toByteArray();
    }

    public static byte[] rotateNV21(@NonNull final byte[] yuv,
                                    final int width,
                                    final int height,
                                    final int rotation,
                                    final boolean flipHorizontal) throws IOException {
        if (rotation == 0) return yuv;
        if (rotation % 90 != 0 || rotation < 0 || rotation > 270) {
            throw new IllegalArgumentException("0 <= rotation < 360, rotation % 90 == 0");
        } else if ((width * height * 3) / 2 != yuv.length) {
            throw new IOException("provided width and height don't jive with the data length");
        }

        final byte[] output = new byte[yuv.length];
        final int frameSize = width * height;
        final boolean swap = rotation % 180 != 0;
        final boolean xflip = flipHorizontal ? rotation % 270 == 0 : rotation % 270 != 0;
        final boolean yflip = rotation >= 180;

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                final int yIn = j * width + i;
                final int uIn = frameSize + (j >> 1) * width + (i & ~1);
                final int vIn = uIn + 1;

                final int wOut = swap ? height : width;
                final int hOut = swap ? width : height;
                final int iSwapped = swap ? j : i;
                final int jSwapped = swap ? i : j;
                final int iOut = xflip ? wOut - iSwapped - 1 : iSwapped;
                final int jOut = yflip ? hOut - jSwapped - 1 : jSwapped;

                final int yOut = jOut * wOut + iOut;
                final int uOut = frameSize + (jOut >> 1) * wOut + (iOut & ~1);
                final int vOut = uOut + 1;

                output[yOut] = (byte) (0xff & yuv[yIn]);
                output[uOut] = (byte) (0xff & yuv[uIn]);
                output[vOut] = (byte) (0xff & yuv[vIn]);
            }
        }
        return output;
    }
}
