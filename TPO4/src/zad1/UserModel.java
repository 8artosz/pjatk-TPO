package zad1;

import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.HashSet;

public class UserModel {
    SocketAddress address;
    SocketChannel channel;
    HashSet<String> topics = new HashSet<>();
    UserModel(SocketAddress address, SocketChannel channel){
        this.address = address;
        this.channel = channel;
    }
    void subscribe (String topic){
        topics.add(topic);
    }
    void unsubscribe(String topic){
        if(topics.contains(topic)){
            topics.remove(topic);
        }
    }
}
