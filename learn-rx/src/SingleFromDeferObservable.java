import io.reactivex.Observable;
import io.reactivex.Single;

public class SingleFromDeferObservable {

    private void doIt() {

        Single.just(true)
                .flatMap((work) -> {
                    System.out.println("flatmap");
                    if (work) {
                        return Single.fromObservable(doWork());
                    } else {
                        return Single.just(false);
                    }
                }).subscribe(
                (aBoolean) -> System.out.println("got boolean " + aBoolean),
                Throwable::printStackTrace
        );

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
        }
    }

    private Observable<Boolean> doWork() {
        return Observable.defer(() -> Observable.just(work()))
                .doOnComplete(() -> System.out.println("Work Complete"));
    }

    private boolean work() {
        System.out.println("Doing work");
        return true;
    }

    public static void main(String[] args) {
        SingleFromDeferObservable singleFromDeferObservable = new SingleFromDeferObservable();
        singleFromDeferObservable.doIt();
    }
}
