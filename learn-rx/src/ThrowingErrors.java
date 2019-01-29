import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ThrowingErrors {

    private Integer arr[] = new Integer[] {-1, 0, 1, 2, 3};

    public void doItWithFlatMap() {
        Observable.fromArray(arr)
                .flatMap((num) -> {
                    if (num == 1) {
                        return Observable.error(new Exception("BRRRRR"));
                    }
                    return Observable.just(num * 2);
                })
                .doOnError(Throwable::printStackTrace)
                .subscribe(
                        (num) -> System.out.println("Got " + num),
                        (err) -> System.out.println("Got " + err.getMessage())
                );
    }

    public void doItWithMap() {
        Observable.fromArray(arr)
                .map((num) -> {
                    System.out.println("Map on " + Thread.currentThread().getId());
                    if (num == 1) {
                        throw new Exception("BRRRRR");
                    }
                    return num * 2;
                })
                //.doOnError(Throwable::printStackTrace)
                .observeOn(Schedulers.io())
                .doOnNext((str) -> System.out.println("do on next " + Thread.currentThread().getId()))
                .doOnError((thrown) -> System.out.println("do on error " + Thread.currentThread().getId()))
                .doOnDispose(() -> System.out.println("do on dispose " + Thread.currentThread().getId()))
                .subscribe(
                        (num) -> System.out.println("Got " + num + " on " + Thread.currentThread().getId()),
                        (err) -> System.out.println("Got " + err.getMessage() + " on " + Thread.currentThread().getId())
                );
    }

    public static void main(String[] args) {
        ThrowingErrors throwingErrors = new ThrowingErrors();
        //throwingErrors.doItWithFlatMap();
        throwingErrors.doItWithMap();
    }
}
