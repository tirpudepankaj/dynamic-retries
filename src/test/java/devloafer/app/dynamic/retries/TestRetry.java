package devloafer.app.dynamic.retries;

import devloafer.app.dynamic.retries.config.RetryConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class TestRetry {


    @InjectMocks
    private Retry retry;

    @Mock
    private TestCallable testCallable;


    @BeforeEach
    private void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void test_dummy() throws Exception {
        RetryConfig retryConfig = defaultRetryConfig();
        int actual = retry.run(() -> {
            return 10;
        }, retryConfig);
        Assertions.assertEquals(10, actual);
    }

    @Test
    public void test_DefaultConfiguration() throws Exception {
        RetryConfig retryConfig = defaultRetryConfig();
        Mockito.when(testCallable.call()).thenThrow(ArithmeticException.class);
        Assertions.assertThrows(ArithmeticException.class, () -> retry.run(testCallable, retryConfig));
        Mockito.verify(testCallable, Mockito.times(1)).call();
    }

    @Test
    public void test_MaxAttemptsAs4Times() throws Exception {
        RetryConfig retryConfig = retryConfigFor4Attempts();
        Mockito.when(testCallable.call()).thenThrow(ArithmeticException.class);
        Assertions.assertThrows(ArithmeticException.class, () -> retry.run(testCallable, retryConfig));
        Mockito.verify(testCallable, Mockito.times(4)).call();
    }

    @Test
    public void test_ConfigureException() throws Exception {
        RetryConfig retryConfig = retryConfigForIOException();
        Mockito.when(testCallable.call()).thenThrow(ArithmeticException.class);
        Assertions.assertThrows(ArithmeticException.class, () -> retry.run(testCallable, retryConfig));
        Mockito.verify(testCallable, Mockito.times(1)).call();
    }


    private RetryConfig defaultRetryConfig() {
        return RetryConfig.toBuilder().build();
    }

    private RetryConfig retryConfigFor4Attempts() {
        Set<Class> retryWith = new HashSet<>();
        retryWith.add(ArithmeticException.class);
        return RetryConfig.toBuilder().maxAttempts(4)
                .retryWith(retryWith)
                .build();
    }

    private RetryConfig retryConfigForIOException() {

        Set<Class> retryWith = new HashSet<>();
        retryWith.add(IOException.class);

        return RetryConfig.toBuilder()
                .retryWith(retryWith)
                .build();
    }

    private class TestCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            return Integer.valueOf(100);
        }
    }
}
