package Homework6;

import java.io.IOException;
import java.sql.SQLException;

public class Controller {
    AccuweatherModel weatherModel = new AccuweatherModel();

    public void getWeather(String command, String selectedCity) throws IOException {
        try {
            switch (Functionality.fromValue(command)){
                case GET_CURRENT_WEATHER:
                    weatherModel.getWeather(Period.NOW, selectedCity);
                    break;
                case GET_WEATHER_IN_NEXT_FIVE_DAYS:
                    weatherModel.getWeather(Period.FIVE_DAYS, selectedCity);
                    break;
                case END:
                    System.exit(0);
                    break;
                case HISTORY_DATA:
                    //код
                    DataBaseRepository dataBaseRepository = new DataBaseRepository();
                    dataBaseRepository.getHistoryFromDB();
                    break;
            }
        } catch (NullPointerException | SQLException e){
            System.out.println("Команда не распознана. Повторите, пожалуйста, ввод.");
        }

    }


}
