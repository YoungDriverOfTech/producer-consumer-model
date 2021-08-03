package threadPool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class WordCount {

    private final int threadNum;
    private ExecutorService threadPool;

    public WordCount(int threadNum) {
        this.threadNum = threadNum;
        this.threadPool = Executors.newFixedThreadPool(threadNum);  // 使用此方法做成一个线程池
    }

    // 统计文件中个单词的数量
    public Map<String, Integer> count(File file ) throws FileNotFoundException, ExecutionException, InterruptedException {

        BufferedReader reader = new BufferedReader((new FileReader(file)));
        List<Future<Map<String, Integer>>> futures = new ArrayList<>();  // 这个list用来装各个线程处理的结果

        // 开启若干个线程，每个线程区读取文件的一行内容，并将其中的单词统计结果返回
        // 最后，主线程将工作线程放回的结果汇总再一起
        for (int i = 0; i < threadNum; i++) {

            // threadPool.submit方法，可以提交一个任务，但是这里不推荐使用Runnable接口，因为这个接口没有返回值，不能把每个线程的结果收集
            // 起来，且没有抛出异常，对于异常的处理也会变得很难. 而Callable接口正好解决了这两个问题
            Future<Map<String, Integer>> future = threadPool.submit(new Callable<Map<String, Integer>>() {  // Callable后面跟的泛型就是返回值

                @Override
                public Map<String, Integer> call() throws Exception {
                    String line;
                    Map<String, Integer> result = new HashMap<>();
                    while ((line = reader.readLine()) != null) {
                        String[] words = line.split(" ");
                        for (String word : words) {
                            result.put(word, result.getOrDefault(word, 0) + 1);
                        }
                    }
                    return result;
                }
            });
            futures.add(future);
        }

        // 对futures的结果进行合并处理
        Map<String, Integer> finalResult = new HashMap<>();
        for (Future<Map<String, Integer>> future : futures) {
            Map<String, Integer> resultFromWorker = future.get();
            mergeFutureResultIntoFinalResult(resultFromWorker, finalResult);
        }

        return finalResult;
    }

    private void mergeFutureResultIntoFinalResult(Map<String, Integer> resultFromWorker,
                                                  Map<String, Integer> finalResult) {

        for (Map.Entry<String, Integer> entry : resultFromWorker.entrySet()) {
            String word = entry.getKey();
            int wordNumber = finalResult.getOrDefault(word, 0) + 1;
            finalResult.put(word, wordNumber);
        }
    }
}
