package server;


import command.AckCommand;
import command.client.ClientCommandExecutor;
import command.client.ClientCommandProcessor;
import connection.client.SocketClientConnectionFactory;
import connection.server.ServerConnectionAcceptor;

import java.util.concurrent.TimeUnit;


public class TestServerCommandProcessor {


    public static void main(String[] args) throws Exception {
        // Start server
        new ServerConnectionAcceptor();

        // Start client
        TimeUnit.SECONDS.sleep(1);
        ClientCommandProcessor clientProcessor = new ClientCommandProcessor(new SocketClientConnectionFactory(),
                new ClientCommandExecutor());

        clientProcessor.finish();
    }
}
