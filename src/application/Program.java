package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DB.getConnection();
			
			preparedStatement = connection.prepareStatement(
					"INSERT INTO seller"
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ "VALUES"
					+"(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, "José Carlos");
			preparedStatement.setString(2, "jccfjr@gmail.com");
			preparedStatement.setDate(3, new java.sql.Date(simpleDateFormat.parse("04/01/1981").getTime()));
			preparedStatement.setDouble(4, 2900.0);
			preparedStatement.setInt(5, 3);
			
			preparedStatement = connection.prepareStatement(
					"insert into department  (Name) values (D1), (D2)",
					Statement.RETURN_GENERATED_KEYS);
			
			int rowsAffected = preparedStatement.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				while(resultSet.next()) {
					int id = resultSet.getInt(1);
					System.out.println("Feito! Id: " + id);
				}
			}else {
				System.out.println("Nenhuma linha afetada.");
			}
			
			System.out.println("Feito! Linhas afetadas: " + rowsAffected);
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (ParseException e) {
			e.printStackTrace();
		}finally {
			DB.closeStatement(preparedStatement);
			DB.closeConnection();
		}
	}
}