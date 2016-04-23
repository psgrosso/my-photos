package connection.server;

import command.server.ServerCommandExecutor;
import command.server.ServerCommandProcessor;
import connection.Connection;
import connection.SocketConnection;
import log.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public final class ServerConnectionAcceptor extends Thread {
    public static final int PORT = 8088;

    public ServerConnectionAcceptor() {
        start();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), PORT));
            while (true) {
                Log.SERVER.info("Waiting for a connection");
                Socket workerSocket = serverSocket.accept();
                Log.SERVER.info("New connection started");
                Connection connection = new SocketConnection(workerSocket);
                ServerCommandProcessor serverProcessor = new ServerCommandProcessor(
                        ServerCommandExecutor.getInstance(), connection);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
