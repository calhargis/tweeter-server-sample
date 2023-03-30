package edu.byu.cs.tweeter.client.presenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter extends PagedPresenter<User> {

    private static final String LOG_TAG = "MainPresenter";

    private final MainPresenter.View view;

    private StatusService statusService;

    /**
     * Creates an instance
     * @param view
     */
    public MainPresenter(MainPresenter.View view) {
        this.view = view;
        this.statusService = new StatusService();
    }

    public interface View extends Presenter.View {
        void displayErrorMessage(String message);
        void displayInfoMessage(String message);
        void navigateToUser(User user);
        void logoutUser();
        void updateFollowerCount(int count);
        void updateFollowingCount(int count);
        void updateIsFollower(boolean isFollower);
        void updateFollow(boolean success);
        void updateUnfollow(boolean success);
        void updatePostStatus(boolean success);
        void clearInfoMessages();
    }

    public void logout(AuthToken authToken) {
        view.displayInfoMessage("Logging Out...");
        new UserService().logout(authToken, new MainObserver());
    }

    public void getFollowersCount(AuthToken authToken, User targetUser) {
        new FollowService().getFollowersCount(authToken, targetUser, new MainObserver());
    }

    public void getFollowingCount(AuthToken authToken, User user) {
        new FollowService().getFollowingCount(authToken, user, new MainObserver());
    }

    public void isFollower(AuthToken authToken, User follower, User followee) {
        new FollowService().isFollower(authToken, follower, followee, new MainObserver());
    }

    public void follow(AuthToken authToken, String userAlias) {
        new FollowService().follow(authToken, userAlias, new MainObserver());
    }

    public void unfollow(AuthToken authToken, String userAlias) {
        new FollowService().unfollow(authToken, userAlias, new MainObserver());
    }

    public void postStatus(String post) {
        view.displayInfoMessage("Posting Status...");
        try {
            Status newStatus = new Status(post, Cache.getInstance().getCurrUser(), System.currentTimeMillis(), parseURLs(post), parseMentions(post));
            getStatusService().postStatus(newStatus, new MainObserver());
        } catch (Exception ex) {
            view.displayErrorMessage("Failed to post the status because of exception: " + ex.getMessage());
        }
    }



    /* ---------------- HELPER FUNCTIONS FOR MAIN ACTIVITY ---------------- */

    public String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }

    public List<String> parseURLs(String post) {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }

    public int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }

    public List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }

    public StatusService getStatusService() {
        if (statusService == null) {
            return new StatusService();
        }
        return statusService;
    }

    private class MainObserver implements UserService.LogoutObserver, FollowService.GetFollowCountObserver,
    FollowService.IsFollowerObserver, FollowService.FollowObserver, FollowService.UnfollowObserver, StatusService.PostStatusObserver {
        @Override
        public void handleLogoutSuccess() {
            view.logoutUser();
        }

        /**
         * Invoked when the login request completes if the login request was unsuccessful. Notifies the
         * view of the unsuccessful login.
         *
         * @param message error message.
         */
        @Override
        public void handleFailure(String message) {
            view.clearInfoMessages();
            view.displayInfoMessage("Failed to post status: " + message);
        }

        /**
         * A callback indicating that an exception occurred in an asynchronous method this class is
         * observing.
         *
         * @param exception the exception.
         */
        @Override
        public void handleException(Exception exception) {
            view.clearInfoMessages();
            view.displayInfoMessage("Failed to post status because of exception: " + exception.getMessage());
        }

        @Override
        public void handleFollowerCountSuccess(int count) {
            view.updateFollowerCount(count);
        }

        @Override
        public void handleFollowingCountSuccess(int count) {
            view.updateFollowingCount(count);
        }

        @Override
        public void isFollowerSuccess(boolean isFollower) {
            view.updateIsFollower(isFollower);
        }

        @Override
        public void handleFollowSuccess(boolean success) {
            view.updateFollow(success);
        }

        @Override
        public void handleUnfollowSuccess(boolean success) {
            view.updateUnfollow(success);
        }

        @Override
        public void handlePostStatusSuccess(boolean success) {
            view.clearInfoMessages();
            view.displayInfoMessage("Successfully Posted!");
            view.updatePostStatus(success);
        }
    }

}
