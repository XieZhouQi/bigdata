package com.technology.flink.operator;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @description: map可以理解为映射，对每个元素进行一定的变换后，映射为另一个元素
 * @author: xzq
 * @date: 2021/2/19
 * @version:
 */
public class MapJob {

    public static void main(String[] args) throws Exception {
        // 获取执行环境配置信息
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 连接socket获取输入的数据
        DataStream<String> text = env.socketTextStream("127.0.0.1", 9000);

        // map操作, 直接转换
        DataStream<String> dataStream = text.map(d-> d);

        // map操作, 自定义函数处理
        DataStream<Tuple2<String,String>> dataStream1 = text.map(new MapFunction<String, Tuple2<String, String>>() {
            @Override
            public Tuple2<String, String> map(String s) throws Exception {
                // split("\\W+") 使用非数字字母切分字符串
                String[] tokens = s.toLowerCase().split("\\W+");
                return new Tuple2<String, String>(tokens[0], tokens[1]);
            }
        });

        // 打印输出sink
        dataStream.print();

        dataStream1.print();

        // 开始执行
        env.execute();
    }
}
