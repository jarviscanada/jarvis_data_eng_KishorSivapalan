package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetUtil {

    public static Tweet buildTweet(String txt, Double lat, Double lon) {
        Coordinates coordinates = new Coordinates();
        List<Double> doubleList = new ArrayList<>();
        doubleList.add(lon);
        doubleList.add(lat);
        coordinates.setCoordinates(doubleList);
        Tweet post = new Tweet();
        post.setText(txt);
        post.setCoordinates(coordinates);
        return post;
    }
}
