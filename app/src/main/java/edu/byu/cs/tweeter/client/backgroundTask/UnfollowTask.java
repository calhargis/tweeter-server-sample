package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;

/**
 * Background task that removes a following relationship between two users.
 */
public class UnfollowTask extends AuthenticatedTask {

    /**
     * The user that is being followed.
     */
    private final String followeeAlias;

    public static final String URL_PATH = "/unfollow";

    public static final String LOG_TAG = "UnfollowTask";

    public UnfollowTask(AuthToken authToken, String followeeAlias, Handler messageHandler) {
        super(authToken, messageHandler);
        this.followeeAlias = followeeAlias;
    }

    @Override
    protected void runTask() {
        try {

            UnfollowRequest request = new UnfollowRequest(getAuthToken(), followeeAlias);
            UnfollowResponse response = getServerFacade().unfollow(request, URL_PATH);

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
