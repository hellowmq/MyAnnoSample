package com.wenmq.annosample.util;

import java.util.Random;

/**
 * @author ifans.wen
 * @date 2020/6/24
 * @description
 */
public class TimeUtil {
    private static long getRandomMillis() {
        Random random = new Random();
        return random.nextInt(100);
    }

    public static void randomSleep() {
        try {
            Thread.sleep(getRandomMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
