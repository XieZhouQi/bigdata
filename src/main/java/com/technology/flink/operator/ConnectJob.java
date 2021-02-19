package com.technology.flink.operator;

import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: TODO
 * @author: xzq
 * @date: 2021/2/19
 * @version:
 */
public class ConnectJob {

    public static void main(String[] args) throws Exception {
        // 获取执行环境配置信息
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 连接socket获取输入的数据
        DataStream<String> textStream9000 = env.socketTextStream("127.0.0.1", 9000);


        // 连接socket获取输入的数据
        DataStream<String> textStream9001 = env.socketTextStream("127.0.0.1", 9100);

//        //将输入处理一下，变为tuple2
//        DataStream<Tuple2<String, String>> mapStream9000 = textStream9000
//                .map(new MapFunction<String, Tuple2<String, String>>() {
//                    @Override
//                    public Tuple2<String, String> map(String s) throws Exception {
//                        return Tuple2.of(s, "来自9000端口：" + s);
//                    }
//                });
//
//        DataStream<Tuple2<String, String>> mapStream9100 = textStream9001
//                .map(new MapFunction<String, Tuple2<String, String>>() {
//                    @Override
//                    public Tuple2<String, String> map(String s) throws Exception {
//                        return Tuple2.of(s, "来自9100端口：" + s);
//                    }
//                });

        //转为Integer类型流
        DataStream<Integer> intStream = textStream9000.filter(s -> isNumeric(s)).map(s -> Integer.valueOf(s));
        //连接起来，分别处理，返回同样的一种类型。
        SingleOutputStreamOperator result = intStream.connect(textStream9001)
                .map(new CoMapFunction<Integer, String, Tuple2<Integer, String>>() {
                    @Override
                    public Tuple2<Integer, String> map1(Integer value) throws Exception {
                        return Tuple2.of(value, "");
                    }

                    @Override
                    public Tuple2<Integer, String> map2(String value) throws Exception {
                        return Tuple2.of(null, value);
                    }
                });

        //4.打印输出sink
        result.print();

        //5.开始执行
        env.execute();
    }

    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

}
