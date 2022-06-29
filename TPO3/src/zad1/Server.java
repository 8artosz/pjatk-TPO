package zad1;

import Models.ClientModel;
import Models.TranslationModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server{

    private static ServerSocket serverSocket;
    private static int port;
    public static int newPort = 0;

    public static void main(String[] args){
        InetAddress host = null;
        List<String> codes = new ArrayList<String>();
        codes.add("de");
        codes.add("fr");
        codes.add("eng");

        ServerSocket socket1 = null;
        try {
            socket1 = new ServerSocket(0);
            socket1.setReuseAddress(true);
            newPort = socket1.getLocalPort();
            socket1.close();
            host = InetAddress.getLocalHost();
            port = Integer.parseInt(args[0]);
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {

            Socket socket = null;
            ClientModel cm = null;
            try {
                socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                cm = (ClientModel) ois.readObject();
                ois.close();
                String word = "";
                for (int i = 0; i < codes.size(); i++) {
                    if(cm.getLanguageCode().equals(codes.get(i)))
                        word=cm.getLanguageCode();
                }
                if (word != "") {
                    Thread serverThread = new Thread(() -> {
                        try {
                            new LanguageServer().start(newPort);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });

                    serverThread.start();

                    sendToLanguageServer(cm, word, host.getHostName());
                }
                else {

                    Socket socketReturn = new Socket(host.getHostName(), cm.getPort());
                    ObjectOutputStream objos = new ObjectOutputStream(socketReturn.getOutputStream());
                    objos.writeObject("Taki jezyk nie istnieje");
                    objos.close();

                    if (socketReturn.isBound()) {
                        socketReturn.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendToLanguageServer(ClientModel cm, String word, String hostName ) throws IOException {

        Socket socketLang = new Socket(hostName, newPort);
        TranslationModel tm = new TranslationModel(cm.getWordToTranslate(),word,cm.getHost().getAddress(),cm.getPort());
        ObjectOutputStream oos = new ObjectOutputStream(socketLang.getOutputStream());
        oos.writeObject(tm);
        oos.close();

    }
}
