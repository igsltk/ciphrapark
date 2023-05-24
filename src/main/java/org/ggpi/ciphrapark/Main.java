package org.ggpi.ciphrapark;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primStage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/auth.fxml"));
			primStage.setTitle("CiphraPark");
			primStage.setScene(new Scene(root));
			primStage.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
		primStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
