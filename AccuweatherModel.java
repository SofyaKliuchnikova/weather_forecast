package Homework6;

import Homework6.entity.DailyForecastModelForDB;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AccuweatherModel {

    private static final String PROTOCOL = "http";
    private static final String API_KEY = "BgADgfAxi1eSeAG2eeUWYrHNL5Cwlocc";
    private static final String BASE_HOST = "dataservice.accuweather.com";
    private static final String CURRENT_CONDITIONS_ENDPOINT = "currentconditions";
    private static final String API_V1 = "v1";
    private static final String LOCATIONS_ENDPOINT = "locations";
    private static final String CITIES_ENDPOINT = "cities";
    private static final String AUTOCOMPLETE_ENDPOINT = "autocomplete";
    private static final String FORECASTS_ENDPOINT = "forecasts";
    private static final String DAILY_ENDPOINT = "daily";
    private static final String FIVE_DAYS_ENDPOINT = "5day";

    public static final OkHttpClient okHttpClient = new OkHttpClient();
    public static final ObjectMapper objectMapper = new ObjectMapper();

    public void getWeather(Period period, String selectedCity) throws IOException {
        String cityKey = detectCityKey(selectedCity);
        if (period == Period.NOW) {
            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme(PROTOCOL)
                    .host(BASE_HOST)
                    .addPathSegment(CURRENT_CONDITIONS_ENDPOINT)
                    .addPathSegment(API_V1)
                    .addPathSegment(cityKey)
                    .addQueryParameter("apikey", API_KEY)
                    .addQueryParameter("language", "ru-ru")
                    .build();
            Request request = new Request.Builder()
                    .addHeader("accept", "application/json")
                    .url(httpUrl)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            String responseString = response.body().string();

            String forecastWeather =  objectMapper.readTree(responseString).get(0).at("/WeatherText").asText();
            String forecastTemperature =  objectMapper.readTree(responseString).get(0).at("/Temperature")
                    .at("/Metric").at("/Value").asText();
            System.out.println(forecastWeather + ". Текущая температура " + forecastTemperature + " °C.");
        }

        if (period == Period.FIVE_DAYS){
            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme(PROTOCOL)
                    .host(BASE_HOST)
                    .addPathSegment(FORECASTS_ENDPOINT)
                    .addPathSegment(API_V1)
                    .addPathSegment(DAILY_ENDPOINT)
                    .addPathSegment(FIVE_DAYS_ENDPOINT)
                    .addPathSegment(cityKey)
                    .addQueryParameter("apikey", API_KEY)
                    .addQueryParameter("language", "ru-ru")
                    .addQueryParameter("metric", "true")
                    .build();
            Request request = new Request.Builder()
                    .addHeader("accept", "application/json")
                    .url(httpUrl)
                    .build();
            Response response = okHttpClient.newCall(request).execute();

            String responseString = response.body().string();

            String responseHeadlineText = objectMapper.readTree(responseString).at("/Headline/Text").asText();
            System.out.println(responseHeadlineText);

            String dailyForecastsString = objectMapper.readTree(responseString).at("/DailyForecasts").toString();
            List<DailyForecast> dailyForecasts = objectMapper.readValue(dailyForecastsString, new TypeReference<List<DailyForecast>>() {});
            System.out.println(dailyForecasts);

            DailyForecastModelForDB dailyForecastModelForDB1 = new DailyForecastModelForDB();
            dailyForecastModelForDB1.setCity(selectedCity);
            dailyForecastModelForDB1.setLocalDate(dailyForecasts.get(0).getDate());
            dailyForecastModelForDB1.setTempMin(dailyForecasts.get(0).getTemperatureObject().getMinimum().getValue());
            dailyForecastModelForDB1.setTempMax(dailyForecasts.get(0).getTemperatureObject().getMaximum().getValue());
            dailyForecastModelForDB1.setTextDay(dailyForecasts.get(0).getDayObject().getIconPhrase());
            dailyForecastModelForDB1.setTextNight(dailyForecasts.get(0).getNightObject().getIconPhrase());

            DailyForecastModelForDB dailyForecastModelForDB2 = new DailyForecastModelForDB();
            dailyForecastModelForDB2.setCity(selectedCity);
            dailyForecastModelForDB2.setLocalDate(dailyForecasts.get(1).getDate());
            dailyForecastModelForDB2.setTempMin(dailyForecasts.get(1).getTemperatureObject().getMinimum().getValue());
            dailyForecastModelForDB2.setTempMax(dailyForecasts.get(1).getTemperatureObject().getMaximum().getValue());
            dailyForecastModelForDB2.setTextDay(dailyForecasts.get(1).getDayObject().getIconPhrase());
            dailyForecastModelForDB2.setTextNight(dailyForecasts.get(1).getNightObject().getIconPhrase());

            DailyForecastModelForDB dailyForecastModelForDB3 = new DailyForecastModelForDB();
            dailyForecastModelForDB3.setCity(selectedCity);
            dailyForecastModelForDB3.setLocalDate(dailyForecasts.get(2).getDate());
            dailyForecastModelForDB3.setTempMin(dailyForecasts.get(2).getTemperatureObject().getMinimum().getValue());
            dailyForecastModelForDB3.setTempMax(dailyForecasts.get(2).getTemperatureObject().getMaximum().getValue());
            dailyForecastModelForDB3.setTextDay(dailyForecasts.get(2).getDayObject().getIconPhrase());
            dailyForecastModelForDB3.setTextNight(dailyForecasts.get(2).getNightObject().getIconPhrase());

            DailyForecastModelForDB dailyForecastModelForDB4 = new DailyForecastModelForDB();
            dailyForecastModelForDB4.setCity(selectedCity);
            dailyForecastModelForDB4.setLocalDate(dailyForecasts.get(3).getDate());
            dailyForecastModelForDB4.setTempMin(dailyForecasts.get(3).getTemperatureObject().getMinimum().getValue());
            dailyForecastModelForDB4.setTempMax(dailyForecasts.get(3).getTemperatureObject().getMaximum().getValue());
            dailyForecastModelForDB4.setTextDay(dailyForecasts.get(3).getDayObject().getIconPhrase());
            dailyForecastModelForDB4.setTextNight(dailyForecasts.get(3).getNightObject().getIconPhrase());

            DailyForecastModelForDB dailyForecastModelForDB5 = new DailyForecastModelForDB();
            dailyForecastModelForDB5.setCity(selectedCity);
            dailyForecastModelForDB5.setLocalDate(dailyForecasts.get(4).getDate());
            dailyForecastModelForDB5.setTempMin(dailyForecasts.get(4).getTemperatureObject().getMinimum().getValue());
            dailyForecastModelForDB5.setTempMax(dailyForecasts.get(4).getTemperatureObject().getMaximum().getValue());
            dailyForecastModelForDB5.setTextDay(dailyForecasts.get(4).getDayObject().getIconPhrase());
            dailyForecastModelForDB5.setTextNight(dailyForecasts.get(4).getNightObject().getIconPhrase());


            DataBaseRepository dataBaseRepository = new DataBaseRepository();
            try {
                dataBaseRepository.saveWeatherData(dailyForecastModelForDB1);
                dataBaseRepository.saveWeatherData(dailyForecastModelForDB2);
                dataBaseRepository.saveWeatherData(dailyForecastModelForDB3);
                dataBaseRepository.saveWeatherData(dailyForecastModelForDB4);
                dataBaseRepository.saveWeatherData(dailyForecastModelForDB5);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public String detectCityKey(String selectedCity) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(PROTOCOL)
                .host(BASE_HOST)
                .addPathSegment(LOCATIONS_ENDPOINT)
                .addPathSegment(API_V1)
                .addPathSegment(CITIES_ENDPOINT)
                .addPathSegment(AUTOCOMPLETE_ENDPOINT)
                .addQueryParameter("apikey", API_KEY)
                .addQueryParameter("q", selectedCity)
                .build();
        Request request = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(httpUrl)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        String responseString = response.body().string();
        String cityKey =  objectMapper.readTree(responseString).get(0).at("/Key").asText();
        return cityKey;
    }

}
