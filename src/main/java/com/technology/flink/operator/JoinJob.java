package com.technology.flink.operator;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.util.Collector;

/**
 * @description: 多流join job
 * @author: xzq
 * @date: 2021/2/18
 * @version:
 */
public class JoinJob {

    public static void main(String[] args) throws Exception {
        //1.获取执行环境配置信息
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, bsSettings);

        // 2 连接socket获取输入的数据
        DataStream<String> text = env.socketTextStream("127.0.0.1", 9000);

        // 连接socket获取输入的数据
        DataStream<String> text1 = env.socketTextStream("127.0.0.1", 9100);

        DataStream<Tuple2<String, Integer>> dataStream = text.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            // split("\\W+") 使用非数字字母切分字符串
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                String[] tokens = s.toLowerCase().split("\\W+");
                collector.collect(new Tuple2<String, Integer>(tokens[0], Integer.parseInt(tokens[1])));
            }
        }).keyBy(0).timeWindow(Time.seconds(15)).sum(1);

        DataStream<Tuple2<String, Integer>> dataStream1 = text1.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                String[] tokens = s.toLowerCase().split("\\W+");
                collector.collect(new Tuple2<String, Integer>(tokens[0], Integer.parseInt(tokens[1])));
            }
        }).keyBy(0).timeWindow(Time.seconds(15)).sum(1);

        //3.两个流进行join操作，是inner join，关联上的才能保留下来
        DataStream<String> result =  dataStream.join(dataStream1)
                //关联条件，以第0列关联（两个source输入的字符串）
                .where(t1->t1.getField(0)).equalTo(t2->t2.getField(0))
                //以处理时间，每10秒一个滚动窗口
                .window(TumblingProcessingTimeWindows.of(Time.seconds(15)))
                //关联后输出
                .apply((t1,t2)->t1.getField(0) +","+ t1.getField(1)+ ","+t2.getField(0) +","+t2.getField(1))
                ;
        //4.打印输出sink
        result.print();
        //5.开始执行
        env.execute();

    }

}
