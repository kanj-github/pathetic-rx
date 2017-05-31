import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.List;

/**
 * Created by naraykan on 31/05/17.
 */
public class FlatMapTwice {
    public static void main(String[] args) {
        Observable.just(new DataContainer())
                .flatMap(new Func1<DataContainer, Observable<String>>() {
                    @Override
                    public Observable<String> call(DataContainer dataContainer) {
                        return Observable.from(dataContainer.data)
                                .flatMap(new Func1<String, Observable<String>>() {
                                    @Override
                                    public Observable<String> call(String s) {
                                        return getDataAboutString(s)
                                                .map(new Func1<Integer, String>() {
                                                    @Override
                                                    public String call(Integer integer) {
                                                        return s + integer;
                                                    }
                                                });
                                    }
                                });
                    }
                })
                .toList()
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        for (String s: strings) {
                            System.out.println(s);
                        }
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
