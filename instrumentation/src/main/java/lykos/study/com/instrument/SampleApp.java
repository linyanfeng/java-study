package lykos.study.com.instrument;

import java.util.concurrent.TimeUnit;

/**
 * Created by fengfu.qu on 2016/4/24.
 */
public class SampleApp {
    public static void main(String[] args) {
        new SampleApp().test();
    }
    public void test() {

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hello World!");
    }
}
