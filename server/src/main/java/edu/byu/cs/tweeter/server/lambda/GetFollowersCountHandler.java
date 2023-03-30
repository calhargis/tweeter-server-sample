package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.GetFollowCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowCountResponse;
import edu.byu.cs.tweeter.server.service.FollowService;

public class GetFollowersCountHandler implements RequestHandler<GetFollowCountRequest, FollowCountResponse> {
    @Override
    public FollowCountResponse handleRequest(GetFollowCountRequest input, Context context) {
        FollowService service = new FollowService();
        return service.getFollowersCount(input);
    }
}
