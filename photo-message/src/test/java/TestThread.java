import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.function.Function;


public class TestThread implements Callable<String> {
    private BlockingQueue<Function<Integer, String>> queue;

    public TestThread(BlockingQueue<Function<Integer, String>> queue) {
        this.queue = queue;
    }

    @Override
    public String call() throws Exception {
        String name = Thread.currentThread().getName();

        //TimeUnit.SECONDS.sleep(2);
        while (true) {
            System.out.println("waiting " + name);
            Function<Integer, String> function = queue.take();
            System.out.println(function.apply(1973));
        }
    }
}
