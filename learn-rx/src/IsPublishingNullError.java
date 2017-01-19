import rx.subjects.PublishSubject;

/**
 * Created by naraykan on 19/01/17.
 */
public class IsPublishingNullError {
    void doShit() {
        PublishSubject<String> publisher = PublishSubject.create();

        publisher.subscribe(System.out::println, err -> err.printStackTrace());

        publisher.onNext("Garbage");
        publisher.onNext(null);
    }
}
