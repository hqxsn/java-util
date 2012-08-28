package com.javautil.stringhandler;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created with TwinsFactory.
 * User: andysong
 * Date: 8/16/12
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static final Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final long charArrayOffset = unsafe.arrayBaseOffset(char[].class);

    private static final char digit_pairs[] =
            ("00010203040506070809" +
                    "10111213141516171819" +
                    "20212223242526272829" +
                    "30313233343536373839" +
                    "40414243444546474849" +
                    "50515253545556575859" +
                    "60616263646566676869" +
                    "70717273747576777879" +
                    "80818283848586878889" +
                    "90919293949596979899").toCharArray();

    private static final char intMaxArrays[] = {'2', '1', '4', '7', '4', '8', '3', '6', '4', '7'};
    private static final char intMinArrays[] = {'-', '2', '1', '4', '7', '4', '8', '3', '6', '4', '8'};

    private static final int BUFFER_SIZE = 11;

    final static int [] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999,
                                      99999999, 999999999, Integer.MAX_VALUE };

    // Requires positive x
    static int stringSize(int x) {
        for (int i=0; ; i++)
            if (x <= sizeTable[i])
                return i+1;
    }

    public static void getChars(int val, int index, int endPos, char[] dst) {

        if (val == Integer.MAX_VALUE) {
            unsafe.copyMemory(intMaxArrays, charArrayOffset, dst, charArrayOffset + (index << 1), intMaxArrays.length << 1);
        } else if (val == Integer.MIN_VALUE) {
            unsafe.copyMemory(intMinArrays, charArrayOffset, dst, charArrayOffset + (index << 1), intMinArrays.length << 1);
        } else if (val == 0) {
            dst[index++] = '0';
        }




        int pos = endPos - 2;

        if (val >= 0) {
            int div = val / 100;
            while (div > 0) {
                int inx = pos;
                dst[inx++] = digit_pairs[2 * (val - div * 100)];
                dst[inx++] = digit_pairs[2 * (val - div * 100) + 1];
                val = div;
                pos -= 2;
                div = val / 100;
            }
            int inx = pos;
            if (pos == -1) inx =0;
            dst[inx++] = digit_pairs[2 * val];
            if (val < 10)
                dst[inx++] = digit_pairs[2 * val + 1];

        } else {
            /*int div = val / 100;
            while (div) {
                memcpy(it, & digit_pairs[-2 * (val - div * 100)], 2);
                val = div;
                it -= 2;
                div = val / 100;
            }
            memcpy(it, & digit_pairs[-2 * val], 2);
            if (val <= -10)
                it--;
            *it = '-';*/
        }

        //unsafe.copyMemory(buf, charArrayOffset + (pos << 1), dst, charArrayOffset + (index << 1), (BUFFER_SIZE - pos) << 1);
    }


    public static void main(String[] args) {
        char[] dst = new char[512];

        char[] src = {'h','e', 'l', 'l', 'o', ',', 't','h','i','s'};
        int pos = 0;
        unsafe.copyMemory(src, charArrayOffset, dst, charArrayOffset + pos, src.length << 1);
        pos = pos + (src.length << 1);
        unsafe.copyMemory(src, charArrayOffset, dst, charArrayOffset + pos, src.length << 1);
        pos = pos + (src.length << 1);

        System.out.println(new String(dst, 0, pos >> 1));

        /*long executeTime = System.nanoTime();
        for (int i = 10000; i < 100000; ++i) {
            int end = Test.stringSize(i);
            Test.getChars(i, 0 , 0 + end, dst);
            //System.out.println(new String(dst, 0, end));
        }
        System.out.println("New:" + (System.nanoTime() - executeTime));

        System.gc();
        System.gc();

        long executeTime1 = System.nanoTime();
        for (int i = 10000; i < 100000; ++i) {
            int end = IntToString.stringSize(i);
            IntToString.getChars(i, 0 + end, dst);
            //System.out.println(new String(dst));
        }
        System.out.println("Old:" + (System.nanoTime() - executeTime1));*/

    }


}
