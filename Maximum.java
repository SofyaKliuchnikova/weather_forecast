package Homework6;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Maximum {
    private float value;
    private String unit;

    public Maximum() {
    }

    @JsonGetter("Value")
    public float getValue() {
        return value;
    }

    @JsonGetter("Unit")
    public String getUnit() {
        return unit;
    }


    public void setValue(float Value) {
        this.value = Value;
    }

    public void setUnit(String Unit) {
        this.unit = Unit;
    }

    @Override
    public String toString() {
        return " максимальная " + value + " °" + unit;
    }
}
