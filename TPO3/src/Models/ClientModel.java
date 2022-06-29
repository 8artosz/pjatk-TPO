package Models;

import java.io.Serializable;
import java.net.InetAddress;

public class ClientModel implements Serializable {

    private String wordToTranslate;
    private String languageCode;
    private int port;
    private InetAddress host;


    public ClientModel(String wordToTranslate, String languageCode, int port) {
        this.wordToTranslate = wordToTranslate;
        this.languageCode = languageCode;
        this.port = port;
    }

    public String getWordToTranslate() {
        return wordToTranslate;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public int getPort() {
        return port;
    }


    public InetAddress getHost() {
        return host;
    }

    public void setHost(InetAddress host) {
        this.host = host;
    }
}