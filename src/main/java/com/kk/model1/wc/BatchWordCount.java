package com.kk.model1.wc;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/*
 * @Description:    批处理有界流
 * @Author:         阿K
 * @CreateDate:     2022/5/19 21:15
 * @Param:
 * @Return:
**/
public class BatchWordCount {
    public static void main(String[] args) throws Exception {
        // 1. 创建执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment ( );
        // 2. 从文件读取数据 按行读取(存储的元素就是每行的文本)
        DataSource<String> listDateSource = env.readTextFile ("input/words.txt");
        // 3. 将每行数据进行分词，转换成二元组类型
        // Collector: flink 定义的收集器
        // Tuple: 二元组类型 <String,Long>,K 就是具体的单词，V 就是个数
        FlatMapOperator<String, Tuple2<String, Long>> wordAndOneTuple = listDateSource.flatMap ((String line, Collector<Tuple2<String, Long>> out) -> {
            // 每行根据空格分隔出，单词
            String[] words = line.split (" ");
            // out.collect  就是输出的意思
            for (String word : words) {
                // Tuple2.of 构建二元组实例
                out.collect (Tuple2.of (word, 1L));
            }
        })
                // returns 解决 scala 泛型擦除问题
                .returns (Types.TUPLE (Types.STRING, Types.LONG));

        // 根据转换得到的运算子  wordAndOneTuple

        // 4. 按照 word 进行分组, 0 表示第 1 个字段索引，就是上面泛型<k,v> 中的 k 为 word 字段
        UnsortedGrouping<Tuple2<String, Long>> wordAndOneGroup = wordAndOneTuple.groupBy (0);

        // 5. 分组进行聚合(求和)统计，1 表示第 2 个字段索引，就是上面泛型<k,v> 中的 v 为 1L 数值的字段
        AggregateOperator<Tuple2<String, Long>> sum = wordAndOneGroup.sum (1);

        // 6. 打印
        sum.print ( );
    }
}
