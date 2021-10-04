package aplikacja.controllers;

import static aplikacja.connection.FireBaseInit.generateUUID;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference.CompletionListener;

import aplikacja.connection.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {
	
    @FXML private TextField emailTextField;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordPaswordField;
    @FXML private PasswordField cPasswordPaswordField;
    
    private MainController mainControler;

	public void setMainControler(MainController mainControler) {
		this.mainControler = mainControler;
	}
    
    
    
    @FXML void cancelRegistarion(ActionEvent event) {
    	mainControler.loadDetailScreen();
    }

    @FXML void registerNewUser(ActionEvent event) {
  //Wyszukiwanie w grupie "Users" czy istnieje u¿ytkownik o loginie i emailu pasuj¹cym do podanego w userTextField i userEmailTextField
		DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
		mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
			boolean thereIsUsser = false;

			@Override
			public void onDataChange(DataSnapshot snapshot) {

				for (DataSnapshot ds : snapshot.getChildren()) {
					User user = ds.getValue(User.class);
					if (user.getUsername().equals(usernameTextField.getText())
							|| user.getEmail().equals(emailTextField.getText())) {
						Alert alert = new Alert(AlertType.CONFIRMATION, "Nazwa u¿ytkownika b¹dz email zajêty");
						alert.showAndWait()
					      .filter(response -> response == ButtonType.OK);
					     
						System.out.println("Nazwa u¿ytkownika b¹dz email zajêty");

						thereIsUsser = true;
					}

				}
//Jeœli nie ma u¿ytkownika o takich danych towrzy w grupie "Users" nowego u¿ytkownika z indywidualnym ID poprzez wywo³anie metody writeNewuser()
				if (!thereIsUsser) {
					String id = generateUUID();
					writeNewUser(id, passwordPaswordField.getText(), usernameTextField.getText(), emailTextField.getText());
				}

			}

			@Override
			public void onCancelled(DatabaseError error) {
				System.out.println(error.getDetails());

			}
		});
    }
    
    
  //Metoda tworzy obiekt kalsy User wykorzystujac podane informacje i zapisuje je w bazie danych
  	private void writeNewUser(String userId, String password, String name, String email) {
  		DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
  		String id = generateUUID();
  		User user = new User(name, password, userId, email);
  		user.setEmail(email);
  		user.setUsername(name);
  		user.setId_user(id);
  		user.setPassword(password);

  		mDatabase.child(id).setValue(user, new CompletionListener() {

  			@Override
  			public void onComplete(DatabaseError error, DatabaseReference ref) {
  				System.out.println("Data Saved");

  			}
  		});

  	}



}

