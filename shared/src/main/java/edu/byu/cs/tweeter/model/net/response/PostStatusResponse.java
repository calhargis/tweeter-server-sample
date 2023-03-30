package edu.byu.cs.tweeter.model.net.response;

import javax.swing.RepaintManager;

public class PostStatusResponse extends Response {

    public PostStatusResponse() {
        super(true);
    }

    public PostStatusResponse(String message) {
        super(false, message);
    }
}
