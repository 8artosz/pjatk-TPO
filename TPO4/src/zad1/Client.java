package zad1;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import javax.swing.*;


public class Client {

    private SocketChannel socketClientChannel;
    private String name;
    private int serverPort = 44444;
    JTextArea textArea = new JTextArea();


    public Client(String name) throws IOException {
        this.name = name;
        startGui();
        startClient();
    }


    private void sendMsg(String msgString) throws IOException {
        ByteBuffer buffer = null;

        if(msgString != null) {
            byte[] message = msgString.getBytes();
            buffer = ByteBuffer.wrap(message);
            socketClientChannel.write(buffer);
            buffer.clear();
        }
    }

    private void startClient() throws IOException {
        this.socketClientChannel = SocketChannel.open(new InetSocketAddress(serverPort));
        while(true) {
            ByteBuffer buffer = ByteBuffer.allocate(1000);
            socketClientChannel.read(buffer);
            String output = new String(buffer.array()).trim();
            textArea.append(output + "\n");

        }
    }

    private void startGui() {
        JFrame frame = new JFrame("Client");
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        Button subscribeButton = new Button("Subscribe");
        Button unsubscribeButton = new Button("Unsubscribe");
        Button myTopicsButton = new Button("My topics");
        Button topicsButton = new Button("Topics");
        Button clearButton = new Button("Clear");
        TextField tx = new TextField();
        tx.setPreferredSize(new Dimension(200,30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,350);
        frame.setLayout(new FlowLayout());
        textArea.setPreferredSize(new Dimension(300,300));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        subscribeButton.setBounds(150,50,100,60);
        unsubscribeButton.setBounds(150,50,100,60);
        myTopicsButton.setBounds(150,50,100,60);
        topicsButton.setBounds(150,50,100,60);
        clearButton.setBounds(150,50,100,60);
        top.add(textArea);
        bottom.add(tx);
        bottom.add(subscribeButton);
        bottom.add(unsubscribeButton);
        bottom.add(myTopicsButton);
        bottom.add(topicsButton);
        bottom.add(clearButton);
        frame.setLayout(new BorderLayout());
        frame.add(top,BorderLayout.NORTH);
        frame.add(bottom,BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);


        subscribeButton.addActionListener(e->{
            try {
                this.sendMsg("Subscribe " + tx.getText());
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        });
        unsubscribeButton.addActionListener(e->{
            try {
                this.sendMsg("Unsubscribe " + tx.getText());
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        });
        myTopicsButton.addActionListener(e->{
            try {
                this.sendMsg("My topics");
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        });
        topicsButton.addActionListener(e->{
            try {
                this.sendMsg("List");
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        });
        clearButton.addActionListener(e->{
            textArea.setText("");
        });

    }

    public static void main(String[] args) {
        try {
            new Client("Client");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}