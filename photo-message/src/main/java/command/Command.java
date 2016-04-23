package command;


import java.io.Serializable;

public interface Command extends Serializable {
    CommandType getType();
    String getHostName();
    long getDataLength();
}
