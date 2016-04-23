import command.Command;
import command.CommandExecutor;
import command.PingCommand;
import command.client.ClientCommandProcessor;
import connection.client.ClientConnectionFactory;
import connection.Connection;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class TestClientCommandProcessor implements ClientConnectionFactory, CommandExecutor {
    private static final int CMD_COUNT = 4000;
    private static final int THREAD_COUNT = 22;
    private static final int CMD_THREAD = CMD_COUNT / THREAD_COUNT;
    private static final int THRESHOLD = CMD_THREAD / 15;

    private final Map<Long, Integer> activeThreads;
    private int connectionCount;


    public static void main(String[] args) {
        new TestClientCommandProcessor().test();
    }

    public TestClientCommandProcessor() {
        activeThreads = new HashMap<>();
    }

    public void test() {
        ClientCommandProcessor processor = new ClientCommandProcessor(this, this, THREAD_COUNT);

        for (int i = 0; i < CMD_COUNT; i++) {
            processor.addCommand(new PingCommand());
        }
        processor.finish();

        try {
            // Wait for all threads to finish
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {}

        int total = 0;
        assert(activeThreads.size() == THREAD_COUNT);
        assert(connectionCount == THREAD_COUNT);
        for (int count : activeThreads.values()) {
            total += count;
            assert(count >= (CMD_THREAD - THRESHOLD) && count <= (CMD_THREAD + THRESHOLD));
        }
        // Assert all commands were processed
        assert(total == CMD_COUNT);
    }

    @Override
    public Connection newConnection() {
        connectionCount++;
        return new TestConnection();
    }

    @Override
    public boolean execute(@NotNull Command command, @NotNull Connection connection) {
        switch (command.getType()) {
            case TERMINATE:
                return true;
            case PING:
                try {
                    // Assert that the same connection is used by the same thread
                    long id = Thread.currentThread().getId();
                    assert(((TestConnection) connection).getThreadId() == id);
                    synchronized (activeThreads) {
                        if (activeThreads.computeIfPresent(id, (key, count) -> count + 1) == null) {
                            activeThreads.put(id, 1);
                        }
                    }
                    // Small delay to allow another thread to be selected
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {}
                break;
        }
        return false;
    }

    private static class TestConnection implements Connection {
        private long threadId;

        public TestConnection() {
            threadId = Thread.currentThread().getId();
        }

        public long getThreadId() {
            return threadId;
        }

        @Override
        public InputStream getInputStream() {
            return null;
        }

        @Override
        public OutputStream getOutputStream() {
            return null;
        }

        @Override
        public boolean writeCommand(@NotNull Command command) {
            return false;
        }

        @Override
        public Command readCommand() {
            return null;
        }

        @Override
        public boolean acknowledge() {
            return false;
        }

        @Override
        public boolean waitAcknowledge() {
            return false;
        }
    }
}
