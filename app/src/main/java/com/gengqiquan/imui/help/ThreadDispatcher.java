package com.gengqiquan.imui.help;

import java.util.concurrent.*;


/**
 * 线程池辅助类
 * 适合零散的，不定时的、不经常发生的，不强连续的，
 * 数量不定但是不能丢弃的，没有优先级的，一旦生产需要马上尽快执行的异步行为，
 *
 * @author gengqiquan
 * @date 2019/4/4 4:59 PM
 */
public class ThreadDispatcher {
    private volatile static
    ExecutorService executorService;

    private static ExecutorService executorService() {
        if (executorService == null) {
            synchronized (ThreadDispatcher.class) {
                if (executorService == null) {
                    executorService = Executors.newCachedThreadPool(new ThreadFactory() {
                        public Thread newThread(Runnable runnable) {
                            Thread result = new Thread(runnable, "GQQ-ThreadPool");
                            result.setDaemon(false);
                            return result;
                        }
                    });
                }
            }
        }
        return executorService;
    }

    public static void post(Runnable runnable) {
        ThreadDispatcher.executorService().execute(runnable);
    }

    public static <T> T get(Callable<T> callable) throws ExecutionException, InterruptedException {
        return (T) ThreadDispatcher.executorService().submit(callable).get();
    }
}
