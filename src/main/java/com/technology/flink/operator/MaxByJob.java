package com.technology.flink.operator;

import com.sun.xml.internal.ws.resources.TubelineassemblyMessages;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.api.windowing.windows.Window;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.util.Collector;

import java.util.concurrent.TimeUnit;

/**
 * @description: 逻辑上将Stream根据指定的Key进行分区，是根据key的散列值进行分区的
 * @author: xzq
 * @date: 2021/2/19
 * @version:
 */
public class MaxByJob {

    public static void main(String[] args) throws Exception {
        // 获取执行环境配置信息
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();

        // 连接socket获取输入的数据
        KeyedStream<String, Tuple> text = env.socketTextStream("127.0.0.1", 9000).keyBy(0);

//        DataStream<Tuple3<String, Integer, Integer>> result = text
//                .map(new MapFunction<String, Tuple3<String, Integer,Integer>>() {
//
//                    @Override
//                    public Tuple3<String, Integer, Integer> map(String s) throws Exception {
//                        //String[] tokens = s.toLowerCase().split("\\W+");
//                        String[] tokens = s.toLowerCase().split(" ");
//                        return new Tuple3<String, Integer,Integer>(tokens[0], Integer.parseInt(tokens[1]) ,Integer.parseInt(tokens[2]));
//                    }
//                })
//                //如果要用Lambda表示是，Tuple2是泛型，那就得用returns指定类型。
//                .returns(Types.TUPLE(Types.STRING, Types.INT, Types.INT))
//                //keyBy进行分区，按照第一列，也就是按照单词进行分区
//                .keyBy(0)
//                .timeWindowAll(Time.seconds(30))
//                .maxBy(2);


//        AllWindowedStream<Tuple3<String, Integer, Integer>, TimeWindow> result = text
//                .map(new MapFunction<String, Tuple3<String, Integer,Integer>>() {
//
//                    @Override
//                    public Tuple3<String, Integer, Integer> map(String s) throws Exception {
//                        //String[] tokens = s.toLowerCase().split("\\W+");
//                        String[] tokens = s.toLowerCase().split(" ");
//                        return new Tuple3<String, Integer,Integer>(tokens[0], Integer.parseInt(tokens[1]) ,Integer.parseInt(tokens[2]));
//                    }
//                })
//                //如果要用Lambda表示是，Tuple2是泛型，那就得用returns指定类型。
//                .returns(Types.TUPLE(Types.STRING, Types.INT, Types.INT))
//                //keyBy进行分区，按照第一列，也就是按照单词进行分区
//                .keyBy(0)
//                .maxBy(2)
//                //指定窗口，每10秒个计算一次
//                .timeWindowAll(Time.of(30, TimeUnit.SECONDS));

        SingleOutputStreamOperator<Tuple3<String, Integer, Integer>> result = text
                .map(new MapFunction<String, Tuple3<String, Integer,Integer>>() {
                    @Override
                    public Tuple3<String, Integer, Integer> map(String s) throws Exception {
                        //String[] tokens = s.toLowerCase().split("\\W+");
                        String[] tokens = s.toLowerCase().split(" ");
                        return new Tuple3<String, Integer,Integer>(tokens[0], Integer.parseInt(tokens[1]) ,Integer.parseInt(tokens[2]));
                    }
                })
                //如果要用Lambda表示是，Tuple2是泛型，那就得用returns指定类型。
                .returns(Types.TUPLE(Types.STRING, Types.INT, Types.INT))
                .timeWindowAll(Time.seconds(30))
                //keyBy进行分区，按照第一列，也就是按照单词进行分区
                .maxBy(2);


        //4.打印输出sink
        //5.开始执行
//        result.process(new ProcessAllWindowFunction<Tuple3<String, Integer, Integer>, String, TimeWindow>(){
//            @Override
//            public void process(Context context, Iterable<Tuple3<String, Integer, Integer>> elements, Collector<String> out) throws Exception {
//                // iterable可以访问当前窗口内的所有数据，
//                // 这里简单处理，只统计了元素数量
//                for (Tuple3<String, Integer, Integer> tuple3 : elements) {
//                    System.out.println("结果"+ tuple3.f0.toString() + tuple3.f1 + tuple3.f2);
//                }
//            }
//        });
        result.print();
        env.execute();
    }

}
