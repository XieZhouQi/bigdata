package com.technology.flink.util;

import org.apache.flink.api.java.tuple.Tuple13;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

import java.sql.Timestamp;
import java.util.Properties;

/**
 * @Author shenlian description TODO date 2019/8/10
 **/
public class Utils {

    private final static int SIXTEEN = 16;

    /**
     * 创建HandleId
     */
    public static String createHandleId() {
        long time = System.currentTimeMillis();

        String timeToStr = time + "";

        if (timeToStr.length() < SIXTEEN) {
            timeToStr = String.format("%015d", time);
        } else {
            timeToStr = timeToStr.substring(0, 15);
        }

        return timeToStr + "" + ((int) (Math.random() * 900) + 100) + "" + (
                (int) (Math.random() * 900) + 100);

    }




    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    public static boolean checkNull(String str) {
        return str == null || "".equals(str.trim());
    }

    public static boolean checkNull(Object obj) {
        return obj == null || "".equals(obj);
    }

    public static boolean isEqual(byte[] source, byte[] target) {
        if (source.length == target.length) {
            for (int i = 0; i < source.length; i++) {
                if (source[i] != target[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
