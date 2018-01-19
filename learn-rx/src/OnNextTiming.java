/**
 * Created by voldemort on 23/11/16.
 */

import io.reactivex.Observable;

/* ​
*  Make Subscriber and call onNext before subscribing to observable
*  Update: This is not a matter of timing.
*/
public class OnNextTiming {
    void doShit() {
        Observable<Garbage> pile = Observable.create(observableEmitter -> {
            observableEmitter.onNext(new Garbage("stuff"));
            observableEmitter.onComplete();
        });
        // Create needs ObservableOnSubscribe<Garbage> instance

        Observable<Garbage> pile2 = Observable.create((subscriber) -> {
                subscriber.onNext(new Garbage("more stuff"));
                subscriber.onComplete();
        });

        pile.zipWith(pile2, ((garbage1, garbage2) ->
                new Garbage(garbage1.text + " " + garbage2.text))
        ).subscribe((garbage -> System.out.println(garbage.text)));
    }

    class Garbage {
        String text;

        public Garbage(String text) {
            this.text = text;
        }
    }
}
