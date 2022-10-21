package ca.jrvs.apps.twitter.dao.helper;

import org.junit.Test;
import com.google.gdata.util.common.base.PercentEscaper;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class TwitterHttpHelperTest {

    @Test
    public void httpPost() throws Exception {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");
        HttpHelper twitterHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN,
                TOKEN_SECRET);
        String status = "testing the twitter api";
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        HttpResponse response = twitterHelper.httpPost(
                new URI("https://api.twitter.com/1.1/statuses/update.json?status=first_tweet2"
                        + percentEscaper.escape(status)));
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void httpGet() throws Exception {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");
        HttpHelper twitterHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN,
                TOKEN_SECRET);
        HttpResponse response = twitterHelper.httpGet(
                new URI(
                        "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=elonmusk"));
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}