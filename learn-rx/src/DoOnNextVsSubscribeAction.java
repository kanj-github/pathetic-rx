import io.reactivex.Observable;

/**
 * Created by naraykan on 25/11/16.
 */

// Does doOnNext action occur before subscribe action or after it?
// Update: doOnNext action runs earlier.
public class DoOnNextVsSubscribeAction {
    void doShit() {
        Observable.just("jackass")
                .doOnNext(str -> System.out.println("doOnNext action " + str))
                .subscribe(str -> System.out.println("subscribe action " + str));
    }
}
