package zad1;


import Models.TranslationModel;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class LanguageServer {
    private ServerSocket serverSocket;
    static TranslationModel tm = null;
    String translatedWord;
    String word;

    public void start(int port ) throws IOException, ClassNotFoundException {

        InetAddress host = InetAddress.getLocalHost();

        serverSocket = new ServerSocket(port);

        Socket socket = serverSocket.accept();

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        tm = (TranslationModel) ois.readObject();
        word = tm.getWord();
        if(tm.getWordToTranslate() == null){
            translatedWord = "Takie slowo nie istnieje";
        }
        else translatedWord = getWordFromDictionary(tm.getWordToTranslate(),word);

        ois.close();

        while (true) {

            Socket info = new Socket(host.getHostName(), tm.getPort());
            ObjectOutputStream oos = new ObjectOutputStream(info.getOutputStream());
            oos.writeObject(translatedWord);
            oos.close();
            info.close();
            serverSocket.close();


            if (info.isBound()) {
                break;
            }
        }
    }

     public String getWordFromDictionary(String wordToTranslate,String word) throws FileNotFoundException, IOException {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/dictionary_" + word + ".properties"));
            String translatedWord = properties.getProperty(wordToTranslate);

            if(translatedWord == null)
                translatedWord = "Takie slowo nie istnieje";
         return translatedWord;
     };
}