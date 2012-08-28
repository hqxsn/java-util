package com.javautil.stringhandler;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Arrays;

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

    private char[] charArray;
    private int pos;
    private int capacity;

    public StringBuilderV1() {
        capacity = 64;
        charArray = new char[capacity];
        pos = 0;
    }

    public StringBuilderV1(int capacity) {
        this.capacity = capacity;
        charArray = new char[capacity];
        pos = 0;
    }

    void expandCapacity(int minimumCapacity) {
        int newCapacity = (charArray.length + 1) * 3;
        if (newCapacity < 0 || newCapacity == Integer.MAX_VALUE) {
            newCapacity = Integer.MAX_VALUE;
        } else if (minimumCapacity > newCapacity) {
            newCapacity = minimumCapacity;
        }
        char[] newCharArray = new char[newCapacity];
        unsafe.copyMemory(charArray, charArrayOffset, newCharArray, charArrayOffset, charArray.length << 1);
        charArray = newCharArray;
        capacity = newCapacity;
    }

    public StringBuilderV1 append(int i) {

        if (i == Integer.MIN_VALUE) {
            append("-2147483648");
            return this;
        }
        int appendedLength = (i < 0) ? IntToString.stringSize(-i) + 1
                                     : IntToString.stringSize(i);
        int spaceNeeded = pos + appendedLength;
        if (spaceNeeded > capacity)
            expandCapacity(spaceNeeded);
        IntToString.getChars(i, spaceNeeded, charArray);
        pos = spaceNeeded;
        return this;
    }

    public StringBuilderV1 append(long l) {
        if (l == Integer.MIN_VALUE) {
            append("-9223372036854775808");
            return this;
        }
        int appendedLength = (l < 0) ? LongToString.stringSize(-l) + 1
                                     : LongToString.stringSize(l);
        int spaceNeeded = pos + appendedLength;
        if (spaceNeeded > capacity)
            expandCapacity(spaceNeeded);
        LongToString.getChars(l, spaceNeeded, charArray);
        pos = spaceNeeded;
        return this;

    }

    public StringBuilderV1 append(float f) {


        return this;
    }

    public StringBuilderV1 append(char[] str) {
        this.append(str, 0, str.length);
        return this;
    }

    public StringBuilderV1 append(char[] str, int offset, int len) {
        long bytesToCopy = len << 1;

        if ((pos + len) > capacity) {
            //long beginTime = System.nanoTime();
            expandCapacity(pos + len);
            //System.out.println("expand capacity: " + (System.nanoTime() - beginTime));
        }

        int snapPos = pos;
        //System.arraycopy(str, offset, charArray, pos, len);
        unsafe.copyMemory(str, charArrayOffset, charArray, charArrayOffset + (snapPos << 1) , bytesToCopy);
        pos = (int) ((snapPos << 1) + bytesToCopy) >> 1;
        return this;
    }

    public StringBuilderV1 append(String strVal) {

        /*long beginTime = System.nanoTime();
  char[] values = (char[])stringValField.get(strVal);
  System.out.println("get fields: " + (System.nanoTime() - beginTime));*/

        long bytesToCopy = strVal.length() << 1;

        if ((pos + strVal.length()) > capacity) {
            //long beginTime = System.nanoTime();
            expandCapacity(pos + strVal.length());
            //System.out.println("expand capacity: " + (System.nanoTime() - beginTime));
        }
        //beginTime = System.nanoTime();
        //unsafe.copyMemory(values, charArrayOffset, charArray, charArrayOffset + pos, bytesToCopy);

        //System.out.println("copy content: " + (System.nanoTime() - beginTime));

        strVal.getChars(0, strVal.length(), charArray, pos);
        pos = (int) (pos + strVal.length());
        return this;
    }

    public String toString() {
        return new String(charArray, 0, pos);
    }


    public static void main(String[] args) throws InstantiationException {
        char[] src = {'h', 'e', 'l', 'l', 'o', ',', 't', 'h', 'i', 's'};

        StringBuilderV1 stringBuilderV1 = new StringBuilderV1();

        stringBuilderV1.append("hello,this is a test");
        stringBuilderV1.append(src);

        System.out.println(stringBuilderV1.toString());

    }

}
