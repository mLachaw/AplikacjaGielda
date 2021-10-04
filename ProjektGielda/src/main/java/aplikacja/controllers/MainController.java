package aplikacja.controllers;

import java.io.IOException;
import aplikacja.connection.FireBaseInit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainController {

	@FXML
	private StackPane mainStackPane;

	FireBaseInit dodajInit = new FireBaseInit();

	@FXML
	public void initialize() {
		dodajInit.initialize();
		loadDetailScreen();

	}

	public void loadDetailScreen() {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXML/DetailsScreen.fxml"));

		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		DetailsController detailsController = loader.getController();
		detailsController.setMainControler(this);
		setScreen(pane);
	}
	
	public void setScreen(Pane pane){
		mainStackPane.getChildren().clear();
		mainStackPane.getChildren().add(pane);
	}



}
