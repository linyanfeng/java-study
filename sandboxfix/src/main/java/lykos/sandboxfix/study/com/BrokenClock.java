package lykos.sandboxfix.study.com;


/**
 * @author lykos
 * @create 2018/2/28 下午10:49
 */
public class BrokenClock extends Clock {

    @Override
    void checkState() {
        throw new IllegalStateException();
    }

    @Override
    void delay() throws InterruptedException {
        Thread.sleep(10000L);
    }
}
