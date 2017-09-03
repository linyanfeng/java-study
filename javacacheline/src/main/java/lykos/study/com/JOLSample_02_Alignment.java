package lykos.study.com;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class JOLSample_02_Alignment {
    /*
     * This is the more advanced field layout example.
     *
     * Because the underlying hardware platform often requires aligned accesses
     * to maintain the performance and correctness, it is expected the fields
     * are aligned by their size. For booleans it does not mean anything, but
     * for longs it's different. In this example, we can see the long field
     * is indeed aligned for 8 bytes, sometimes making the gap after the
     * object header.
     */

    public static void main(String[] args) throws Exception {
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseClass(A.class).toPrintable());
    }

    public static class A {
        long f;
    }
}
