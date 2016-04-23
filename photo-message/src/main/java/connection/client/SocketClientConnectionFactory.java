package connection.client;


import connection.Connection;
import connection.SocketConnection;
import connection.server.ServerConnectionAcceptor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


public final  class SocketClientConnectionFactory implements ClientConnectionFactory {
    private final int port;

    public SocketClientConnectionFactory() {
        this(ServerConnectionAcceptor.PORT);
    }

    public SocketClientConnectionFactory(int port) {
        this.port = port;
    }

    @Override
    public Connection newConnection() {
        try {
            Socket clientSocket = new Socket(InetAddress.getLoopbackAddress(), port);
            return new SocketConnection(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
