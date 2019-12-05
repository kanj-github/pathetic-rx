import io.reactivex.subjects.PublishSubject;

public class KeepObservingAfterError {

    private static final PublishSubject<Integer> source = PublishSubject.create();

    private void observe() {

        source.map(number -> 1 / number)
                .subscribe(reciprocal -> {
                    System.out.println("reciprocal is " + reciprocal);
                }, error -> {
                    System.out.println("error is " + error.getMessage());
                    observe();
                });
    }

    private void doIt() {
        observe();
    }

    private static void holdOn() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        new KeepObservingAfterError().doIt();

        for (int i = -2; i < 3; i++) {
            holdOn();
            source.onNext(i);
        }

        holdOn();
    }
}
