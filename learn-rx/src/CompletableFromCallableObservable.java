import io.reactivex.Completable;
import io.reactivex.Observable;

public class CompletableFromCallableObservable {

    private void doIt() {

        Observable<Object> observable = Observable.fromCallable(this::getString)
                .map((str) -> {
                    System.out.println("Mapping " + str);
                    if (str.length() < 4) {
                        throw new RuntimeException("mock error");
                    }
                    return new Object();
                });

        Completable.fromObservable(observable)
                .subscribe(() -> System.out.println("Completable done"), Throwable::printStackTrace);
    }

    private String getString() {
        return "abcd";
    }

    public static void main(String[] args) {
        new CompletableFromCallableObservable().doIt();

        try {
            Thread.sleep(2);
        } catch (InterruptedException ie) {}
    }
}
