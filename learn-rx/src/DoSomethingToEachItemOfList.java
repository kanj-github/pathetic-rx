import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naraykan on 24/01/17.
 */
public class DoSomethingToEachItemOfList {
    void doShit() {
        // Approach 1
        ArrayList<String> array = new ArrayList<>();
        array.add("abc");
        array.add("123");
        array.add("!@#");
        Observable<List<String>> result = Observable.just(array);

        result.flatMap(Observable::fromIterable)
            .flatMap(this::dealWithString) // Can't use map because map function would have to return Boolean.
            .subscribe(System.out::println, Throwable::printStackTrace);

        // Approach 2
        boolean errorOccurred = false;
        PublishSubject<String> stringHandler = PublishSubject.create();
        stringHandler.subscribe(str -> {
            dealWithString(str + "-")
                    .subscribe(bool -> {
                        if (bool) {
                            System.out.println(str + " done");
                        } else {

                        }
                    });
        });
        stringHandler.subscribe(s -> { }, throwable -> { });
    }

    private Observable<Boolean> dealWithString(String s) {
        System.out.println(s);
        return Observable.just(true);
    }
}
