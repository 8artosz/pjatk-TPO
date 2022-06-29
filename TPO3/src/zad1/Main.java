package zad1;


import java.io.IOException;
import java.net.ServerSocket;

public class Main   {

    public static void main(String[] args) {
        int port = 0;
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(0);
            socket.setReuseAddress(true);
            port = socket.getLocalPort();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String newPort = String.valueOf(port);

        Thread mainServer = new Thread(() -> {
                new Server().main(new String[]{newPort});
        });

        mainServer.start();

        Thread client = new Thread(() -> {
            try {
                new Client().main(new String[]{newPort});
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        if(mainServer.isAlive()) {
            client.start();
        }
    }

}