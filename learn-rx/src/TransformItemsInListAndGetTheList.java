import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by naraykan on 13/04/17.
 */
public class TransformItemsInListAndGetTheList {
    public static void main(String args[]) {
        ArrayList<String> array = new ArrayList<>();
        array.add("abcd");
        array.add("abc");
        array.add("!@#");
        array.add("hkjgjhg");
        Observable.fromIterable(array)
                .flatMap(str -> {
                    final String st = str;
                    System.out.println("flat map - " + st);
                    return getInfo(st).map(info -> {
                        if (info) {
                            return st.toUpperCase();
                        } else {
                            return st;
                        }
                    });
                })
                .toList()
                .subscribe(strings -> {
                    for (String s: strings) {
                        System.out.println(s);
                    }
                });

        // Need to keep the program running to allow sufficient time for getting info
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static Observable<Boolean> getInfo(String str) {
        System.out.println("getting info - " + str);
        return Observable.just(str)
                .delay(str.length() * 200, TimeUnit.MILLISECONDS)
                .map(s -> {
                    System.out.println("returning info for - " + s);
                    return s.length() > 3;
                });
    }
}
