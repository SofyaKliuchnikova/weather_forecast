package Homework6;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Temperature {
    Minimum minimumObject;
    Maximum maximumObject;

    public Temperature() {
    }

    @JsonGetter("Minimum")
    public Minimum getMinimum() {
        return minimumObject;
    }

    @JsonGetter ("Maximum")
    public Maximum getMaximum() {
        return maximumObject;
    }

    public void setMinimum(Minimum MinimumObject) {
        this.minimumObject = MinimumObject;
    }

    public void setMaximum(Maximum MaximumObject) {
        this.maximumObject = MaximumObject;
    }

    @Override
    public String toString() {
        return ". Температура воздуха: " + minimumObject + maximumObject;
    }
}
