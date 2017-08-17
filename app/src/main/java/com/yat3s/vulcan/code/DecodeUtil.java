package com.yat3s.vulcan.code;

/**
 * Created by Yat3s on 17/08/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class DecodeUtil {
    public static int convertByteArrayToInt(byte[] bytes, int offset, boolean reverse) {
        int result = 0;
        if (reverse) {
            for (int i = offset + 3; i >= offset; i--) {
                result <<= 8;
                result |= (int) bytes[i] & 0xFF;
            }
        } else {
            for (int i = offset; i < offset + 4; i++) {
                result <<= 8;
                result |= (int) bytes[i] & 0xFF;
            }
        }
        return result;
    }
}
