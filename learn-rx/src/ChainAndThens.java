import io.reactivex.Completable;
import io.reactivex.Observable;

public class ChainAndThens {

    private void doIt() {
        Completable.fromAction(() -> {
            delay(500);
            System.out.println("Step 1");
        }).andThen(Completable.fromAction(() -> {
            delay(500);
            System.out.println("Step 2");
        })).andThen(lastStep())
            .subscribe();

        delay(5000);
    }

    private void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    private Observable<Object> lastStep() {

        System.out.println("lastStep called");

        return Observable.fromCallable(() -> {
            delay(100);
            System.out.println("lastStep executes");
            return new Object();
        });
    }

    public static void main(String[] args) {
        new ChainAndThens().doIt();
    }
}
