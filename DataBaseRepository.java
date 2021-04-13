package Homework6;

import Homework6.entity.DailyForecastModelForDB;

import java.sql.*;
import java.util.List;

public class DataBaseRepository {
    private static final String DB_NAME = "geekbrains.db";
    String insert_db = "insert into dailyForecasts (city, localdate, tempMin, tempMax, textDay, textNight) values (?, ?, ?, ?, ?, ?)";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean saveWeatherData(DailyForecastModelForDB dailyForecastModelForDB) throws SQLException {
        Connection connection = null;
        PreparedStatement saveDailyForecast = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:geekbrains.db");
            saveDailyForecast = connection.prepareStatement(insert_db);
            saveDailyForecast.setString(1, dailyForecastModelForDB.getCity());
            saveDailyForecast.setString(2, dailyForecastModelForDB.getLocalDate());
            saveDailyForecast.setDouble(3, dailyForecastModelForDB.getTempMin());
            saveDailyForecast.setDouble(4, dailyForecastModelForDB.getTempMax());
            saveDailyForecast.setString(5, dailyForecastModelForDB.getTextDay());
            saveDailyForecast.setString(6, dailyForecastModelForDB.getTextNight());
            return saveDailyForecast.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            saveDailyForecast.close();
            connection.close();
        }
        throw new SQLException("Сохранение прогноза в базу данных не выполнено!");
    }

    public void getHistoryFromDB () throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:geekbrains.db");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from dailyForecasts");
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String city = resultSet.getString(2);
                String localDate = resultSet.getString(3);
                Float tempMin = resultSet.getFloat(4);
                Float tempMax = resultSet.getFloat(5);
                String textDay = resultSet.getString(6);
                String textNight = resultSet.getString(7);
                System.out.printf("%s. %s %s %.1f %.1f %s. %s. \n", id, city, localDate, tempMin, tempMax, textDay, textNight);
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            statement.close();
            connection.close();
        }
    }

}
