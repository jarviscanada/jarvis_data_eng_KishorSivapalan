package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "text",
        "indices"
})
public class Hashtag {

    @JsonProperty("text")
    public String text;
    @JsonProperty("indices")
    public List<Integer> indices;

    @JsonGetter
    public String getText() {
        return text;
    }

    @JsonSetter
    public void setText(String text) {
        this.text = text;
    }

    @JsonGetter
    public List<Integer> getIndices() {
        return indices;
    }

    @JsonSetter
    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }
}