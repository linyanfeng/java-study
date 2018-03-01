package lykos.sandbox.study.com;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author lykos
 * @create 2018/2/28 下午10:49
 */
public class BrokenClocker {
    private static Logger logger = LoggerFactory.getLogger(BrokenClocker.class);

    public static void main(String[] args) throws InterruptedException {
        loopReport();
    }

    public static final void loopReport() throws InterruptedException {
        while (true) {
            try {
                System.out.println(report());
            } catch (Throwable cause) {
                cause.printStackTrace();
            }
            delay();
        }
    }

    static final String report() {
        checkState();
        Date date = new Date();
        return date.toString();
    }

    static void checkState() {
        throw new IllegalStateException();
    }

    static void delay() throws InterruptedException {
        Thread.sleep(1000L);
    }

}
