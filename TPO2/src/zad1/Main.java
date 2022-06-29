/**
 *
 *  @author Wasilewski Bartosz S20296
 *
 */

package zad1;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {
  Button button;
  Scene scene;
  private static Service s;
  private static String weatherJson;
  private static Double rate1;
  private static Double rate2;
 static String country,city;
  public static void main(String[] args) {
    s = new Service("Canada");
    weatherJson = s.getWeather("Toronto");
    rate1 = s.getRateFor("USD");
    rate2 = s.getNBPRate();
    launch(args);
  }
  public void prepareScene(Stage primaryStage){
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10,10,10,10));
    grid.setVgap(8);
    grid.setHgap(10);
    TextArea ta = new TextArea(weatherJson);
    ta.setPrefColumnCount(10);
    ta.setPrefRowCount(7);
    ta.setWrapText(true);
    GridPane.setConstraints(ta,0,1);
    Label nameLabel = new Label("Weather");
    GridPane.setConstraints(nameLabel,0,0);
    button = new Button("Change");
    GridPane.setConstraints(button,3,1);

    Label rateLabel = new Label("Currency rate");
    GridPane.setConstraints(rateLabel,1,0);
    Label PLNLabel = new Label("PLN rate");
    GridPane.setConstraints(PLNLabel,2,0);

    TextArea ta1 = new TextArea(Double.toString(rate1));
    ta1.setPrefColumnCount(6);
    ta1.setWrapText(true);
    ta1.setMaxHeight(150);
    GridPane.setConstraints(ta1,1,1);

    TextArea ta2 = new TextArea(Double.toString(rate2));
    ta2.setPrefColumnCount(6);
    ta2.setWrapText(true);
    ta2.setMaxHeight(150);
    GridPane.setConstraints(ta2,2,1);
    WebView webView = new WebView();
    webView.setPrefSize(1200,500);

    GridPane.setConstraints(webView,0,3);
    WebEngine webEngine = webView.getEngine();
    webEngine.load(s.getPage("Toronto"));


    button.setOnAction(e -> {
      boolean result = ConfirmBox.display("Input parametres in english");
      try {
        if(result == true) {
          s = new Service(ConfirmBox.input.get(0));
          weatherJson = s.getWeather(ConfirmBox.input.get(1));
          rate1 = s.getRateFor(ConfirmBox.input.get(2));
          rate2 = s.getNBPRate();
          webEngine.load(s.getPage(ConfirmBox.input.get(1)));

          ta.setText(weatherJson);
          ta1.setText(Double.toString(rate1));
          ta2.setText(Double.toString(rate2));
        }
      }
      catch(NullPointerException s){
        s.getMessage();
        System.out.println("You did not provide arguments");
      }

    });

    grid.getChildren().addAll(nameLabel,button,ta,rateLabel,PLNLabel,ta1,ta2,webView);
    scene = new Scene(grid,1510,700);

  }
  @Override
  public void start(Stage primaryStage) throws Exception{
    prepareScene(primaryStage);

    primaryStage.setTitle("City Info");
    primaryStage.setScene(scene);
    primaryStage.show();
  }


}
