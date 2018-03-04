package lykos.sandboxfix.study.com;

/**
 * @author yanfenglin
 * @version 2018/3/4 17:17
 */
public class BrokenClockSandboxFix {
    public static void main(String[] args) throws InterruptedException {
        BrokenClock clock = new BrokenClock();
        clock.loopReport();
    }
}
