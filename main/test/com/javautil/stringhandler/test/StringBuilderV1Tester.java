package com.javautil.stringhandler.test;

import com.javautil.stringhandler.StringBuilderV1;

/**
 * Created with TwinsFactory.
 * User: andysong
 * Date: 8/14/12
 * Time: 11:19 AM
 * New version of java string builder test. It will generate the performance counter
 * @author      hqxsn
 * @version     0.1 beta
 */
public class StringBuilderV1Tester {

    public void testStringBuilderV1() {
        long executeTime1 = System.nanoTime();
        StringBuilderV1 stringConstructor = new StringBuilderV1();
        for (int i = 0; i < 10000; ++i)
            stringConstructor.append("Hello, this is test.").append("UnsafeVersion come to play, to see how fast is!");
        stringConstructor.toString();
        executeTime1 = System.nanoTime() - executeTime1;

        System.out.println("StringConstructor:" + executeTime1);
    }

    public void testStringBuilder() {
        long executeTime = System.nanoTime();
        StringBuilder stringBuilder = new StringBuilder();
        for( int i=0; i< 10000; ++i)
            stringBuilder.append("Hello, this is test.").append("JDK StringBuilder come to play, to see how fast is!").toString();
        stringBuilder.toString();
        executeTime = System.nanoTime() - executeTime;
        System.out.println("StringBuilder:" + executeTime);
    }

    public static void main(String[] args) {
        //Please run these class testing separately and avoid the GC involve impact the performance counter.
        new StringBuilderV1Tester().testStringBuilder();
        new StringBuilderV1Tester().testStringBuilderV1();
    }

}
