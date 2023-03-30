package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingRequest {
    private AuthToken authToken;
    private String targetUserAlias;
    private int limit;
    private String lastFolloweeAlias;

    public GetFollowingRequest() {
    }

    public GetFollowingRequest(AuthToken authToken, String targetUserAlias, int limit, String lastFolloweeAlias) {
        this.authToken = authToken;
        this.targetUserAlias = targetUserAlias;
        this.limit = limit;
        this.lastFolloweeAlias = lastFolloweeAlias;
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

    public String getLastFolloweeAlias() {
        return lastFolloweeAlias;
    }

    public void setLastFolloweeAlias(String lastFolloweeAlias) {
        this.lastFolloweeAlias = lastFolloweeAlias;
    }
}
