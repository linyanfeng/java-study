package lykos.study.com;

/**
 * -XX:-UseCompressedOops OutOfMemoryError
 *
 * wiki:http://blog.csdn.net/lqp276/article/details/52231261
 */
public class Test {
    public static void main(String[] args) throws Exception {
        // 512M个引用槽位
        final int count = 512 * 1024 * 1024;

        Object[] array = new Object[count];

        Thread.sleep(1000000);
    }
}
