import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

public class UseDefer {
    private String fresh = "abc";

    private PublishSubject<Object> trigger = PublishSubject.create();

    // Convert a sync access to string to asynchronous
    private Observable<String> getFreshString() {
        System.out.println("getFresh " + fresh + " " + System.currentTimeMillis());
        return Observable.defer(() -> Observable.just(fresh).delay(1, TimeUnit.SECONDS));
    }

    private void doIt() {
        getFreshString()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        (str) -> System.out.println(str + " " + System.currentTimeMillis()),
                        Throwable::printStackTrace
                );

        // Trigger publishes fresh change events
        Disposable d = trigger.flatMap((object) -> getFreshString())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        (str) -> System.out.println(str + " " + System.currentTimeMillis()),
                        Throwable::printStackTrace
                );

        holdOnDude(); // hold on for some time otherwise this change will get skipped
        fresh = "xyz";
        trigger.onNext(new Object());

        holdOnDude();
        fresh = "123";
        trigger.onNext(new Object());

        holdOnDude();
        d.dispose();
    }

    private static void holdOnDude() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UseDefer().doIt();
    }
}
