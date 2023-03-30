package edu.byu.cs.tweeter.model.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class StoryResponse extends PagedResponse {
    List<Status> statuses;

    public StoryResponse(List<Status> statuses, boolean hasMorePages) {
        super(true, hasMorePages);
        this.statuses = statuses;
    }

    public StoryResponse(String message) {
        super(false, message, false);
    }

    public List<Status> getStatuses() {
        return statuses;
    }
}
