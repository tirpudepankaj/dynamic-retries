package devloafer.app.dynamic.retries.config;


import java.util.concurrent.TimeUnit;


public class DelayConfig {

    private long delay;

    private TimeUnit timeUnit;

    private DelayConfig(DelayConfigBuilder builder) {
        this.delay = builder.delay;
        this.timeUnit = builder.timeUnit;
    }


    public long getDelay() {
        return delay;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public static DelayConfigBuilder toBuilder() {
        return new DelayConfigBuilder();
    }

    public static class DelayConfigBuilder {
        private long delay;
        private TimeUnit timeUnit;

        private DelayConfigBuilder() {
        }

        public DelayConfigBuilder delay(long delay) {
            this.delay = delay;
            return this;
        }

        public DelayConfigBuilder timeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public DelayConfig build() {
            return new DelayConfig(this);
        }

    }


}
