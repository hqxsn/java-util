package com.javautil.stringhandler;

/**
 * Created with JuluMobile.
 * User: andysong
 * Date: 12-8-28
 * Time: 上午12:47
 * To change this template use File | Settings | File Templates.
 */
public class StringBuilderV3 {

    private char[]  buffer;
    private int pos;
    private int capacity = 64;
    private StringBuilderV2 stringBuilderV2;

    public StringBuilderV3() {
        buffer = new char[capacity];
        pos = 0;
        stringBuilderV2 = new StringBuilderV2(capacity * 2);
    }

    public StringBuilderV3(int capacity) {
        this.capacity = capacity;
        buffer = new char[capacity];
        pos = 0;
        stringBuilderV2 = new StringBuilderV2(capacity * 2);
    }

    public StringBuilderV3 append(String strVal) {


        if ((pos + strVal.length()) > capacity) {
            if (pos > 0)  {
                flushBuffer();
                pos = 0;
            } else if (pos == 0) {
                stringBuilderV2.append(strVal);
                return this;
            }
            //buffer = new char[capacity + strVal.length()];
            //capacity = capacity + strVal.length();
        }


        strVal.getChars(0, strVal.length(), buffer, pos);
        pos += strVal.length();
        return this;
    }

    public void flushBuffer() {
        if (pos > 0)
            stringBuilderV2.append(buffer, 0, pos);
        stringBuilderV2.flushBuffer();
    }

    public String toString() {
        return stringBuilderV2.toString();
    }

    public static void main(String[] args) {
        StringBuilderV3 stringBuilderV3 = new StringBuilderV3();
        stringBuilderV3.append("hello, this is a test").append("let's see how fast it is").
                append("we want to see the miracle").append("the future is bright");
        stringBuilderV3.flushBuffer();
        System.out.println(stringBuilderV3.toString());
    }
}
