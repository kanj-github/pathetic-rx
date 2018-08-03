import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ZipInParallel {
    private Disposable d;
    private long now;

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
        Observable<String> src1 = Observable.defer(() -> Observable.just(slowString1())).subscribeOn(Schedulers.io());
        Observable<String> src2 = Observable.defer(() -> Observable.just(slowString2())).subscribeOn(Schedulers.io());
        now = System.currentTimeMillis();
        d =  Observable.zip(src1, src2, (str1, str2) -> str1 + str2)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        this::acceptLogic,
                        Throwable::printStackTrace
                );

        holdOnDude(6);
        if (d == null) {
            System.out.println("Disposable is null");
        }
    }

    private void acceptLogic(String str) {
        System.out.println(str + " " + (System.currentTimeMillis() - now));
        d.dispose();
        d = null;
    }

    public static void main(String[] args) {
        new ZipInParallel().doIt();
    }
}
