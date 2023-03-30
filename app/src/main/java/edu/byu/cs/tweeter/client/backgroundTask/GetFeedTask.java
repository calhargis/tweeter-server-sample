package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's feed.
 */
public class GetFeedTask extends PagedStatusTask {

    public static final String URL_PATH = "/getfeed";

    public static final String LOG_TAG = "FeedTask";

    public GetFeedTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                       Handler messageHandler) {
        super(authToken, targetUser, limit, lastStatus, messageHandler);
    }

    @Override
    protected void runTask() {
        try {
            String targetUserAlias = getTargetUser() == null ? null : getTargetUser().getAlias();
            String lastStatusPost = getLastItem() == null ? null : getLastItem().getPost();

            GetFeedRequest request = new GetFeedRequest(getAuthToken(), targetUserAlias, getLimit(), lastStatusPost);
            FeedResponse response = getServerFacade().getFeed(request, URL_PATH);

            if (response.isSuccess()) {
                setItems(response.getStatuses());
                setHasMorePages(response.getHasMorePages());
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (IOException | TweeterRemoteException ex) {
            Log.e(LOG_TAG, "Failed to get feed", ex);
            sendExceptionMessage(ex);
        }
    }

    @Override
    protected Pair<List<Status>, Boolean> getItems() {
        return getFakeData().getPageOfStatus(getLastItem(), getLimit());
    }
}
