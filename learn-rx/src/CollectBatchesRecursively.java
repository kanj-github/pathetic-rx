import rx.Observable;

import java.util.ArrayList;

/**
 * Created by naraykan on 18/04/17.
 */
public class CollectBatchesRecursively {
    private static final int TOTAL = 13;
    ArrayList<Integer> array;

    CollectBatchesRecursively() {
        array = new ArrayList<>();
        for (int i = 1; i<= TOTAL; i++) {
            array.add(i);
        }
    }

    Observable<ArrayList<Integer>> getBatch(int lastIndex) {
        System.out.println(" -> " + lastIndex);
        return Observable.just(array)
                .map(arr -> {
                    ArrayList<Integer> batch = new ArrayList<>();
                    for (int i = 3; i>= 1; i--) {
                        if (lastIndex - i >= 0) {
                            batch.add(arr.get(lastIndex - i));
                        }
                    }
                    return batch;
                });
    }

    void getAllInBatches() {
        getBatch(TOTAL)
                .flatMap(arr -> fetchMoreBatchesAndAppend(arr, arr.get(0)))
                .toBlocking()
                .forEach(System.out::println);
    }

    Observable<ArrayList<Integer>> fetchMoreBatchesAndAppend(final ArrayList<Integer> lastArray, final int previousLastIndex) {
        return getBatch(previousLastIndex - 1)
                .flatMap(arr -> {
                    lastArray.addAll(0, arr);
                    if (arr.size() >= 3) {
                        return fetchMoreBatchesAndAppend(lastArray, arr.get(0));
                    } else {
                        return Observable.just(lastArray);
                    }
                });
    }
}
