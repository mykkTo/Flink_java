package com.kk.wc;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Arrays;

public class BoundedStreamWordCount {
    public static void main(String[] args) throws Exception{

        // 1. 创建流式执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment ( );

        // 2. 从文件读取数据 按行读取(存储的元素就是每行的文本)
        DataStreamSource<String> listDateSource = env.readTextFile ("input/words.txt");

        // 3. 将每行数据进行分词，转换成二元组类型
        SingleOutputStreamOperator<Tuple2<String, Long>> wordAndOne = listDateSource.flatMap ((String line, Collector<String> words) -> {
                    Arrays.stream (line.split (" ")).forEach (words::collect);
                })
                .returns (Types.STRING)
                .map (word -> Tuple2.of (word, 1L)
                )
                .returns (Types.TUPLE (Types.STRING, Types.LONG));

        // 4. 分组
        KeyedStream<Tuple2<String, Long>, String> wordAndOneKS = wordAndOne.keyBy (t -> t.f0);

        // 5. 求和
        SingleOutputStreamOperator<Tuple2<String, Long>> result = wordAndOneKS.sum (1);

        // 6. 打印
        result.print ();

        // 7. 执行
        env.execute ();

    }
}