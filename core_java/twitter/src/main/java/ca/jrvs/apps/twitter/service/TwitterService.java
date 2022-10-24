package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@org.springframework.stereotype.Service
public class TwitterService implements Service{

    private CrdDao dao;

    private static final int TWEET_LIMIT = 280;
    private static final float LONG_MIN = -180;
    private static final float LONG_MAX = 80;
    private static final float LAT_MIN = -90;
    private static final float LAT_MAX = 90;

    @Autowired
    public TwitterService(CrdDao dao) {
        this.dao = dao;
    }

    @Override
    public Tweet postTweet(Tweet tweet){
        validatePostTweet(tweet);
        return (Tweet) dao.create(tweet);
    }

    @Override
    public Tweet showTweet(String id, String[] fields){
        validateShowTweet(id, fields);
        return (Tweet) dao.findById(id);
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids){
        for(String id : ids){
            validateId(id);
        }

        List<Tweet> deletedTweets = new ArrayList<Tweet>();
        for(String id : ids){
            deletedTweets.add((Tweet)dao.deleteById(id));
        }

        return deletedTweets;
    }

    public void validateShowTweet(String id, String[] fields){
        String[] validFields = {
                "created_at",
                "id",
                "id_str",
                "text",
                "entities",
                "coordinates",
                "retweet_count",
                "favorite_count",
                "favorited",
                "retweeted"
        };

        validateId(id);

        if(fields!=null) {
            ArrayList<String> validFieldsList = new ArrayList(Arrays.asList(validFields));
            ArrayList<String> invalidFields = new ArrayList<String>();
            for(String field : fields){
                if(!validFieldsList.contains(field)){
                    invalidFields.add(field);
                }
            }
            if(invalidFields.size()!=0){
                String exceptionMessage = "Invalid or Missing Fields(s): ";
                for(String field : invalidFields){
                    exceptionMessage += field + " ";
                }
                throw new IllegalArgumentException(exceptionMessage);
            }
        }
    }

    public void validateId(String id){
        if(!id.matches("[0-9]+")){
            throw new IllegalArgumentException("ID must be numerical characters.");
        }
    }

    public void validatePostTweet(Tweet tweet) {
        Double lon = tweet.getCoordinates().getCoordinates().get(0);
        Double lat = tweet.getCoordinates().getCoordinates().get(1);
        if (tweet.getText().length() > 140 || lat > 90.0 || lat < -90.0 || lon > 180.0 || lon < -180.0) {
            throw new IllegalArgumentException();
        }
    }
}