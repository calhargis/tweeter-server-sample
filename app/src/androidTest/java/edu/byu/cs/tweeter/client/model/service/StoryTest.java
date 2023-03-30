package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;

public class StoryTest {

    private StatusService statusServiceSpy;
    private AuthToken authToken;
    private User fakeUser;
    private StoryServiceObserver storyObserver;
    private CountDownLatch countDownLatch;

    @BeforeEach
    public void setup() {
        fakeUser = FakeData.getInstance().getFirstUser();
        authToken = new AuthToken();
        statusServiceSpy = Mockito.spy(new StatusService());
        storyObserver = Mockito.mock(StoryServiceObserver.class);
        Mockito.doAnswer(invocation -> {
            countDownLatch.countDown();
            return null;
        }).when(storyObserver).handleSuccess(Mockito.anyList(), Mockito.anyBoolean());
        resetCountDownLatch();
    }


    private void resetCountDownLatch() {
        countDownLatch = new CountDownLatch(1);
    }

    private void awaitCountDownLatch() throws InterruptedException {
        countDownLatch.await();
        resetCountDownLatch();
    }

    private class StoryServiceObserver implements StatusService.GetStoryObserver {

        @Override
        public void handleSuccess(List<Status> items, boolean hasMorePages) {
            countDownLatch.countDown();
        }
        @Override
        public void handleFailure(String message) {
            countDownLatch.countDown();
        }
        @Override
        public void handleException(Exception exception) {
            countDownLatch.countDown();
        }
    }

    @Test
    public void testGetStory_validRequest_correctResponse() throws InterruptedException {
        statusServiceSpy.getStory(authToken, fakeUser, 3, null, this.storyObserver);
        awaitCountDownLatch();
        Mockito.verify(storyObserver).handleSuccess(Mockito.anyList(), Mockito.anyBoolean());
    }
}
