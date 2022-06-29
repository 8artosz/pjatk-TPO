package zad1;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class Admin {

    private SocketChannel socketAdminChannel;
    private String name;
    TextArea textArea = new TextArea();
    private int serverPort = 44444;


    public Admin(String name) throws IOException {
        this.name = name;
        startGui();
        startClient();
    }


    private void sendMsg(String msgString) throws IOException {
        ByteBuffer buffer = null;

        if(msgString != null) {
            byte[] message = msgString.getBytes();
            buffer = ByteBuffer.wrap(message);
            socketAdminChannel.write(buffer);
            buffer.clear();
        }
    }
    private void startClient() throws IOException {
        this.socketAdminChannel = SocketChannel.open(new InetSocketAddress(serverPort));
        while(true) {
            ByteBuffer buffer = ByteBuffer.allocate(1000);
            socketAdminChannel.read(buffer);
            String output = new String(buffer.array()).trim();
            textArea.append(output + "\n");

        }
    }

    private void startGui() {
        JFrame frame = new JFrame("Admin");
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button newsButton = new Button("News");
        Button topicsButton = new Button("Topics");
        Button clearButton = new Button("Clear");
        TextField tx = new TextField();
        tx.setPreferredSize(new Dimension(200,30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,350);
        frame.setLayout(new FlowLayout());
        textArea.setPreferredSize(new Dimension(300,300));
        addButton.setBounds(150,50,100,60);
        deleteButton.setBounds(150,50,100,60);
        newsButton.setBounds(150,50,100,60);
        topicsButton.setBounds(150,50,100,60);
        clearButton.setBounds(150,50,100,60);
        top.add(textArea);
        bottom.add(tx);
        bottom.add(addButton);
        bottom.add(deleteButton);
        bottom.add(newsButton);
        bottom.add(clearButton);
        bottom.add(topicsButton);
        frame.setLayout(new BorderLayout());
        frame.add(top,BorderLayout.NORTH);
        frame.add(bottom,BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);


        addButton.addActionListener(e->{
            try {
                this.sendMsg("Add " + tx.getText());
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        });
        deleteButton.addActionListener(e->{
            try {
                this.sendMsg("Delete " + tx.getText());
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        });
        newsButton.addActionListener(e->{
            try {
                this.sendMsg("News " + tx.getText());
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
            new zad1.Admin("Admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
