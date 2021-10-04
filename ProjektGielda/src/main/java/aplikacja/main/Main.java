package aplikacja.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
		

		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/FXML/MainScreen.fxml"));
		StackPane stackPane = loader.load();

		Scene scene = new Scene(stackPane, 800, 600);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Gielda");
		primaryStage.setResizable(false);
		primaryStage.show();

	}
	
	public static void giveSimpleAlert(String message) {
		Alert alert = new Alert(null, message, ButtonType.OK);
		alert.initStyle(StageStyle.UTILITY);
		alert.showAndWait().ifPresent(response -> {
		     if (response == ButtonType.OK) {
		    	
		        
		     }
		 });
	}

}
