package Homework6;

import java.io.IOException;

public interface WeatherModel {
    public void getWeather(Period period, String selectedCity) throws IOException;
}
