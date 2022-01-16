package devloafer.app.dynamic.retries;

import devloafer.app.dynamic.retries.config.RetryConfig;
import devloafer.app.dynamic.retries.exception.RetryConfigException;

import java.util.Set;
import java.util.concurrent.Callable;

public class Retry {

    private Set<Class> runWith = null;
    private long _delay = 0;
    private Exception thrownException = null;


    public <R> R run(Callable<R> callable, RetryConfig retryConfig) throws Exception {
        if (retryConfig == null || retryConfig.getRetryWith() == null)
            throw new RetryConfigException("Configuration for retry can not be null.");
        boolean delay = retryConfig.getDelayConfig() == null;
        if (delay) {
            this._delay = retryConfig.getDelayConfig().getDelay();
        }
        int maxAttempts = retryConfig.getMaxAttempts();
        this.runWith = retryConfig.getRetryWith();

        while (maxAttempts-- > 0) {
            try {
                return callable.call();
            } catch (Exception calledException) {
                thrownException = calledException;
                if (isCallbackThrowableFound(calledException)) {
                    if (delay) {
                        retryConfig.getDelayConfig().getTimeUnit().sleep(this._delay);
                    }
                } else break;
            }
        }

        throw thrownException;
    }

    private boolean isCallbackThrowableFound(Exception exception) {
        return runWith.contains(exception.getClass());
    }


}
