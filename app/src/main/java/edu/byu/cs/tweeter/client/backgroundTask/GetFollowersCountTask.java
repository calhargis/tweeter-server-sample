package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetFollowCountRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.response.FollowCountResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;

/**
 * Background task that queries how many followers a user has.
 */
public class GetFollowersCountTask extends GetCountTask {

    public static final String URL_PATH = "/getfollowerscount";

    public static final String LOG_TAG = "GetFollowersCountTask";

    public GetFollowersCountTask(AuthToken authToken, User targetUser, Handler messageHandler) {
        super(authToken, targetUser, messageHandler);
    }

    @Override
    protected void runTask() {
        try {
            GetFollowCountRequest request = new GetFollowCountRequest(getAuthToken(), getTargetUser());
            FollowCountResponse response = getServerFacade().getFollowersCount(request, URL_PATH);

            if (response.isSuccess()) {
                setCount(response.getCount());
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
    }

    @Override
    protected int runCountTask() {
        return 20;
    }
}
