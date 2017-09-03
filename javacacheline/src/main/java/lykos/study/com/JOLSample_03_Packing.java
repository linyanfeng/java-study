package lykos.study.com;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class JOLSample_03_Packing {

    /*
     * This is the example how VM packs the fields.
     *
     * JVMs pack the fields to minimize the memory footprint. Run
     * this example and see the fields are densely packed, and gaps
     * are minimal. It is achieved by aligning fields in 8->4->2->1
     * order, because it can not break the initial alignment, once we
     * align the 8-byte field. The gap resulted in the initial 8-byte
     * align can be taken by one or few smaller-sized fields.
     *
     * Note that the actual field order is very different from the
     * declared order. Nothing in the JVM spec requires otherwise.
     */

    public static void main(String[] args) throws Exception {
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseClass(A.class).toPrintable());
    }

    public static class A {
        boolean bo1, bo2;
        byte b1, b2;
        char c1, c2;
        double d1, d2;
        float f1, f2;
        int i1, i2;
        long l1, l2;
        short s1, s2;
    }
}
