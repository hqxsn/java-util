package com.javautil.stringhandler.test;

import com.javautil.stringhandler.StringBuilderV1;
import com.javautil.stringhandler.StringBuilderV2;
import com.javautil.stringhandler.StringBuilderV3;

import java.util.concurrent.TimeUnit;

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

    private final static String helloPrefix = "Hello, this is test.";
    private final static String helloSuffix = "JDK StringBuilder come to play, to see how fast is!";

    private final static char[] helloPrefixCharArray = helloPrefix.toCharArray();
    private final static char[] helloSuffixCharArray = helloSuffix.toCharArray();

    public void testStringBuilderV3() {
        long executeTime1 = System.nanoTime();
        StringBuilderV3 stringConstructor = new StringBuilderV3();
        //stringConstructor.append(helloPrefix)/*.append(i)*//*.append(i + 100000000l)*/.append(helloSuffix).append(helloSuffix);
        //stringConstructor.flushBuffer();
        for (int i = 0; i < 100000; ++i)
            stringConstructor.append(helloPrefix)/*.append(i)*//*.append(i + 100000000l)*/.append(helloSuffix).append(helloSuffix);
        stringConstructor.flushBuffer();
        stringConstructor.toString();
        executeTime1 = System.nanoTime() - executeTime1;

        System.out.println("StringBuilderV3:" + executeTime1 + " nSec, " + TimeUnit.NANOSECONDS.toMillis(executeTime1) + " mSec");
    }

     public void testStringBuilderV2() {
        long executeTime1 = System.nanoTime();
        StringBuilderV2 stringConstructor = new StringBuilderV2(256);
        //stringConstructor.append(helloPrefix)/*.append(i)*//*.append(i + 100000000l)*/.append(helloSuffix).append(helloSuffix);
        //stringConstructor.flushBuffer();
        for (int i = 0; i < 100000; ++i)
            stringConstructor.append(helloPrefix)/*.append(i)*//*.append(i + 100000000l)*/.append(helloSuffix).append(helloSuffix);
        stringConstructor.flushBuffer();
        stringConstructor.toString();
        executeTime1 = System.nanoTime() - executeTime1;

        System.out.println("StringBuilderV2:" + executeTime1 + " nSec, " + TimeUnit.NANOSECONDS.toMillis(executeTime1) + " mSec");
    }

    public void testStringBuilderV1() {
        long executeTime1 = System.nanoTime();
        StringBuilderV1 stringConstructor = new StringBuilderV1();
        for (int i = 0; i < 100000; ++i)
            stringConstructor.append(helloPrefix)/*.append(1000000)*//*.append(i + 100000000l)*/.append(helloSuffix).append(helloSuffix);
        stringConstructor.toString();
        executeTime1 = System.nanoTime() - executeTime1;

        System.out.println("StringBuilderV1:" + executeTime1 + " nSec, " + TimeUnit.NANOSECONDS.toMillis(executeTime1) + " mSec");
    }

    public void testStringBuilder() {
        long executeTime = System.nanoTime();
        StringBuilder stringBuilder = new StringBuilder();
        for( int i=0; i< 100000; ++i)
            stringBuilder.append(helloPrefix)/*.append(1000000)*//*/*.append(i + 100000000l)*/.append(helloSuffix).append(helloSuffix);
        stringBuilder.toString();
        executeTime = System.nanoTime() - executeTime;
        System.out.println("StringBuilder:" + executeTime + " nSec, " + TimeUnit.NANOSECONDS.toMillis(executeTime) + " mSec");
    }

    public void testStringBuilderV1WithCharArray() {
        long executeTime1 = System.nanoTime();
        StringBuilderV1 stringConstructor = new StringBuilderV1();
        for (int i = 0; i < 100000; ++i)
            stringConstructor.append(helloPrefixCharArray)/*.append(i)*//*/*.append(i + 100000000l)*/.append(helloSuffixCharArray).append(helloSuffixCharArray);
        stringConstructor.toString();
        executeTime1 = System.nanoTime() - executeTime1;

        System.out.println("StringBuilderV1:" + executeTime1 + " nSec, " + TimeUnit.NANOSECONDS.toMillis(executeTime1) + " mSec");
    }

    public void testStringBuilderWithCharArray() {
        long executeTime = System.nanoTime();
        StringBuilder stringBuilder = new StringBuilder();
        for( int i=0; i< 100000; ++i)
            stringBuilder.append(helloPrefixCharArray).append(i)/*.append(i + 100000000l)*/.append(helloSuffixCharArray).append(helloSuffixCharArray);
        stringBuilder.toString();
        executeTime = System.nanoTime() - executeTime;
        System.out.println("StringBuilder:" + executeTime + " nSec, " + TimeUnit.NANOSECONDS.toMillis(executeTime) + " mSec");
    }


    public static void main(String[] args) {
        //Please run these class testing separately and avoid the GC involve impact the performance counter.
        StringBuilderV1Tester buildTester = new StringBuilderV1Tester();
        /*for (int i = 0; i < 10; ++i) {
            long freeMemory = Runtime.getRuntime().freeMemory();
            buildTester.testStringBuilder();
            System.out.println("V1 Memory Cost:" + -(Runtime.getRuntime().freeMemory() - freeMemory));
            System.gc();
        }

        System.gc();
        System.gc();
        for (int i = 0; i < 10; ++i) {
            long freeMemory = Runtime.getRuntime().freeMemory();
            buildTester.testStringBuilderV1();
            System.out.println("V1 Memory Cost:" + -(Runtime.getRuntime().freeMemory() - freeMemory));
            System.gc();
        }*/

        for (int i = 0; i < 4; ++i) {
            long freeMemory = Runtime.getRuntime().freeMemory();
            buildTester.testStringBuilder();
            //System.out.println("V1 Memory Cost:" + -(Runtime.getRuntime().freeMemory() - freeMemory));
            System.gc();
        }

        System.gc();
        System.gc();
        for (int i = 0; i < 4; ++i) {
            long freeMemory = Runtime.getRuntime().freeMemory();
            buildTester.testStringBuilderV1();
            //System.out.println("V2 Memory Cost:" + -(Runtime.getRuntime().freeMemory() - freeMemory));
            System.gc();
        }

        System.gc();
        System.gc();
        for (int i = 0; i < 4; ++i) {
            long freeMemory = Runtime.getRuntime().freeMemory();
            buildTester.testStringBuilderV2();
            //System.out.println("V3 Memory Cost:" + -(Runtime.getRuntime().freeMemory() - freeMemory));
            System.gc();
        }

        System.gc();
        System.gc();
        for (int i = 0; i < 4; ++i) {
            long freeMemory = Runtime.getRuntime().freeMemory();
            buildTester.testStringBuilderV3();
            //System.out.println("V3 Memory Cost:" + -(Runtime.getRuntime().freeMemory() - freeMemory));
            System.gc();
        }
    }

}
