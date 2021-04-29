package com.technology.flink.mq;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.*;
import com.technology.flink.util.ConfigUtil;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import java.util.Map;
import java.util.Properties;


public class RocketMqSource extends RichParallelSourceFunction<Tuple3<String, String, Integer>> {

    private Consumer consumer;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        Properties properties = ConfigUtil.getRocketMqConfiguration();
        properties.put(PropertyKeyConst.GROUP_ID, "leo-test-flink" + ConfigUtil.getDomainConfig());
        consumer = ONSFactory.createConsumer(properties);
    }

    @Override
    public void close() throws Exception {
        try {
            cancel();
        } finally {
            super.close();
        }
    }

    @Override
    public void run(SourceContext<Tuple3<String, String, Integer>> sourceContext) {
        //这里通过订阅DEVICE_TO_ATHENA_TOPIC 来接受任务消息和图片特征提取消息
        consumer.subscribe("flink-test-demo",
                "test",
                new MessageListener() {
                    @Override
                    public Action consume(Message message, ConsumeContext context) {
                        try {

                            //反序列化
                            MqData mqData = JSON.parseObject(new String(message.getBody()),
                                    MqData.class);
                            Tuple3<String, String, Integer> tuple = new Tuple3<String, String, Integer>(
                                    mqData.getNo(), mqData.getName(), mqData.getAge()
                            );
                            sourceContext.collect(tuple);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return Action.CommitMessage;
                    }
                });
        consumer.start();
        System.out.println("=================consumer start");
        //这里sleep是因为RichParallelSourceFunction在run 方法执行完了就close了，为了使mq消费者一直活着，需要通过sleep来保持线程
        while (consumer.isStarted()) {
            try {
                //System.out.println("=================Thread start sleep");
                Thread.sleep(50);
                //System.out.println("=================Thread end sleep");
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                Thread.currentThread().interrupt();
            }
        }

    }



    @Override
    public void cancel() {
        if (consumer != null) {
            consumer.shutdown();
        }
    }
}
