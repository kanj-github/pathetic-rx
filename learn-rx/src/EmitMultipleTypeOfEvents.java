import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class EmitMultipleTypeOfEvents {

    static class Car {

        private Subject<Object> eventsPublisher = PublishSubject.create();

        public void start() {
            eventsPublisher.onNext(new StartEvent());
        }

        public void stop() {
            eventsPublisher.onNext(new StopEvent());
        }

        public <T> Observable<T> ofEvent(Class<T> clazz) {
            return eventsPublisher.ofType(clazz);
        }
    }

    static class StartEvent {}

    static class StopEvent {}

    public static void main(String[] args) {

        Car c = new Car();

        c.ofEvent(StartEvent.class)
                .subscribe((startEvent) -> {System.out.println("Start listener got start");});

        c.ofEvent(StopEvent.class)
                .subscribe((stopEvent -> {System.out.println("Stop listener got stop");}));

        c.start();
        c.start();
        c.stop();
    }
}
