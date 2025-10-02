import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private TextField celsiusField = new TextField();
    private Label resultLabel = new Label();
    private double fahrenheit;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        celsiusField.setPromptText("Enter Celsius");

        Button convertButton = new Button("Convert");
        convertButton.setOnAction(e -> convertTemperature());

        Button convertButtonFtoC = new Button("ConvertFahrenheitToCelsius");
        convertButtonFtoC.setOnAction(e -> convertFahrenheitToCelsius());

        Button convertButtonCtoF= new Button("ConvertCelsiusToFahrenheit");
        convertButtonCtoF.setOnAction(e ->convertCelsiusToFahrenheit());

        Button saveButton = new Button("Save to DB");
        saveButton.setOnAction(e -> Database.saveTemperature(
                Double.parseDouble(celsiusField.getText()), fahrenheit, resultLabel));

        VBox root = new VBox(10, celsiusField, convertButtonFtoC, convertButtonCtoF,resultLabel, saveButton);
        Scene scene = new Scene(root, 300, 200);

        stage.setTitle("Celsius to Fahrenheit");
        stage.setScene(scene);
        stage.show();
    }

    private void convertTemperature() {
        try {
            double celsius = Double.parseDouble(celsiusField.getText());
            fahrenheit = (celsius * 9 / 5) + 32;
            resultLabel.setText(String.format("Fahrenheit: %.2f", fahrenheit));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input!");
        }
    }

    private void convertFahrenheitToCelsius() {
        try {
            double fahrenheit = Double.parseDouble(celsiusField.getText());
            double celsius = (fahrenheit - 32) * 5.0 / 9.0;
            resultLabel.setText(String.format("Celsius: %.2f", celsius));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input!");
        }
    }

    private void convertCelsiusToFahrenheit() {
        try {
            double celsius = Double.parseDouble(celsiusField.getText());
            double fahrenheit = (celsius * 9.0 / 5.0) + 32;
            resultLabel.setText(String.format("Fahrenheit: %.2f", fahrenheit));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input!");
        }
    }

    public boolean isExtremeTemperature(double celsius) {
        return celsius < -40 || celsius > 50;
    }

    public double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }
}