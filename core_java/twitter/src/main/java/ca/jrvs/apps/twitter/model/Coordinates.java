package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "coordinates",
        "type"
})
public class Coordinates {

    @JsonProperty("coordinates")
    public List<Double>  coordinates;
    @JsonProperty("type")
    public String type;

    @JsonGetter
    public List<Double> getCoordinates() {
        return coordinates;
    }

    @JsonSetter
    public void setCoordinates(List<Double>  coordinates) {
        this.coordinates = coordinates;
    }

    @JsonGetter
    public String getType() {
        return type;
    }

    @JsonSetter
    public void setType(String type) {
        this.type = type;
    }
}