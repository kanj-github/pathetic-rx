import io.reactivex.Observable;

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
                    if (num == 1) {
                        throw new Exception("BRRRRR");
                    }
                    return num * 2;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribe(
                        (num) -> System.out.println("Got " + num),
                        (err) -> System.out.println("Got " + err.getMessage())
                );
    }

    public static void main(String[] args) {
        ThrowingErrors throwingErrors = new ThrowingErrors();
        throwingErrors.doItWithFlatMap();
        throwingErrors.doItWithMap();
    }
}
