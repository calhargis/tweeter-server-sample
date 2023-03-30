package edu.byu.cs.tweeter.client.model.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetFollowCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowCountResponse;
import edu.byu.cs.tweeter.util.FakeData;

public class GetFollowersCountTest {
    private static final String URL_PATH = "/getfollowerscount";
    private ServerFacade serverFacade;

    private User fakeUser;
    private AuthToken authToken;

    @BeforeEach
    public void setup() {
        serverFacade = new ServerFacade();
        fakeUser = FakeData.getInstance().getFirstUser();
        authToken = new AuthToken();
    }

    @Test
    public void testSuccessfulGetFollowersCount() throws IOException, TweeterRemoteException {
        GetFollowCountRequest request = new GetFollowCountRequest(authToken, fakeUser);
        FollowCountResponse response = serverFacade.getFollowersCount(request, URL_PATH);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.isSuccess());
        Assert.assertNull(response.getMessage());
        Assert.assertEquals(20, response.getCount());
    }

    @Test
    public void testSuccessfulGetFollowingCount() throws IOException, TweeterRemoteException {
        FollowCountResponse response = serverFacade.getFollowingCount(new GetFollowCountRequest(authToken, fakeUser), URL_PATH);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.isSuccess());

        Assert.assertEquals(20, response.getCount());
    }

    @Test
    public void testFailedGetFollowersCount() throws IOException, TweeterRemoteException {
            GetFollowCountRequest request = new GetFollowCountRequest(null, null);
            FollowCountResponse response = serverFacade.getFollowersCount(request, URL_PATH);
            Assert.assertFalse(response.isSuccess());
    }
}
