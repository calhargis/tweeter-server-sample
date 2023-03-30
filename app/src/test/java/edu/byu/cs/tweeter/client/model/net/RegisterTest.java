package edu.byu.cs.tweeter.client.model.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;

public class RegisterTest {
    private static final String URL = "/register";
    private ServerFacade serverFacade;
    private final String username = "dummy_user";
    private final String password = "dummy_password";
    private final String image = "image";
    private final String firstname = "firstname";
    private final String lastname = "lastname";

    @BeforeEach
    public void setup() {
        serverFacade = new ServerFacade();
    }

    @Test
    public void testSuccessfulRegister() throws IOException, TweeterRemoteException {
        RegisterRequest request = new RegisterRequest(firstname, lastname, username, password, image);
        RegisterResponse response = serverFacade.register(request, URL);

        Assert.assertNotNull(response);
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getAuthToken());
    }

    @Test
    public void testFailedRegister() {
        assertThrows(TweeterRemoteException.class, () ->  {
            serverFacade.register(new RegisterRequest(null, null, null, null, null), URL);
        });
    }
}
