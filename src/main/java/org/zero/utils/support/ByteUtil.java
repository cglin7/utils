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
            for (int i = byteArrLength - 1; i >= 0; i--) {
                bytes[i] = (byte) (value >>> (i * 8) & 0xFF);
            }
        }
        return bytes;
    }

    /**
     * 数字型转十六进制字符串
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
     * 数字型转十六进制字符串
     *
     * @param num:  数字（暂时只支持整数Short，Integer，Long）
     * @param mode: 模式（小端：0  大端：1）
     * @return java.lang.String:
     * @author : cgl
     * @version : 1.0
     * @since 2020/4/15 14:46
     **/
    public static String num2HexString(Number num, Mode mode) {
        byte[] bytes = num2ByteArray(num, mode);
        return byteArray2HexString(bytes);
    }

    /**
     * 字节数组转十六进制格式字符串
     *
     * @param bytes: 数字（暂时只支持整数Short，Integer，Long）
     * @return byte[]:
     * @author : cgl
     * @version : 1.0
     * @since 2020/4/17 14:15
     **/
    public static String byteArray2HexString(byte... bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(hexDigits.charAt(b >>> 4 & 0xF)).append(hexDigits.charAt(b & 0xF));
        }
        return result.toString();
    }

    /**
     * 十六进制字符串转字节数组
     *
     * @param hexString: 十六进制格式字符串
     * @return byte[]:
     * @author : cgl
     * @version : 1.0
     * @since 2020/4/15 14:52
     **/
    public static byte[] hexString2ByteArray(String hexString) {
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
     * 十六进制字符串转数字（暂时只支持到long型）
     *
     * @param hexString:
     * @return long:
     * @author : cgl
     * @version : 1.0
     * @since 2020/5/8 9:41
     **/
    public static long hexString2Long(String hexString) {
        char[] chars = hexString.toUpperCase().toCharArray();
        long sum = 0;
        // 超过8个字节就不转换了
        if (chars.length > 16) {
            return 0;
        }
        for (int i = 0; i < chars.length; i++) {
            int index = hexDigits.indexOf(String.valueOf(chars[i]));
            if (index == -1) {
                return 0;
            }
            sum += index << ((chars.length - i - 1) * 4);
        }
        return sum;
    }

    public enum Mode {
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
