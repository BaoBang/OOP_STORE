package ConnectDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDatabse {
	private final String className = "com.mysql.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/db_shop?autoReconnect=true&useSSL=false";
	private final String user = "root";
	private final String pass = "2211321";
	public static Connection connection;
	private String sqlCommand = "";
	private String sqlStatement = "";

	public void connect() {
		try {
			Class.forName(className);
			connection = DriverManager.getConnection(url, user, pass);
			System.out.println("connect success");

		} catch (ClassNotFoundException e) {
			System.out.println("Class not found");

		} catch (SQLException e) {
			System.out.println("Error");
		}
	}

	public void setSqlStatement(String st) {
		sqlStatement = st;
	}

	public void setSqlCommand(String sql) {
		sqlCommand = sql;
	}

	public ResultSet getData() {
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlCommand);
			if (resultSet.isBeforeFirst())
				return resultSet;
		} catch (SQLException e) {
			System.out.println("Select Eror");
		}

		return null;
	}

	public ResultSet getColum(String tableName, String colum) {
		String sql = "select " + colum + " from " + tableName;
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if (resultSet.isBeforeFirst())
				return resultSet;
		} catch (SQLException e) {
			System.out.println("Select Eror");
		}
		return null;
	}

	public ResultSet getOrderDetail(String colum, int order_id) {
		String sqlCommand = "select " + colum + " from order_details" + " where order_id = ?";
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sqlCommand);
			preparedStatement.setInt(1, order_id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.isBeforeFirst())
				return resultSet;
		} catch (SQLException e) {
			System.out.println("Select order detail Eror");
		}
		return null;
	}

	public ResultSet getDataId(int id, String tableName) {
		ResultSet resultSet = null;
		String sqlCommand = "select * from " + tableName + " where id = ?";
		PreparedStatement preparedStatement = null;
		try {

			preparedStatement = connection.prepareStatement(sqlCommand);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.isBeforeFirst()) {
				resultSet.next();
				return resultSet;
			}
		} catch (SQLException e) {
			System.out.println("Select Id Eror");
		}

		return null;
	}

	public ResultSet getDataSearchCategory(String key) {
		ResultSet resultSet = null;
		String sqlCommand = "select * from categories where name like '%" + key + "%' or id like '%" + key + "%'";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sqlCommand);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.isBeforeFirst())
				return resultSet;
		} catch (SQLException e) {
			System.out.println("Select Error");
		}

		return null;
	}

	public ResultSet getDataSearchBrand(String key) {
		ResultSet resultSet = null;
		String sqlCommand = "select * from brands where name like '%" + key + "%' or id like '%" + key + "%'";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sqlCommand);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.isBeforeFirst())
				return resultSet;
		} catch (SQLException e) {
			System.out.println("Select Error");
		}

		return null;
	}

	public ResultSet getDataSearchUser(String key) {
		ResultSet resultSet = null;
		String sqlCommand = "select * from users where email like '%" + key + "%' or name like '%" + key
				+ "%' or phone like '%" + key + "%' or address like '%" + key + "%'";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sqlCommand);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.isBeforeFirst())
				return resultSet;
		} catch (SQLException e) {
			System.out.println("Select Error");
		}

		return null;
	}

	public ResultSet getDataSearchOrder(String key) {
		ResultSet resultSet = null;
		String sqlCommand = "select orders.id, users.name, orders.total, users.phone, orders.address, orders.created_at, status from orders inner join users on users.id = user_id where orders.id like '%" + key
				+ "%' or users.name like '%" + key + "%' or orders.address like '%" + key + "%' or users.phone like '%"
				+ key + "%'";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sqlCommand);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.isBeforeFirst())
				return resultSet;
		} catch (SQLException e) {
			System.out.println("Select Error");
		}

		return null;
	}

	public ResultSet getDataSearchProduct(String key) {
		ResultSet resultSet = null;
		String sqlCommand = "select products.id, products.image, products.name, model, categories.name, brands.name, stock, price, discount, created_at "
				+ "from products " + "inner join brands on brand_id = brands.id "
				+ "inner join categories on category_id = categories.id " + "where products.name like '%" + key + "%'"
				+ "or brands.name like '%" + key + "%'" + "or categories.name like '%" + key + "%'"
				+ "or products.model like '%" + key + "%'";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sqlCommand);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.isBeforeFirst())
				return resultSet;
		} catch (SQLException e) {
			System.out.println("Select Error");
		}

		return null;
	}

	public ResultSet getDataName(String name, String colum, String tableName) {
		ResultSet resultSet = null;
		String sqlCommand = "select * from " + tableName + " where " + colum + " = ?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sqlCommand);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.isBeforeFirst()) {
				resultSet.next();
				return resultSet;
			}
		} catch (SQLException e) {
			System.out.println("Select name Eror");
		}

		return null;
	}

	public boolean checkData(String key, String value, String tableName) {
		ResultSet resultSet = null;
		String sqlCommand = "select * from " + tableName + " where " + key + " = ?";
		PreparedStatement preparedStatement = null;
		try {

			preparedStatement = connection.prepareStatement(sqlCommand);
			preparedStatement.setString(1, value);
			resultSet = preparedStatement.executeQuery();

		} catch (SQLException e) {
			System.out.println("Select name Eror");
		}

		try {
			if (resultSet.next())
				return true;
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return false;
	}

	public void deleteId(String id) {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
