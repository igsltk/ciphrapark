package org.ggpi.ciphrapark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataBaseConnector extends AppConfig {
	Connection dbConnection;

	public Connection getConnection()
			throws ClassNotFoundException, SQLException {
		String con = "jdbc:mysql://" + dbHost+":"+dbPort+"/"+dbName;
		Class.forName("com.mysql.jdbc.Driver");
		dbConnection = DriverManager.getConnection(con,dbUser,dbPass);

		return dbConnection;
	}
	public void closeConnection() throws SQLException {
		if (dbConnection != null) {
			dbConnection.close();
		}
	}

	public boolean authMaker(User user) {
		ResultSet resSet = null;
		String select = "SELECT * FROM " + USER_TABLE +
			" WHERE " + USERS_LOGIN + "=? AND " +
			USERS_PASS + "=?";
		try {
			PreparedStatement prSt = getConnection().prepareStatement(select);
			prSt.setString(1,user.getLogin());
			prSt.setString(2,user.getPassword());
			resSet = prSt.executeQuery();
			int rows=0;
			try {
				while(resSet.next()) {
					rows++;
				}
				if (rows >= 1) {
					return true;
				}
			} catch(SQLException e) {
				e.printStackTrace();
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	public boolean signupMaker(User user) {
		String ins = "INSERT INTO " + USER_TABLE + " (" +
			USERS_LOGIN + "," + USERS_PASS + "," + USERS_NAME + "," +
			USERS_LASTNAME + "," + USERS_EMAIL + "," + USERS_PHONE +
			") VALUES(?,?,?,?,?,?)";
		try {
			PreparedStatement prSt = getConnection().prepareStatement(ins);
			prSt.setString(1,user.getLogin());
			prSt.setString(2,user.getPassword());
			prSt.setString(3,user.getName());
			prSt.setString(4,user.getLastName());
			prSt.setString(5,user.getEmail());
			prSt.setString(6,user.getPhone());
			prSt.executeUpdate();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Park FillParkData(int id) {
		Park park = new Park();
		ResultSet resSet = null;
		String select = "SELECT * FROM " + PARK_TABLE +
			" WHERE " + PARKS_ID + "=?";
		try {
			PreparedStatement prSt = getConnection().prepareStatement(select);
			prSt.setInt(1, id);
			resSet = prSt.executeQuery();
			if (resSet.next()) {
				park.setId(resSet.getString(PARKS_ID));
				park.setOwner(resSet.getString(PARKS_OWNER));
				park.setOwnerId(resSet.getString(PARKS_OWNERID));
				park.setExpireDate(resSet.getString(PARKS_EXPIREDATE));
				park.setPrice(resSet.getString(PARKS_PRICE));
				park.setSpecials(resSet.getString(PARKS_SPECIALS));
				park.setQueue(resSet.getString(PARKS_QUEUE));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return park;
	}
	public User FillUserData(String login) {
		User user = new User();
		ResultSet resSet = null;
		String select = "SELECT * FROM " + USER_TABLE +
			" WHERE " + USERS_LOGIN + " =?";
		try {
			PreparedStatement prSt = getConnection().prepareStatement(select);
			prSt.setString(1, login);
			resSet = prSt.executeQuery();
			if (resSet.next()) {
				user.setId(resSet.getString(USERS_ID));
				user.setLogin(resSet.getString(USERS_LOGIN));
				user.setName(resSet.getString(USERS_NAME));
				user.setLastName(resSet.getString(USERS_LASTNAME));
				user.setEmail(resSet.getString(USERS_EMAIL));
				user.setPhone(resSet.getString(USERS_PHONE));
				user.setParks(resSet.getString(USERS_PARKS));
				user.setBalance(resSet.getString(USERS_BALANCE));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return user;
	}

	public void MakeRent(int id, String newOwner, String newOwnerId, String newExpDate, String newBal) {
		String up = "UPDATE " + PARK_TABLE + " SET " +
			PARKS_OWNER + " = ?, " +
			PARKS_OWNERID + " = ?, " +
			PARKS_EXPIREDATE + " = ? " +
			" WHERE " + PARKS_ID + "=?";
		try {
			PreparedStatement prSt = getConnection().prepareStatement(up);
			prSt.setString(1, newOwner);
			prSt.setString(2, newOwnerId);
			prSt.setString(3, newExpDate);
			prSt.setInt(4, id);
			int rowsUpdated = prSt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			closeConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		up = "UPDATE " + USER_TABLE + " SET " +
			USERS_PARKS + " = ?, " +
			USERS_BALANCE + " = ? " +
			" WHERE " + USERS_ID + "=?";
		try {
			PreparedStatement prSt = getConnection().prepareStatement(up);
			prSt.setInt(1, id);
			prSt.setString(2, newBal);
			prSt.setString(3, newOwnerId);
			int rowsUpdated = prSt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void CancelRent(int id, String ownerid) {
		String up = "UPDATE " + PARK_TABLE + " SET " +
			PARKS_OWNER + " = ?, " +
			PARKS_OWNERID + " = ? " +
			" WHERE " + PARKS_ID + "=?";
		try {
			PreparedStatement prSt = getConnection().prepareStatement(up);
			prSt.setInt(1, 0);
			prSt.setInt(2, 0);
			prSt.setInt(3, id);
			int rowsUpdated = prSt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			closeConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}

		up = "UPDATE " + USER_TABLE + " SET " +
			USERS_PARKS + " = ? " +
			" WHERE " + USERS_ID + "=?";
		try {
			PreparedStatement prSt = getConnection().prepareStatement(up);
			prSt.setInt(1, 0);
			prSt.setString(2, ownerid);
			int rowsUpdated = prSt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}