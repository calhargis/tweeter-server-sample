package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;

/**
 * Background task that establishes a following relationship between two users.
 */
public class FollowTask extends AuthenticatedTask {
    /**
     * The user that is being followed.
     */
    private final String followeeAlias;

    public static final String URL_PATH = "follow";

    public static final String LOG_TAG = "FollowTask";

    public FollowTask(AuthToken authToken, String followeeAlias, Handler messageHandler) {
        super(authToken, messageHandler);
        this.followeeAlias = followeeAlias;
    }

    @Override
    protected void runTask() {
        try {

            FollowRequest request = new FollowRequest(getAuthToken(), followeeAlias);
            FollowResponse response = getServerFacade().follow(request, URL_PATH);

            if (response.isSuccess()) {
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
    }

}
