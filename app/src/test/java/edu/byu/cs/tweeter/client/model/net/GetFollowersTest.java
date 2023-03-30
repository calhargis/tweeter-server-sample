package edu.byu.cs.tweeter.client.model.net;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetFollowersRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowersResponse;
import edu.byu.cs.tweeter.util.FakeData;

public class GetFollowersTest {
    private static final String URL = "/getfollowers";
    private final int limit = 20;

    private User user;
    private AuthToken authToken;
    private ServerFacade serverFacade;

    @BeforeEach
    public void setup() {
        user = FakeData.getInstance().getFirstUser();
        serverFacade = new ServerFacade();
    }

    @Test
    public void testSuccessfulGetFollowers() throws IOException, TweeterRemoteException {
        authToken = new AuthToken("odpfaosk", "asldkflskdf");
        GetFollowersRequest request = new GetFollowersRequest(authToken, user.getAlias(), limit, "fakeuseralias");
        GetFollowersResponse response = serverFacade.getFollowers(request, URL);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getFollowers());
        Assert.assertEquals(limit, response.getFollowers().size());
    }

    @Test
    public void testInvalidLimitGetFollowers() {
        assertThrows(TweeterRemoteException.class, () ->  {
            serverFacade.getFollowers(new GetFollowersRequest(null, user.getAlias(), -39402, "faskeUserAlias"), URL);
        });
    }
}
