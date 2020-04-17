package org.zero.utils.support;

import lombok.Getter;

/**
 * 字节处理工具
 *
 * @author : cgl
 * @version : 1.0
 * @since : 2020/4/17 12:04
 **/
public class ByteUtil {

    /**
     * 十六进制字符
     */
    private final static String hexDigits = "0123456789ABCDEF";

    /**
     * 十六进制字符串转字节数组
     *
     * @param hexString: 十六进制格式字符串
     * @return byte[]:
     * @author : cgl
     * @version : 1.0
     * @since 2020/4/15 14:52
     **/
    public static byte[] hexStringToByteArray(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.replace(" ", "").toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            bytes[i] = (byte) (hexDigits.indexOf(hexChars[pos]) << 4 | hexDigits.indexOf(hexChars[pos + 1]));
        }
        return bytes;
    }

    /**
     * 整数转字节数组（默认为大端模式）
     *
     * @param num: 数字（暂时只支持整数Short，Integer，Long）
     * @return byte[]:
     * @author : cgl
     * @version : 1.0
     * @since 2020/4/17 14:15
     **/
    public static byte[] num2ByteArray(Number num) {
        return num2ByteArray(num, Mode.BIG_ENDIAN);
    }

    /**
     * 整数转字节数组
     *
     * @param num:  数字（暂时只支持整数Short，Integer，Long）
     * @param mode: 模式（小端：0  大端：1）
     * @return byte[]:
     * @author : cgl
     * @version : 1.0
     * @since 2020/4/17 14:15
     **/
    public static byte[] num2ByteArray(Number num, Mode mode) {
        int byteArrLength;
        long value;

        if (num instanceof Short) {
            byteArrLength = 2;
            value = num.shortValue();
        } else if (num instanceof Integer) {
            byteArrLength = 4;
            value = num.intValue();
        } else if (num instanceof Long) {
            byteArrLength = 8;
            value = num.longValue();
        } else {
            return new byte[1];
        }

        byte[] bytes = new byte[byteArrLength];

        if (mode == Mode.LITTLE_ENDIAN) {
            for (int i = 0; i < byteArrLength; i++) {
                bytes[i] = (byte) (value >>> (i * 8) & 0xFF);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                bytes[byteArrLength - i - 1] = (byte) (value >>> (i * 8) & 0xFF);
            }
        }
        return bytes;
    }

    /**
     * int型转十六进制字符串
     *
     * @param num:
     * @return java.lang.String:
     * @author : cgl
     * @version : 1.0
     * @since 2020/4/15 14:46
     **/
    public static String num2HexString(Number num) {
        return num2HexString(num, Mode.BIG_ENDIAN);
    }

    /**
     * int型转十六进制字符串
     *
     * @param num:  数字（暂时只支持整数Short，Integer，Long）
     * @param mode: 模式（小端：0  大端：1）
     * @return java.lang.String:
     * @author : cgl
     * @version : 1.0
     * @since 2020/4/15 14:46
     **/
    public static String num2HexString(Number num, Mode mode) {
        StringBuilder result = new StringBuilder();
        byte[] bytes = num2ByteArray(num, mode);
        for (byte b : bytes) {
            result.append(hexDigits.charAt(b >>> 4 & 0xF)).append(hexDigits.charAt(b & 0xF));
        }
        return result.toString();
    }

    enum Mode {
        /**
         * 大端模式
         */
        BIG_ENDIAN(1),

        /**
         * 小端模式
         */
        LITTLE_ENDIAN(0);

        @Getter
        int value;

        Mode(int value) {
            this.value = value;
        }
    }

}
