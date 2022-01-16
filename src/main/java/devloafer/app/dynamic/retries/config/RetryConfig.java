package devloafer.app.dynamic.retries.config;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class RetryConfig {

    private Set<Class> retryWith;

    private int maxAttempts;

    private DelayConfig delayConfig;

    private static final int MAX_ATTEMPTS = 1;


    private RetryConfig(RetryConfigBuilder builder) {
        this.init(builder);
    }

    private void init(RetryConfigBuilder builder) {
        this.initMaxAttempts(builder.maxAttempts);
        this.initDefaultDelayConfig(builder.delayConfig);
        this.initRetryWith(builder.retryWith);

    }

    /**
     * initialized retryWith
     *
     * @param retryWith
     */
    private void initRetryWith(Set<Class> retryWith) {
        if (retryWith == null || retryWith.size() == 0) {
            retryWith = new HashSet<>();
            retryWith.add(Exception.class);
        }
        this.retryWith = retryWith;
    }

    /**
     * initialized maxAttempts
     *
     * @param maxAttempts
     */
    private void initMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts == 0 ? MAX_ATTEMPTS : maxAttempts;
    }

    /**
     * initialized delayConfig
     *
     * @param delayConfig
     */
    private void initDefaultDelayConfig(DelayConfig delayConfig) {
        if (delayConfig == null) {
            delayConfig = DelayConfig.toBuilder()
                    .delay(500L)
                    .timeUnit(TimeUnit.MILLISECONDS)
                    .build();
        }

        this.delayConfig = delayConfig;
    }

    public static RetryConfigBuilder toBuilder() {
        return new RetryConfigBuilder();
    }

    public DelayConfig getDelayConfig() {
        return delayConfig;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public Set<Class> getRetryWith() {
        return retryWith;
    }

    /**
     * Builder for Retry config
     */
    public static class RetryConfigBuilder {

        private Set<Class> retryWith;
        private int maxAttempts;
        private DelayConfig delayConfig;

        private RetryConfigBuilder() {
        }

        public RetryConfigBuilder retryWith(Set<Class> retryWith) {
            this.retryWith = retryWith;
            return this;
        }

        public RetryConfigBuilder maxAttempts(int maxAttempts) {
            this.maxAttempts = maxAttempts;
            return this;
        }

        public RetryConfigBuilder delayConfig(DelayConfig delayConfig) {
            this.delayConfig = delayConfig;
            return this;
        }

        public RetryConfig build() {
            return new RetryConfig(this);
        }

    }
}
