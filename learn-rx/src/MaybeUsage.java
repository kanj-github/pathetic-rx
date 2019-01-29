import io.reactivex.Flowable;
import io.reactivex.Maybe;

public class MaybeUsage {

    private void doIt() {
        Boolean flag = getBoolean().blockingGet();
        System.out.println(flag);
    }

    private Maybe<Boolean> getBoolean() {
        return getStringMaybe().zipWith(getNumberMaybe(), (str, number) -> str.length() > number);
    }

    private Maybe<String> getStringMaybe() {
        //return Maybe.empty();
        //return Maybe.just("abcd");
        return Flowable.just("abcd", "abc", "abcdf").firstElement();
    }

    private Maybe<Integer> getNumberMaybe() {
        //return Maybe.empty();
        return Maybe.fromCallable(() -> {
            System.out.println("Getting number");
            return 3;
        });
    }

    public static void main(String[] args) {
        new MaybeUsage().doIt();
    }
}
