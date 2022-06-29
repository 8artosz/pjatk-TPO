package zad1;

import Models.ClientModel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.io.*;
import java.net.*;

public class Client extends Application   {

    private static ServerSocket SocketReceived;
    public static ClientModel cm;
    private static String data;

    private static String word = null;
    private static String languageCode = null;


    public static void main(String[] args) throws  IOException, ClassNotFoundException, InterruptedException {

        InetAddress host = InetAddress.getLocalHost();
        int port = Integer.parseInt(args[0]);
        Thread gui = new Thread(() -> Application.launch(args));

        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        setCode(null);
        setWord(null);
        setData(null);


        while (true) {

            if (gui.isAlive()) {

            }
            else {
                gui.start();
            }


            while (getWord()==null && getCode()==null) {
                Thread.sleep(150);
            }

            socket = new Socket(host.getHostName(), port);

            oos = new ObjectOutputStream(socket.getOutputStream());


            cm = new ClientModel(getWord(), getCode(), socket.getLocalPort());
            cm.setHost(host);
            oos.writeObject(cm);
            oos.close();

            if (socket.isBound()) {
                socket.close();
            }

            SocketReceived = new ServerSocket(cm.getPort());
            Socket socketReceived = SocketReceived.accept();
            ObjectInputStream oiss = new ObjectInputStream(socketReceived.getInputStream());
            data = (String) oiss.readObject();
            oiss.close();
            setData(data);
            Thread.sleep(150);

            if (socketReceived.isBound()) {
                socketReceived.close();
            }

            setWord(null);
            setCode(null);
            setData(null);
        }

    }
    @Override
    public void start (Stage primaryStage)   {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);
        TextField textFieldTranslate = new TextField();
        TextField textFieldCode = new TextField();
        TextField text = new TextField();
        Label label_translated = new Label("Tlumaczenie");
        Label label_code = new Label("Kod języka");
        Label label_word = new Label("Słowo do przetłumaczenia");
        Button button = new Button("Przetłumacz");

        GridPane.setConstraints(textFieldTranslate,1,0);
        GridPane.setConstraints(label_word,0,0);
        GridPane.setConstraints(button,1,3);
        GridPane.setConstraints(label_code,0,1);
        GridPane.setConstraints(textFieldCode,1,1);
        GridPane.setConstraints(label_translated,0,2);
        GridPane.setConstraints(text,1,2);


        grid.getChildren().addAll(textFieldTranslate,label_word,button,label_code,textFieldCode,label_translated,text);
        Scene scene = new Scene(grid, 350, 150);
        primaryStage.setTitle("Tłumacz");
        primaryStage.setScene(scene);
        primaryStage.show();

        button.setOnAction(event -> {
            word = textFieldTranslate.getText();
            languageCode = textFieldCode.getText().toString().toLowerCase();
            setCode(languageCode);
            setWord(word);

            while(getData() == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            text.setText(getData());

        });

    }

    public static String getWord() {
        return word;
    }

    public static String getCode() {
        return languageCode;
    }

    public static void setWord(String word) {
        Client.word = word;
    }

    public static void setCode(String langCode) {
        Client.languageCode = langCode;
    }

    public static String getData() {
        return data;
    }

    public static void setData(String dataReceived) {
        Client.data = dataReceived;
    }
}


