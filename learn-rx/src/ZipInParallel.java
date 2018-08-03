import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ZipInParallel {
    private String slowString1() {
        holdOnDude(5);
        return "abc";
    }

    private String slowString2() {
        holdOnDude(2);
        return "ABC";
    }

    private static void holdOnDude(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doIt() {
        Observable<String> src1 = Observable.defer(() -> Observable.just(slowString1()));
        Observable<String> src2 = Observable.defer(() -> Observable.just(slowString2()));
        long now = System.currentTimeMillis();
        Disposable d =  Observable.zip(
                src1.subscribeOn(Schedulers.io()),
                src2.subscribeOn(Schedulers.io()),
                (str1, str2) -> str1 + str2
        )
                .subscribeOn(Schedulers.io())
                .subscribe(
                        (str) -> System.out.println(str + " " + (System.currentTimeMillis() - now)),
                        Throwable::printStackTrace
                );

        holdOnDude(6);
        d.dispose();
    }

    public static void main(String[] args) {
        new ZipInParallel().doIt();
    }
}
