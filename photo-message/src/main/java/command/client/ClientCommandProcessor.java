package command.client;

import command.Command;
import command.CommandExecutor;
import command.PingCommand;
import command.TerminateCommand;
import connection.Connection;
import connection.client.ClientConnectionFactory;
import log.Log;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


public class ClientCommandProcessor {
    private static final int MAX_THREADS = 5;

    private final int maxThreads;
    private final ClientConnectionFactory connectionFactory;
    private final CommandExecutor executor;
    private final BlockingQueue<Command> queue;
    private final List<ClientCommandThread> threads;
    private final Set<ClientCommandThread> idleThreads;

    public ClientCommandProcessor(@NotNull ClientConnectionFactory connectionFactory,
                                  @NotNull CommandExecutor commandExecutor) {
        this(connectionFactory, commandExecutor, MAX_THREADS);
    }

    public ClientCommandProcessor(@NotNull ClientConnectionFactory connectionFactory,
                                  @NotNull CommandExecutor commandExecutor, int maxThreads) {
        this.connectionFactory = connectionFactory;
        this.executor = commandExecutor;
        this.maxThreads = maxThreads;
        queue = new LinkedBlockingQueue<>();
        threads = new LinkedList<>();
        idleThreads = new HashSet<>();

        // Connect to the server and send PING command
        addCommand(new PingCommand());
    }

    public void addCommand(@NotNull Command command) {
        synchronized (idleThreads) {
            // Lazy thread creation, if appropriate
            if (idleThreads.size() == 0 && threads.size() < maxThreads) {
                // We could create a new thread for this command
                threads.add(new ClientCommandThread());
            }
        }
        queue.add(command);
    }

    public void finish() {
        try {
            // Give other threads some time
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            // Nothing to do here
        }
        // Terminate threads one by one
        for (int i = 0; i < threads.size(); i++) {
            // We specifically don't want to use addCommand() method to send a terminate command
            queue.add(new TerminateCommand());
        }
    }

    private void threadIdle(@NotNull ClientCommandThread thread) {
        idleThreads.add(thread);
    }

    private void threadWorking(@NotNull ClientCommandThread thread) {
        idleThreads.remove(thread);
    }

    private class ClientCommandThread extends Thread {
        private final String workerName;

        ClientCommandThread() {
            workerName = Thread.currentThread().getName();
            Log.CLIENT.info("Starting new worker thread: " + workerName);
            start();
        }

        @Override
        public void run() {
            Log.CLIENT.info(workerName + ": attemping connection to server");
            Connection connection = connectionFactory.newConnection();
            Log.CLIENT.info("Connection established");
            boolean terminate = false;
            while (!terminate) {
                try {
                    // This thread is currently idle
                    Log.CLIENT.info(workerName + ": now idle");
                    threadIdle(this);
                    Command command = queue.take();
                    // As soon as the command is obtained, the thread becomes busy
                    threadWorking(this);

                    Log.CLIENT.info(workerName + ": about to process " + command);

                    // Process command
                    terminate = executor.execute(command, connection);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
