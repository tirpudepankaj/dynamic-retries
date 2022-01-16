# dynamic-retries

dynamic-retries is lib which can be used to retry the operation based on configurable exception.

Below are the steps to use this lib.

1. Create DelayConfig.

    ``DelayConfig delayConfig = DelayConfig.toBuilder() .delay(500L) .timeUnit(TimeUnit.MILLISECONDS).build()``

2. Create RetryConfig.

    `` Set<Class> retryWith = new HashSet<>() ``

    `` retryWith.add(ArithmeticException.class) ``

    `` RetryConfig retryConfig = RetryConfig.toBuilder().maxAttempts(4).retryWith(retryWith).build()``

3. Pass Callable to Retry call method.

    `` Retry retry = new Retry()``
    `` retry.run(callableInstance, retryConfig) ``
