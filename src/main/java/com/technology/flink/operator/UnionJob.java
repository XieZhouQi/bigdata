package com.technology.flink.operator;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.util.concurrent.TimeUnit;

/**
 * @description: union可以将多个流合并到一个流中，以便对合并的流进行统一处理。是对多个流的水平拼接。
 * 参与合并的流必须是同一种类型。
 * @author: xzq
 * @date: 2021/2/19
 * @version:
 */
public class UnionJob {

    public static void main(String[] args) throws Exception {
        // 获取执行环境配置信息
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 连接socket获取输入的数据
        DataStream<String> textStream = env.socketTextStream("127.0.0.1", 9000);

        Thread.sleep(5);

        // 连接socket获取输入的数据
        DataStream<String> textStream1 = env.socketTextStream("127.0.0.1", 9100);

        Thread.sleep(5);

        // 连接socket获取输入的数据
        DataStream<String> textStream2 = env.socketTextStream("127.0.0.1", 9200);

        DataStream<String> mapStream9000=textStream.map(s->"来自9000端口："+s);
        DataStream<String> mapStream9001=textStream1.map(s->"来自9001端口："+s);
        DataStream<String> mapStream9002=textStream2.map(s->"来自9002端口："+s);


        //3.union用来合并两个或者多个流的数据，统一到一个流中
        DataStream<String> result =  mapStream9000.union(mapStream9000,mapStream9001);



        //4.打印输出sink
        result.print();


        //5.开始执行
        env.execute();
    }

}
