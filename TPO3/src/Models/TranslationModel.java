package Models;

import java.io.Serializable;

public class TranslationModel implements Serializable {

    private byte[] clientAdress;
    private String word;
    private int port;
    private String wordToTranslate;

    public TranslationModel(String wordToTranslate, String word, byte[] clientAdress, int port) {
        this.port = port;
        this.wordToTranslate = wordToTranslate;
        this.clientAdress = clientAdress;
        this.word = word;

    }

    public int getPort() {
        return port;
    }
    public String getWordToTranslate() {
        return wordToTranslate;
    }
    public String getWord() {
        return word;
    }


}