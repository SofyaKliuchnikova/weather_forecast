package Homework6;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Day {
    private String iconPhrase;

    public Day() {
    }

    @JsonGetter("IconPhrase")
    public String getIconPhrase() {
        return iconPhrase;
    }

    public void setIconPhrase(String IconPhrase) {
        this.iconPhrase = IconPhrase;
    }

    @Override
    public String toString() {
        return ". Днём: " + iconPhrase;
    }
}
