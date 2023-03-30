package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.util.FakeData;

public class StatusDAO {

    public PostStatusResponse postStatus(PostStatusRequest request) {
        return new PostStatusResponse();
    }

    /**
     * Gets the users from the database that the user specified in the request is following. Uses
     * information in the request object to limit the number of followees returned and to return the
     * next set of followees after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public FeedResponse getFeed(GetFeedRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getLimit() > 0;
        assert request.getTargetUserAlias() != null;

        List<Status> allStatuses = getDummyFeed();
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allStatuses != null) {
                int followeesIndex = getStatusesFirstIndex(request.getLastStatusPost(), allStatuses);

                for(int limitCounter = 0; followeesIndex < allStatuses.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                    responseStatuses.add(allStatuses.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allStatuses.size();
            }
        }

        return new FeedResponse(responseStatuses, hasMorePages);
    }

    /**
     * Gets the users from the database that the user specified in the request is following. Uses
     * information in the request object to limit the number of statuses returned and to return the
     * next set of statuses after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
     *
     * @param request contains information about the user whose statuses are to be returned and any
     *                other information required to satisfy the request.
     * @return the statuses.
     */
    public StoryResponse getStory(GetStoryRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getLimit() > 0;
        assert request.getTargetUserAlias() != null;

        List<Status> allStatuses = getDummyFeed();
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allStatuses != null) {
                int followeesIndex = getStatusesFirstIndex(request.getLastStatusPost(), allStatuses);

                for(int limitCounter = 0; followeesIndex < allStatuses.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                    responseStatuses.add(allStatuses.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allStatuses.size();
            }
        }

        return new StoryResponse(responseStatuses, hasMorePages);
    }

    private int getStatusesFirstIndex(String lastStatusPost, List<Status> allStatuses) {

        int followeesIndex = 0;

        if(lastStatusPost != null) {
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatusPost.equals(allStatuses.get(i).getPost())) {
                    followeesIndex = i + 1;
                    break;
                }
            }
        }

        return followeesIndex;
    }

    /**
     * Returns the list of dummy followee data. This is written as a separate method to allow
     * mocking of the followees.
     *
     * @return the followees.
     */
    List<Status> getDummyFeed() {
        return getFakeData().getFakeStatuses();
    }

    /**
     * Returns the {@link FakeData} object used to generate dummy followees.
     * This is written as a separate method to allow mocking of the {@link FakeData}.
     *
     * @return a {@link FakeData} instance.
     */
    FakeData getFakeData() {
        return FakeData.getInstance();
    }
}