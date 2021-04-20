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
import java.util.ArrayList;
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
            DailyForecastModelForDB dailyForecastModelForDB2 = new DailyForecastModelForDB();
            DailyForecastModelForDB dailyForecastModelForDB3 = new DailyForecastModelForDB();
            DailyForecastModelForDB dailyForecastModelForDB4 = new DailyForecastModelForDB();
            DailyForecastModelForDB dailyForecastModelForDB5 = new DailyForecastModelForDB();

            ArrayList<DailyForecastModelForDB> dailyForecastModelForDBList = new ArrayList<>();
            dailyForecastModelForDBList.add(dailyForecastModelForDB1);
            dailyForecastModelForDBList.add(dailyForecastModelForDB2);
            dailyForecastModelForDBList.add(dailyForecastModelForDB3);
            dailyForecastModelForDBList.add(dailyForecastModelForDB4);
            dailyForecastModelForDBList.add(dailyForecastModelForDB5);

            for (int i = 0; i < 5; i++){
                dailyForecastModelForDBList.get(i)
                        .setCity(selectedCity)
                        .setLocalDate(dailyForecasts.get(i).getDate())
                        .setTempMin(dailyForecasts.get(i).getTemperatureObject().getMinimum().getValue())
                        .setTempMax(dailyForecasts.get(i).getTemperatureObject().getMaximum().getValue())
                        .setTextDay(dailyForecasts.get(i).getDayObject().getIconPhrase())
                        .setTextNight(dailyForecasts.get(i).getNightObject().getIconPhrase());
            }

            DataBaseRepository dataBaseRepository = new DataBaseRepository();
            try {
                for (int i = 0; i < 5; i++){
                    dataBaseRepository.saveWeatherData(dailyForecastModelForDBList.get(i));
                }
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
