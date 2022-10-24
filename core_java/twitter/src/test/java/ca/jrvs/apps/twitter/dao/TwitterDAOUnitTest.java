//package ca.jrvs.apps.twitter.dao;
//
//import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
//import ca.jrvs.apps.twitter.example.JsonParser;
//import ca.jrvs.apps.twitter.model.Tweet;
//import ca.jrvs.apps.twitter.util.TweetUtil;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//
//import static org.junit.Assert.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.when;
//
//public class TwitterDAOUnitTest {
//
//    @Mock
//    HttpHelper mockHelper;
//
//    @InjectMocks
//    TwitterDAO dao;
//
//    @Test
//    public void showTweet() throws Exception {
//        Tweet post = TweetUtil.buildTweet("text", -1.0, 1.0);
//
//        //should get exception here
//        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
//        try {
//            dao.create(post);
//            fail();
//        } catch (RuntimeException e) {
//            assertTrue(true);
//        }
//
//        //Now test a good path
//        String tweetJsonStr = "{\n"
//                + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
//                + "   \"id\":1097607853932564480,\n"
//                + "   \"id_str\":\"1097607853932564480\",\n"
//                + "   \"text\":\"test with loc223\",\n"
//                + "   \"entities\":{\n"
//                + "      \"hashtags\":[],"
//                + "      \"user_mentions\":[]"
//                + "   },\n"
//                + "   \"coordinates\":null,"
//                + "   \"retweet_count\":0,\n"
//                + "   \"favorite_count\":0,\n"
//                + "   \"favorited\":false,\n"
//                + "   \"retweeted\":false\n"
//                + "}";
//
//        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
//        TwitterDAO spyDao = Mockito.spy(dao);
//        Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);
//
//        //mock parseResponse
//        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
//        Tweet tweet = spyDao.create(post);
//        assertNotNull(tweet);
//        assertNotNull(tweet.getText());
//    }
//}