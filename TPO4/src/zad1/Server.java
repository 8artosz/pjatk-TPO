package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Server {

    private InetSocketAddress hostAddress;
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private int port = 44444;
    HashSet<String> topics = new HashSet<>();
    HashMap<SocketChannel, UserModel> users = new HashMap<>();

    public Server() throws IOException {
        selector = Selector.open();
        hostAddress = new InetSocketAddress(port);
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(hostAddress);
        serverSocketChannel.configureBlocking(false);

        int ops = serverSocketChannel.validOps();
        serverSocketChannel.register(selector, ops, SelectionKey.OP_ACCEPT);

        while(true){
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> itr = selectedKeys.iterator();

            while (itr.hasNext()) {
                SelectionKey key =  itr.next();
                itr.remove();
                if(key.isAcceptable()) {
                    SocketChannel user = serverSocketChannel.accept();
                    users.put(user, new UserModel(user.getRemoteAddress(),user));
                    users.put(user, new UserModel(user.getRemoteAddress(),user));
                    user.configureBlocking(false);
                    user.register(selector,SelectionKey.OP_READ);

                }else if (key.isReadable()) {
                    SocketChannel user = users.get(key.channel()).channel;
                    ByteBuffer buffer = ByteBuffer.allocate(1000);
                    user.read(buffer);
                    String output = new String(buffer.array()).trim();
                    String [] tmp = output.split(" ");
                    if(tmp[0].equals( "Add")){
                        topics.add(tmp[1]);
                        sendMessageToAll("New topic added: " + tmp[1]);
                    }
                    if(tmp[0].equals( "Delete")){
                        if(topics.contains(tmp[1])){
                            delete(tmp[1]);
                            topics.remove(tmp[1]);
                        }
                        sendMessageToAll("Topic deleted: " + tmp[1]);
                    }
                    if(tmp[0].equals("News")){
                        if(topics.contains(tmp[1])) {
                            String msg = "";
                            for (int i = 2; i < tmp.length; i++) {
                                msg += tmp[i] + " ";
                            }
                            sendMessage(tmp[1], msg);
                            ByteBuffer buf = Charset.forName("ISO-8859-2").encode("News sent");
                            user.write(buf);
                        }
                    }
                    if(tmp[0].equals("Subscribe")){
                        if(topics.contains(tmp[1])) {
                            users.get(key.channel()).subscribe(tmp[1]);
                            ByteBuffer buf = Charset.forName("ISO-8859-2").encode("You subscribed " + tmp[1]);
                            user.write(buf);
                        }
                    }
                    if(tmp[0].equals("Unsubscribe")) {
                        if(topics.contains(tmp[1])) {
                            users.get(key.channel()).unsubscribe(tmp[1]);
                            ByteBuffer buf = Charset.forName("ISO-8859-2").encode("You unsubscribed " + tmp[1]);
                            user.write(buf);
                        }
                    }
                    if(tmp[0].equals("My")) {
                        sendUsersTopics(users.get(key.channel()));
                    }
                    if(tmp[0].equals("List")){
                        ByteBuffer buf = Charset.forName("ISO-8859-2").encode("List of topics: " + topics.toString());
                        user.write(buf);
                    }
                    buffer.clear();
                }

            }
        }
    }

    public void sendMessage(String topic,String msg) throws IOException{
        for(UserModel x:users.values()){
            if(x.topics.contains(topic)){
                SocketChannel user = x.channel;
                if(user.isConnected()){
                    ByteBuffer buffer = ByteBuffer.allocate(1000);
                    ByteBuffer buf = Charset.forName("ISO-8859-2").encode(topic + ": " + msg);
                    user.write(buf);
                    buffer.clear();
                }
            }
        }
    }
    public void sendMessageToAll(String msg) throws IOException{
        for(UserModel x:users.values()){
            SocketChannel user = x.channel;
            if(user.isConnected()){
                ByteBuffer buffer = ByteBuffer.allocate(1000);
                ByteBuffer buf = Charset.forName("ISO-8859-2").encode(msg);
                user.write(buf);
                buffer.clear();
            }
        }
    }
    public void delete(String topic){
        for(UserModel x:users.values()){
            if(x.topics.contains(topic)){
                x.unsubscribe(topic);
            }
        }
    }
    public void sendUsersTopics(UserModel userr) throws IOException{
        for(UserModel x:users.values()){
            if(x.channel == userr.channel ){
                SocketChannel user = x.channel;
                if(user.isConnected()){
                    ByteBuffer buffer = ByteBuffer.allocate(1000);
                    String list = "My list of topics: " + x.topics.toString();
                    ByteBuffer buf = Charset.forName("ISO-8859-2").encode(list);
                    user.write(buf);
                    buffer.clear();
                }
            }
        }
    }


    public static void main(String[] args) throws IOException {
        new Server();
    }

}
