//package ca.jrvs.apps.twitter.dao;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//
//import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
//import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
//import ca.jrvs.apps.twitter.model.Coordinates;
//import ca.jrvs.apps.twitter.model.Tweet;
//import java.util.ArrayList;
//import java.util.List;
//
//import ca.jrvs.apps.twitter.util.TweetUtil;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class TwitterDAOIntTest {
//
//    private CrdDao dao;
//
//    @Before
//    public void setUp() throws Exception {
//        String consumerKey = System.getenv("consumerKey");
//        String consumerSecret = System.getenv("consumerSecret");
//        String accessToken = System.getenv("accessToken");
//        String tokenSecret = System.getenv("tokenSecret");
//        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
//        this.dao = new TwitterDAO(httpHelper);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }
//
//    @Test
//    public void create() throws Exception {
//        String hashTag = "#abc";
//        String text = "@someone some text " + hashTag + " " + System.currentTimeMillis();
//        Double lat = 1d;
//        Double lon = -1d;
//
//        Tweet postTweet = TweetUtil.buildTweet(text, lon, lat);
//
//        System.out.println(postTweet.getCoordinates().getCoordinates().toString());
//
//        assertEquals(text, postTweet.getText());
//        assertNotNull(postTweet.getCoordinates());
//        assertEquals(2, postTweet.getCoordinates().getCoordinates().size());
//        assertEquals(lat, postTweet.getCoordinates().getCoordinates().get(1));
//        assertEquals(lon, postTweet.getCoordinates().getCoordinates().get(0));
//        assertTrue(hashTag.contains(postTweet.getEntities().getHashtags().get(0).getText()));
//    }
//}