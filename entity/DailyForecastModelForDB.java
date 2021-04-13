package Homework6.entity;

public class DailyForecastModelForDB {
    //(id integer primary key autoincrement, city text, localdate text, tempMin real, tempMax real, textDay text, textNight text);
    private String city;
    private String localDate;
    private double tempMin;
    private double tempMax;
    private String textDay;
    private String textNight;

    public DailyForecastModelForDB() {
    }

    public DailyForecastModelForDB(String city, String localDate, double tempMin, double tempMax, String textDay, String textNight) {
        this.city = city;
        this.localDate = localDate;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.textDay = textDay;
        this.textNight = textNight;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public String getTextDay() {
        return textDay;
    }

    public void setTextDay(String textDay) {
        this.textDay = textDay;
    }

    public String getTextNight() {
        return textNight;
    }

    public void setTextNight(String textNight) {
        this.textNight = textNight;
    }
}
