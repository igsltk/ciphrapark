package org.ggpi.ciphrapark;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MainFrame {

	@FXML
	private AnchorPane main_anchorpane_parkinfo;

	@FXML
	private Button main_cancel_rent_btn;

	@FXML
	private Label main_info_field;

	@FXML
	private Label main_login_field;

	@FXML
	private Button main_logout_btn;

	@FXML
	private Label main_money_field;

	@FXML
	private Button main_park1_btn;

	@FXML
	private Button main_park2_btn;

	@FXML
	private Button main_park3_btn;

	@FXML
	private Button main_park4_btn;

	@FXML
	private Button main_park5_btn;

	@FXML
	private Button main_park6_btn;

	@FXML
	private Button main_park7_btn;

	@FXML
	private Button main_park8_btn;

	@FXML
	private Label main_park_title;

	@FXML
	private Button main_rent_btn;

	@FXML
	private Button main_report_btn;

	@FXML
	private Button main_set_queue_btn;

	@FXML
	private Label main_spacer;

	private int i;

	private User user = new User();

	private Park[] parks = new Park[8];

	public void setObject(User obj) {
		user = obj;
		i = 0;
		init();
	}

	DataBaseConnector dbCon = new DataBaseConnector();

	public void updateData() {
		try {
			user = dbCon.FillUserData(user.getLogin());
			dbCon.closeConnection();
			for (i=0; i<8; i++) {
				parks[i] = dbCon.FillParkData(i+1);
				dbCon.closeConnection();
			}
		} catch(Exception e) {
			e.printStackTrace();
			authWinMake();
		}
		main_money_field.setText(user.getBalance());
		main_anchorpane_parkinfo.setVisible(false);
	}

	@FXML
	void init() {
		updateData();

		main_login_field.setText(user.getLogin());
		main_money_field.setText(user.getBalance());
		main_set_queue_btn.setDisable(true);
		main_report_btn.setDisable(true);

		main_park1_btn.setOnAction(event->{
			i = 0;
			infoCreate(i);
		});
		main_park2_btn.setOnAction(event->{
			i = 1;
			infoCreate(i);
		});
		main_park3_btn.setOnAction(event->{
			i = 2;
			infoCreate(i);
		});
		main_park4_btn.setOnAction(event->{
			i = 3;
			infoCreate(i);
		});
		main_park5_btn.setOnAction(event->{
			i = 4;
			infoCreate(i);
		});
		main_park6_btn.setOnAction(event->{
			i = 5;
			infoCreate(i);
		});
		main_park7_btn.setOnAction(event->{
			i = 6;
			infoCreate(i);
		});
		main_park8_btn.setOnAction(event->{
			i = 7;
			infoCreate(i);
		});

		main_rent_btn.setOnAction(event->{
			int id = i+1;
			String newOwner = user.getName()+" "+user.getLastName();
			String newOwnerId = user.getId();

			//=======================
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date tomorrow = calendar.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
			String tomorrowString = formatter.format(tomorrow);
			//=======================

			String newExpDate = tomorrowString;
			double bal = Double.parseDouble(user.getBalance());
			int price = Integer.parseInt(parks[i].getPrice());
			String newBal = String.valueOf(bal - price);
			try {
				dbCon.MakeRent(id, newOwner, newOwnerId, newExpDate, newBal);
				dbCon.closeConnection();
				updateData();
			} catch(Exception e) {
				e.printStackTrace();
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Ошибка!");
				alert.setHeaderText("Произошла ошибка базы данных!");
				alert.setContentText("Попробуйте позже!");
				alert.show();
			}
		});
		main_cancel_rent_btn.setOnAction(event->{
			int id = i+1;
			String ownerid = user.getId();
			try {
				dbCon.CancelRent(id, ownerid);
				dbCon.closeConnection();
				updateData();
			} catch(Exception e) {
				e.printStackTrace();
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Ошибка!");
				alert.setHeaderText("Произошла ошибка базы данных!");
				alert.setContentText("Попробуйте позже!");
				alert.show();
			}
		});

		main_logout_btn.setOnAction(event->{
			authWinMake();
		});
	}

	void infoCreate(int place) {
		String id = parks[place].getId();
		String owner = parks[place].getOwner();
		String ownerid = parks[place].getOwnerId();
		String expiredate = parks[place].getExpireDate();
		String price = parks[place].getPrice();
		String specials = parks[place].getSpecials();
		String queue = parks[place].getQueue();
		String userid = user.getId();
		main_anchorpane_parkinfo.setVisible(true);
		main_park_title.setText("ПаркМесто " + id);
		main_info_field.setText("Владелец: " + owner +
			"\nСрок до: " + expiredate +
			"\nЦена: " + price + " руб/день" +
			"\nОсобые примечания: " + specials +
			"\nОчередь на аренду: " + queue);
		if(owner.equals("0")) {
			main_rent_btn.setDisable(false);
		} else {
			main_rent_btn.setDisable(true);
		}
		if(ownerid.equals(userid)) {
			main_cancel_rent_btn.setDisable(false);
		} else {
			main_cancel_rent_btn.setDisable(true);
		}
	}

	void authWinMake() {
		main_logout_btn.getScene().getWindow().hide();
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
		stage.show();
	}
}
