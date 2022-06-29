package zad1;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class ConfirmBox {
    static boolean answer;
    static List<String> input;
    public static boolean display(String title){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);
        Label nameLabel = new Label("Country: ");
        GridPane.setConstraints(nameLabel,0,0);

        TextField country = new TextField("");
        GridPane.setConstraints(country,1,0);

        Label passLabel = new Label("City: ");
        GridPane.setConstraints(passLabel,0,1);

        TextField passInput = new TextField();
        GridPane.setConstraints(passInput,1,1);

        Label codeLabel = new Label("Currency code: ");
        GridPane.setConstraints(codeLabel,0,2);

        TextField codeInput = new TextField();
        GridPane.setConstraints(codeInput,1,2);

        Button loginButton = new Button("Ok");
        GridPane.setConstraints(loginButton,1,3);

        Button cancelButton = new Button("Cancel");
        GridPane.setConstraints(cancelButton,1,4);

        grid.getChildren().addAll(nameLabel,country,passLabel,passInput,codeLabel,codeInput,loginButton,cancelButton);
        Scene scene = new Scene(grid,350,200);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        loginButton.setOnAction(e -> {
            input = new ArrayList<String>();

            input.add(country.getText());
            input.add(passInput.getText());
            input.add(codeInput.getText());
            answer = true;
            window.close();
        });

        cancelButton.setOnAction(e -> {
            answer=false;
            window.close();
        });

        window.setScene(scene);
        window.showAndWait();
        return answer;
    }
}
