package aplikacja.controllers;

import java.io.IOException;




import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import aplikacja.connection.FireBaseInit;
import aplikacja.connection.UserStock;
import aplikacja.main.Main;

import static aplikacja.connection.FireBaseInit.generateUUID;
import aplikacja.connection.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.ButtonType;

import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;

import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class DetailsController {

	private MainController mainControler;

	public void setMainControler(MainController mainControler) {
		this.mainControler = mainControler;
	}

	@FXML
	private ListView<String> listaAkcji;

	@FXML
	private HBox loginHbox;

	@FXML
	private TextField usserTextField;

	@FXML
	private PasswordField passwordTextField;

	@FXML
	private SplitPane splitPane;

	@FXML
	private AnchorPane loginZone;

	@FXML
	private AnchorPane workZone;

	@FXML
	private Button dodajAkcjeButton;

	@FXML
	private Button saveButton;

	@FXML
	private AnchorPane workPane;

	private static String loggedAs;

	public DetailsController() {

	}

	@FXML
	private TextField nazwaAkcji;
	Boolean whereIsMouse;
	String[] akcje;
	int akcjeLenght;

	private DatabaseReference mDatabase;

	FireBaseInit zapiszAkcje = new FireBaseInit();

	@FXML
	public void initialize() {

	}

	private void getUserStock() {
		mDatabase = FirebaseDatabase.getInstance().getReference("Akcje/" + loggedAs);
		mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

			ObservableList<String> names = FXCollections.observableArrayList();

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				for (DataSnapshot ds : snapshot.getChildren()) {
					UserStock stock = ds.getValue(UserStock.class);

					names.add(stock.getShortid());
				}
				listaAkcji.setItems(names);

			}

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub

			}
		});

	}

	public static String whoIsLoged() {
		return loggedAs;

	}

	@FXML
	void dodajAkcje(ActionEvent event) throws IOException {

		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXML/AddStockScreen.fxml"));
		Pane pane = loader.load();
		AddStockController appStockController = loader.getController();
		appStockController.setMainControler(mainControler);
		workPane.getChildren().clear();
		workPane.getChildren().add(pane);

	}

	@FXML
	void refreshStocks(ActionEvent event) throws IOException {
		mDatabase = FirebaseDatabase.getInstance().getReference("Akcje/" + loggedAs);
		mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

			ObservableList<String> names = FXCollections.observableArrayList();

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				for (DataSnapshot ds : snapshot.getChildren()) {
					UserStock stock = ds.getValue(UserStock.class);

					names.add(stock.getShortid());
				}
				listaAkcji.setItems(names);

			}

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub

			}
		});

	}

//		private void findDataInYahoo(String tag){
//			yahoofinance.Stock stock = YahooFinance.get(tag);
//			 
//			
//			BigDecimal price = stock.getQuote(true).getPrice();
//			 
//			System.out.println("Price: "+price);
//		
//			
//		}

//	@FXML
//	void zapiszAkcje(ActionEvent event1) {
//
//		//ObservableList selectedIndices = listaAkcji.getSelectionModel().getSelectedItems();
//		listaAkcji.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//		listaAkcji.getSelectionModel().selectAll();
//
//		//System.out.println(selectedIndices);
//		// zapisz("" + selectedIndices);
//
//	}

//	private void zapisz(String akcje) {
//
//		FirebaseDatabase database = FirebaseDatabase.getInstance();
//		DatabaseReference mDatabase = database.getReference("Users/" + loggedAs + "/Akcje");
//
//		mDatabase.child("Akcje").setValue(akcje, loggedAs, new DatabaseReference.CompletionListener() {
//
//			@Override
//			public void onComplete(DatabaseError error, DatabaseReference ref) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//	}

//Metoda obs³uguj¹ca logowanie u¿ytkownika wywo³ywana po naciœniêciu przycisku Login
	@FXML
	void loginButton(ActionEvent event) {

//Ustawienie wyszukiwania w grupie "Users"

		mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
		mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
			boolean thereIsUsser = false;
			String id;

//Wyszukiwanie w grupie "Users" czy istnieje u¿ytkownik o loginie i haœle pasuj¹cym do podanych w userTextField i passwordTextField

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				for (DataSnapshot ds : snapshot.getChildren()) {
					User user = ds.getValue(User.class);
					if (user.getUsername().equals(usserTextField.getText())
							&& user.getPassword().equals(passwordTextField.getText())) {
						thereIsUsser = true;
						id = user.getId_user();
						break;
					}
				}
//Jeœli u¿ytkownik istnieje to odblokowuje obszar roboczy i œci¹ga informacje o u¿ytkowniku 
				
				if (thereIsUsser) {

					workZone.setDisable(false);
					loggedAs = id;
					getUserStock();

//Jeœli u¿ytkownik nie istnieje informuje ¿e nie ma takiego u¿ytkownika
				} else {
					
					
				}

			}

			@Override
			public void onCancelled(DatabaseError error) {
				System.out.println(error.getDetails());
				

			}
		});

	}

	
//Metoda weryfikuj¹ca rejestracje nowego u¿ytkownika wywo³ywana po naciœnieciu przycisku Register
	@FXML
	void registerAction(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXML/RegisterScreen.fxml"));

		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		RegisterController registerController = loader.getController();
		registerController.setMainControler(mainControler);
		workPane.getChildren().clear();
		workPane.getChildren().add(pane);
		workZone.setDisable(false);
		
		
	}



//Metoda wywo³ywana po naciœniêciu przycisku Usuñ Konto
	@FXML
	void deleteAccount(ActionEvent event) {

		removeUserData();

	}

//Metoda usuwajaca dane/konto u¿ytkownika po podaniu has³a i nazwy u¿ytkownika
	private void removeUserData() {
		mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
		mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				for (DataSnapshot ds : snapshot.getChildren()) {
					User user = ds.getValue(User.class);
					if (user.getUsername().equals(usserTextField.getText())
							&& user.getPassword().equals(passwordTextField.getText())) {

						String id = user.getId_user();
						mDatabase.child(id).removeValueAsync();
						System.out.println("Konto "+user.getUsername()+" usuniêto pomyœlnie");

					} 
				}

			}
			
			

			@Override
			public void onCancelled(DatabaseError error) {
				System.out.println(error.getDetails());
				

			}
		});
	}
	
	
	

}
