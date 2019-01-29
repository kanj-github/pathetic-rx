import io.reactivex.Maybe;

public class MaybeUsage {

    private void doIt() {
        Boolean flag = getBoolean().blockingGet();
        System.out.println(flag);
    }

    private Maybe<Boolean> getBoolean() {
        return getStringMaybe().zipWith(getNumberMaybe(), (str, number) -> str.length() > number);
    }

    private void holdOn() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    private Maybe<String> getStringMaybe() {
        //return Maybe.empty();
        return Maybe.just("abcd");
    }

    private Maybe<Integer> getNumberMaybe() {
        return Maybe.fromCallable(() -> {
            System.out.println("Getting number");
            return 3;
        });
    }

    public static void main(String[] args) {
        new MaybeUsage().doIt();
    }
}
