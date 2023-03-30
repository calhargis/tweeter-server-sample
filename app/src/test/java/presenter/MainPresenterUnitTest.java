package presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class MainPresenterUnitTest {
    private MainPresenter.View mockView;
    private Cache mockCache;
    private StatusService mockStatusService;
    private MainPresenter mainPresenterSpy;
    private Status mockStatus;
    private AuthToken mockToken;



    @BeforeEach
    public void setup() {
        mockView = Mockito.mock(MainPresenter.View.class);
        mockCache = Mockito.mock(Cache.class);
        mockStatusService = Mockito.mock(StatusService.class);
        mockStatus = Mockito.mock(Status.class);
        mockToken = Mockito.mock(AuthToken.class);
        Cache.setInstance(mockCache);
        mainPresenterSpy = Mockito.spy(new MainPresenter(mockView));
        Mockito.when(mainPresenterSpy.getStatusService()).thenReturn(mockStatusService);
    }

    @Test
    public void testPostStatus_success() {
        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StatusService.PostStatusObserver observer = invocation.getArgument(1, StatusService.PostStatusObserver.class);
                observer.handlePostStatusSuccess(true);
                return null;
            }

        };

        Mockito.doAnswer(answer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any());
        mainPresenterSpy.postStatus("this is the status");

        Mockito.verify(mockView).displayInfoMessage("Posting Status...");
        verifyResult("Successfully Posted!");
    }

    private void verifyResult(String s) {
        Mockito.verify(mockCache, Mockito.times(0)).clearCache();
//        Mockito.verify(mockCache).clearCache();
        Mockito.verify(mockView).clearInfoMessages();
        Mockito.verify(mockView).displayInfoMessage(s);
    }

    @Test
    public void testPostStatus_failed() {
        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StatusService.PostStatusObserver observer = invocation.getArgument(1, StatusService.PostStatusObserver.class);
                observer.handleFailure("something bad happened...");
                return null;
            }
        };

        Mockito.doAnswer(answer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any());
        mainPresenterSpy.postStatus("this is the status");

        Mockito.verify(mockView).displayInfoMessage("Posting Status...");
        verifyResult("Failed to post status: something bad happened...");
    }

    @Test
    public void testPostStatus_failedWithException() {
        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StatusService.PostStatusObserver observer = invocation.getArgument(1, StatusService.PostStatusObserver.class);
                observer.handleException(new Exception("My Exception"));
                return null;
            }
        };

        Mockito.doAnswer(answer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any());
        mainPresenterSpy.postStatus("this is the status");

        Mockito.verify(mockView).displayInfoMessage("Posting Status...");
        verifyResult("Failed to post status because of exception: My Exception");
    }



}
