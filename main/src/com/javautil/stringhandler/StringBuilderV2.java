package com.javautil.stringhandler;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created with TwinsFactory.
 * User: andysong
 * Date: 12-8-21
 * Time: 下午9:28
 * To change this template use File | Settings | File Templates.
 */
public class StringBuilderV2 {

    private static final Unsafe unsafe;

    static
    {
        try
        {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static final long charArrayOffset = unsafe.arrayBaseOffset(char[].class);

    private char[]  buffer;
    private int pos;
    private int capacity = 64;
    private StringBuilderV1 stringBuilderV1;

    public StringBuilderV2() {
        buffer = new char[capacity];
        pos = 0;
        stringBuilderV1 = new StringBuilderV1(capacity);
    }

    public StringBuilderV2(int capacity) {
        this.capacity = capacity;
        buffer = new char[capacity];
        pos = 0;
        stringBuilderV1 = new StringBuilderV1(capacity);
    }

    public StringBuilderV2 append(String strVal) {


        if ((pos + strVal.length()) > capacity) {
            if (pos > 0)  {
                flushBuffer();
                pos = 0;
            } else if (pos == 0) {
                stringBuilderV1.append(strVal);
                return this;
            }
            //buffer = new char[capacity + strVal.length()];
            //capacity = capacity + strVal.length();
        }


        strVal.getChars(0, strVal.length(), buffer, pos);
        pos += strVal.length();
        return this;
    }

    public StringBuilderV2 append(char[] buff, int offset, int len) {
        long bytesToCopy = len << 1;

        if ((pos + len) > capacity) {
            if (pos > 0) {
                flushBuffer();
                pos = 0;
            } else if (pos == 0) {
                stringBuilderV1.append(buff, offset, len);
                return this;
            }
        }

        int snapPos = pos;
        //System.arraycopy(str, offset, charArray, pos, len);
        unsafe.copyMemory(buff, charArrayOffset, buffer, charArrayOffset + (snapPos << 1) , bytesToCopy);
        pos = (int) ((snapPos << 1) + bytesToCopy) >> 1;

        return this;
    }

    public void flushBuffer() {
        if (pos > 0)
            stringBuilderV1.append(buffer, 0, pos);
    }

    public String toString() {
        return stringBuilderV1.toString();
    }

    public static void main(String[] args) {
        StringBuilderV2 stringBuilderV2 = new StringBuilderV2();
        stringBuilderV2.append("hello, this is a test.").append("hello, this is a test").flushBuffer();
        System.out.println(stringBuilderV2.toString());
    }
}
