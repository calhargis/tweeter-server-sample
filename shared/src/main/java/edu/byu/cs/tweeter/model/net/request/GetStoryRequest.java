package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class GetStoryRequest {
    private AuthToken authToken;
    private String targetUserAlias;
    private int limit;
    private String lastStatusPost;

    public GetStoryRequest() {
    }

    public GetStoryRequest(AuthToken authToken, String targetUserAlias, int limit, String lastStatusAlias) {
        this.authToken = authToken;
        this.targetUserAlias = targetUserAlias;
        this.limit = limit;
        this.lastStatusPost = lastStatusAlias;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getTargetUserAlias() {
        return targetUserAlias;
    }

    public void setTargetUserAlias(String targetUserAlias) {
        this.targetUserAlias = targetUserAlias;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getLastStatusPost() {
        return lastStatusPost;
    }

    public void setLastStatusPost(String lastStatusPost) {
        this.lastStatusPost = lastStatusPost;
    }
}
