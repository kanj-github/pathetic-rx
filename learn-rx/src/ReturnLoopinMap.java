import rx.Observable;

import java.util.ArrayList;

/**
 * Created by naraykan on 25/11/16.
 */

// Testing code in getSuggestionForLocation of SearchClientImpl. It has a loop of return statements in map function.

public class ReturnLoopinMap {
    void doShit() {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        Observable.just(numbers)
                .map(arr -> {
                    for (int i: arr) {
                        return i;
                    }
                    return null;
                })
                .subscribe(System.out::println);
        // Checkout how subscribe has method reference instead of lambda.
    }
}
