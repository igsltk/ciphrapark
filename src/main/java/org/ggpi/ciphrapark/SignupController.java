package org.ggpi.ciphrapark;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class SignupController {

	@FXML
	private Button signup_auth_btn;

	@FXML
	private TextField signup_email_field;

	@FXML
	private TextField signup_lastname_field;

	@FXML
	private TextField signup_login_field;

	@FXML
	private TextField signup_name_field;

	@FXML
	private PasswordField signup_password_field;

	@FXML
	private TextField signup_phone_field;

	@FXML
	private Button signup_sign_btn;

	@FXML
	void initialize() {
		DataBaseConnector dbCon = new DataBaseConnector();

		signup_sign_btn.setOnAction(event->{
			String name = signup_name_field.getText().trim();
			String lastname = signup_lastname_field.getText().trim();
			String login = signup_login_field.getText().trim();
			String pass = signup_password_field.getText().trim();
			String email = signup_email_field.getText().trim();
			String phone = signup_phone_field.getText().trim();
			boolean signed = false;
			if (!name.equals("") && !lastname.equals("") &&
				!login.equals("") && !pass.equals("") &&
				!email.equals("") && !phone.equals("")) {
				try {
					String passhash = HashMaker.toHexString(HashMaker.getSHA(pass));
					User user = new User("0",name,lastname,login,passhash,email,phone,"0","0.00");
					signed = dbCon.signupMaker(user);
					if (signed) {
						authWinMake();
					} else {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Ошибка!");
						alert.setHeaderText("Произошла ошибка базы данных!");
						alert.setContentText("Вероятнее всего данный пользователь уже существует!");
						alert.show();
					}
				} catch(Exception e) {
					e.printStackTrace();
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Ошибка!");
					alert.setHeaderText("Произошла ошибка базы данных!");
					alert.setContentText("Сервис временно недоступен!");
					alert.show();
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Ошибка!");
				alert.setHeaderText("Не все поля заполнены!");
				alert.setContentText("Для успешной регистрации нужно заполнить все поля!");
				alert.show();
				return;
			}
		});

		signup_auth_btn.setOnAction(event->{
			authWinMake();
		});
	}

	void authWinMake() {
		signup_sign_btn.getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/auth.fxml"));
		try {
			loader.load();
		} catch(Exception e) {
			e.printStackTrace();
		}
		Parent root = loader.getRoot();
		Stage stage = new Stage();
		stage.setTitle("CiphraPark");
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.showAndWait();
	}
}