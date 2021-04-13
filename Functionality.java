package Homework6;

public enum Functionality {
    GET_CURRENT_WEATHER("1"),
    GET_WEATHER_IN_NEXT_FIVE_DAYS("2"),
    END("end"),
    HISTORY_DATA("history");

    public String numberInUserInterface;

    Functionality(String i){
        numberInUserInterface = i;
    }

    public static Functionality fromValue(String value){
        for (Functionality functionality : values()){
            if (functionality.numberInUserInterface.equalsIgnoreCase(value)){
                return functionality;
            }
        }
        return null;
    }
}
