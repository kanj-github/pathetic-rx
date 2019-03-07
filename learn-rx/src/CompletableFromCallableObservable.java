import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.io.IOException;

public class CompletableFromCallableObservable {

    // If you dispose before the error actually occurs in Callable's lambda, RxPlugin handles the exception and crashes

    private void subscribeCompletable(boolean errorInGettingString, boolean shortString) {

        Disposable disposable = Completable.fromObservable(getObservableUsingCallable(errorInGettingString, shortString))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        () -> System.out.println("Completable done"),
                        (throwable -> {
                            System.out.println("Completable threw " + throwable.getMessage());
                            //throwable.printStackTrace();
                        })
                );

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {}

        System.out.println("dispose");
        disposable.dispose();
    }

    private void blockingGetCompletable(boolean errorInGettingString, boolean shortString) {

        Completable completable = Completable.fromObservable(getObservableUsingCallable(errorInGettingString, shortString));
        Throwable throwable = completable.blockingGet();
        if (throwable != null) {
            System.out.println("Completable get threw " + throwable.getMessage());
            //throwable.printStackTrace();
        } else {
            System.out.println("Completable get didn't throw");
        }
    }

    private Observable<Object> getObservableUsingCallable(boolean errorInGettingString, boolean shortString) {

        return Observable.fromCallable(() -> getString(errorInGettingString, shortString))
                .map((str) -> {
                    System.out.println("Mapping " + str);
                    if (str.length() < 4) {
                        throw new RuntimeException("mock error");
                    }
                    return new Object();
                });
    }

    private String getString(boolean error, boolean shortString) throws IOException {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ie) {}

        if (error) {
            System.out.println("error now");
            return error();
        }

        return (shortString) ? "abc" : "abcd";
    }

    private String error() throws IOException {
        throw new IOException("Error!!");
    }

    private static void stuff() {
        System.out.println("throw stuff");
        //throw new RuntimeException("Stuff");
    }

    public static void main(String[] args) {

        //CompletableFromCallableObservable completable = new CompletableFromCallableObservable();
        //completable.subscribeCompletable(false, false);
        //completable.subscribeCompletable(false, true);
        //completable.subscribeCompletable(true, false);
        //completable.blockingGetCompletable(true, false);

        Completable.fromAction(() -> stuff())
                .subscribeOn(Schedulers.newThread())
                .subscribe(() -> {}, (throwable -> {
                    System.out.println("got thrown stuff");
                    throwable.printStackTrace();
                }));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {}
    }
}
