package connection.client;


import connection.Connection;

public interface ClientConnectionFactory {
    Connection newConnection();
}
