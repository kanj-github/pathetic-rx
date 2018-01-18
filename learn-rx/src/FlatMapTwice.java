import io.reactivex.Observable;

/**
 * Created by naraykan on 31/05/17.
 */
public class FlatMapTwice {
    public static void main(String[] args) {
        Observable.just(new DataContainer())
                .flatMap(dataContainer -> Observable.from(dataContainer.data)
                        .flatMap(s -> getDataAboutString(s)
                                .map(integer -> s + integer)))
                .toList()
                .subscribe(strings -> {
                    for (String s: strings) {
                        System.out.println(s);
                    }
                });
    }

    static Observable<Integer> getDataAboutString(String str) {
        return Observable.just(str.length());
    }

    static class DataContainer {
        String[] data;

        public DataContainer() {
            data = new String[] {"abcd", "xyz"};
        }
    }
}
