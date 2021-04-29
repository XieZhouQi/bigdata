package com.technology.flink;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @author: xzq
 * @date: 2021/4/21
 * @version:
 */
public class Test1 {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //获取数据源
        List data = new ArrayList<Tuple3<Integer,Integer,List<String>>>();
        List<String> str1 = new ArrayList<>();
        str1.add("1");

        List<String> str2 = new ArrayList<>();
        str2.add("1");
        str2.add("2");

        List<String> str3 = new ArrayList<>();
        str3.add("1");
        str3.add("2");
        str3.add("3");

        data.add(new Tuple3<>(0,2,str1));
        data.add(new Tuple3<>(0,1,str2));
        data.add(new Tuple3<>(0,5,str3));
       // data.add(new Tuple3<>(0,3,5));
        data.add(new Tuple3<>(1,1,str3));
        data.add(new Tuple3<>(1,2,str1));
        data.add(new Tuple3<>(1,3,str2));
        //data.add(new Tuple3<>(1,2,9));

        DataStreamSource<Tuple3<Integer,Integer,Integer>> items = env.fromCollection(data);
        items.keyBy(0).minBy(2).print();
        env.execute("defined streaming source");
    }
}
