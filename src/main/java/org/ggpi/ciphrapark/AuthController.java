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

public class AuthController {

	@FXML
	private Button auth_auth_btn;

	@FXML
	private TextField auth_login_field;

	@FXML
	private PasswordField auth_pass_field;

	@FXML
	private Button auth_signup_btn;

	private User user = new User();

	@FXML
	void initialize() {
		DataBaseConnector dbCon = new DataBaseConnector();

		auth_auth_btn.setOnAction(event->{
			String login = auth_login_field.getText().trim();
			String pass = auth_pass_field.getText().trim();
			boolean authed = false;
			if (!login.equals("") && !pass.equals("")) {
				try {
					String passhash = HashMaker.toHexString(HashMaker.getSHA(pass));
					user.setLogin(login);
					user.setPassword(passhash);
					authed = dbCon.authMaker(user);
					if (authed) {
						mainWinMake();
					} else {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Ошибка!");
						alert.setHeaderText("Произошла ошибка базы данных!");
						alert.setContentText("Вероятнее всего данного пользователя не существует!");
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
		auth_signup_btn.setOnAction(event->{
			signWinMake();
		});
	}

	@FXML
	void mainWinMake() {
		auth_auth_btn.getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
		try {
			loader.load();
		} catch(Exception e) {
			e.printStackTrace();
		}
		MainFrame mainF = loader.getController();
		mainF.setObject(user);
		Parent root = loader.getRoot();
		Stage stage = new Stage();
		stage.setTitle("CiphraPark");
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.show();
	}

	@FXML
	void signWinMake() {
		auth_auth_btn.getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/signup.fxml"));
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
