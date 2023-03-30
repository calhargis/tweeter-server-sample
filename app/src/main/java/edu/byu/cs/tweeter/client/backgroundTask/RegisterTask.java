package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that creates a new user account and logs in the new user (i.e., starts a session).
 */
public class RegisterTask extends AuthenticateTask {

    private static final String LOG_TAG = "RegisterTask";
    public static final String URL_PATH = "/register";

    /**
     * The user's first name.
     */
    private String firstName;
    
    /**
     * The user's last name.
     */
    private String lastName;

    /**
     * The base-64 encoded bytes of the user's profile image.
     */
    private String image;

    /**
     * The logged-in user returned by the server.
     */
    protected User user;

    /**
     * The auth token returned by the server.
     */
    protected AuthToken authToken;

    public RegisterTask(String firstName, String lastName, String username, String password, String image, Handler messageHandler) {
        super(messageHandler, username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    @Override
    protected void runTask() {
        try {

            RegisterRequest request = new RegisterRequest(firstName, lastName, username, password, image);
            RegisterResponse response = getServerFacade().register(request, URL_PATH);


            if (response.isSuccess()) {
                setAuthenticatedUser(response.getUser());
                setAuthToken(response.getAuthToken());
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
    }

//    @Override
//    protected Pair<User, AuthToken> runAuthenticationTask() {
//        User registeredUser = getFakeData().getFirstUser();
//        AuthToken authToken = getFakeData().getAuthToken();
//        return new Pair<>(registeredUser, authToken);
//    }
}
