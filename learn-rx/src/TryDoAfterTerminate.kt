import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

val flowable1 = Flowable.just(1, 2, 3, 4)
        .delay(100L, TimeUnit.MILLISECONDS)
        .map {
            if (it == 3) {
                throw Exception("Mock error")
            }
            it * 2
        }.doAfterTerminate {
            throw Exception("Uncaught exception")
            //println("Terminated")
        }

val flowable2 = Flowable.just(5, 6).delay(100L, TimeUnit.MILLISECONDS)

fun main() {
    Flowable.combineLatest(flowable1, flowable2, BiFunction<Int, Int, Int> {n1, n2 -> n1 + n2})
            .subscribe(
                    {num -> println(num) },
                    {throwable -> println(throwable.message)}
            )
    Thread.sleep(5000)
}
