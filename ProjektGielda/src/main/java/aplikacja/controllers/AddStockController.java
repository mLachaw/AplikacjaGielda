package aplikacja.controllers;

import java.io.IOException;
import java.math.BigDecimal;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;

import aplikacja.connection.UserStock;
import aplikacja.main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import javafx.scene.control.Alert.AlertType;

public class AddStockController {

	private MainController mainControler;

	public void setMainControler(MainController mainControler) {
		this.mainControler = mainControler;
	}

	@FXML
	public TextField shortIDTextField;

	@FXML
	public TextField amountTextField;

	@FXML
	public TextField priceTextField;
	public String id_user;
	DatabaseReference mDatabase;
	static boolean needUpdate;

	  @FXML
	    void cancelAdding(ActionEvent event) {

	    }

	@FXML
	void addStockToDatabase(ActionEvent event) {
		saveToDatabase();
	
	}

	public void saveToDatabase() {
		if(shortIDTextField.getText().isEmpty() || amountTextField.getText().isEmpty() || priceTextField.getText().isEmpty()) {
		
			Main.giveSimpleAlert("Uzupe³nij wszystkie pola");
		} else

		createNewStock(shortIDTextField.getText(), amountTextField.getText(), priceTextField.getText());

	}

	private void createNewStock(String shortid, String amount, String price) {
		
		String loggedAs = DetailsController.whoIsLoged();
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference mDatabase = database.getReference("Akcje/" + loggedAs);
		UserStock stock = new UserStock(shortid, amount, price);
		stock.setShortid(shortid);
		stock.setAmount(amount);
		stock.setPrice(price);
		Main.giveSimpleAlert("Akcje "+shortIDTextField.getText()+" w iloœci: "+amountTextField.getText() +" zapisano w bazie danych" +"\n"+ "Œrednia cena za akcje: "+priceTextField.getText());
		
		mDatabase.child(shortid).setValue(stock, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				System.out.println("Data Saved");
				
			}
		});
	}
	
	

	
	

}
