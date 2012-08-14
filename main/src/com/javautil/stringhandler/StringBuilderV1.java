package com.javautil.stringhandler;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created with TwinsFactory.
 * User: andysong
 * Date: 8/14/12
 * Time: 11:19 AM
 * New version of java string builder. It will faster than java StringBuilder in several factor;
 * @author      hqxsn
 * @version     0.1 beta
 */
public class StringBuilderV1 {

    private static final Unsafe unsafe;
    private static final Field stringValField;

    static
    {
        try
        {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);

            stringValField = String.class.getDeclaredField("value");
            stringValField.setAccessible(true);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static final long charArrayOffset = unsafe.arrayBaseOffset(char[].class);

    private char[] charArray;
    private int pos;
    private int capacity;

    public StringBuilderV1() {
        capacity = 16;
        charArray = new char[capacity];
        pos = 0;
    }

    void expandCapacity(int minimumCapacity) {
        int newCapacity = (charArray.length + 1) * 2;
        if (newCapacity < 0) {
            newCapacity = Integer.MAX_VALUE;
        } else if (minimumCapacity > newCapacity) {
            newCapacity = minimumCapacity;
        }
        char[] newCharArray = new char[newCapacity];
        unsafe.copyMemory(charArray, charArrayOffset, newCharArray, charArrayOffset, charArray.length << 1);
        charArray = newCharArray;
        capacity = newCapacity;
    }

    public StringBuilderV1 append(String strVal) {
        try {

            char[] values = (char[])stringValField.get(strVal);

            long bytesToCopy = values.length << 1;

            int tmpCapacity = capacity;
            if ((pos + values.length) > capacity) {
                expandCapacity(pos + values.length);
            }


            unsafe.copyMemory(values, charArrayOffset, charArray, charArrayOffset + pos, bytesToCopy);

            pos = (int) (pos + bytesToCopy);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return this;
    }

    public String toString() {
        return new String(charArray, 0, pos >> 1);
    }

}
