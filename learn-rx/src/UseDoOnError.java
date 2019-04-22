import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class UseDoOnError {

    private Observable<Integer> threeSteps() {

        return Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .map(zero -> {
                    System.out.println("Mapping " + zero);
                    return 1;
                }).flatMap(one -> {
                    System.out.println("Step 2");
                    return secondStep(one);
                })
                .map(two -> {
                    System.out.println("Step 3 " + two);
                    return 3;
                })
                .doOnError(err -> {
                    System.out.println("do on error " + err.getMessage());
                });
    }

    private Observable<Integer> secondStep(Integer one) {

        return Observable.timer(one, TimeUnit.SECONDS).map(zero -> {
            System.out.println("flat mapping " + zero);
            //throw new RuntimeException("Error in step 2");
            return 2;
        });
    }

    public static void main(String[] args) {

        new UseDoOnError().threeSteps()
                .subscribe(
                        num -> System.out.println("Got result " + num),
                        Throwable::printStackTrace
                );

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}
