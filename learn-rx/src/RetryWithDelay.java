import io.reactivex.Observable;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RetryWithDelay {

    private static final int INITIAL_FAILURE_COUNT = 4;
    private static final int RETRY_COUNT = 3;

    private int counter = 0;

    private Observable<Object> logicToRetry() {

        return Observable.fromCallable(() -> {

            System.out.println("logicToRetry " + counter + " time " + new Date().toString());
            if (++counter <= INITIAL_FAILURE_COUNT) {
                throw new Exception("Counter at " + counter);
            }

            return new Object();
        });
    }

    private void doIt() {
        logicToRetry().retryWhen(throwableObservable -> {
            AtomicInteger retryCounter = new AtomicInteger();
            return throwableObservable.flatMap(throwable -> {

                if (retryCounter.incrementAndGet() > RETRY_COUNT) {
                    throw new Exception("No more retries");
                }

                System.out.println("Retry after " + retryCounter.get() + " seconds for exception " + throwable.getMessage());
                return Observable.just(throwable).delay(retryCounter.get(), TimeUnit.SECONDS);
            });
        }).subscribe(
                object -> System.out.println("Got object"),
                throwable -> System.out.println("Handle error after retries- " + throwable.getMessage())
        );
    }

    private static void holdOn() {
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new RetryWithDelay().doIt();
        holdOn();
    }
}
