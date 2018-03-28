package lykos.study.com;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author yanfenglin
 * @version 2018/3/5 16:23
 */
public class WeakReferenceTest {
    private final ReferenceQueue<Object> rQueue = new ReferenceQueue<Object>(); //|

    public static void main(String[] args) throws InterruptedException {
//        testGcNotOom();
//        testGcButOom();
        testGcNotOom();
    }

    /**
     * -Xmx30m
     * oom
     */
    public static void testGcButOom() {
        List<WeakHashMap<byte[][], byte[][]>> maps = new ArrayList<WeakHashMap<byte[][], byte[][]>>();
        for (int i = 0; i < 1000; i++) {
            WeakHashMap<byte[][], byte[][]> d = new WeakHashMap<byte[][], byte[][]>();
            d.put(new byte[1000][1000], new byte[1000][1000]);
            maps.add(d);
//            System.gc();
            System.err.println(i);
        }
        System.out.println(maps.size());
    }

    /**
     * -Xmx30m
     * d.size() 触发 expungeStaleEntries(); 进行回收
     * wiki：https://hongjiang.info/java-referencequeue/
     *
     * @throws InterruptedException
     */
    public static void testGcNotOom() throws InterruptedException {
        List<WeakHashMap<byte[][], byte[][]>> maps = new ArrayList<WeakHashMap<byte[][], byte[][]>>();
        for (int i = 0; i < 1000; i++) {
            WeakHashMap<byte[][], byte[][]> d = new WeakHashMap<byte[][], byte[][]>();
            d.put(new byte[1000][1000], new byte[1000][1000]);
            maps.add(d);
            System.gc();
            TimeUnit.MILLISECONDS.sleep(10);
            System.out.println(i + " size:" + d.size());

        }
        System.out.println("map size:"+  maps.size());
        for (WeakHashMap<byte[][], byte[][]> map : maps) {
            System.out.println(map.keySet());
        }


    }

    /**
     * -Xmx30m
     * 没有oom，说明gc 对WeakHashMap 的key 进行回收
     *
     */
    public static void testGcNotOom2() {
        List<WeakHashMap<byte[][], Object>> maps = new ArrayList<WeakHashMap<byte[][], Object>>();
        for (int i = 0; i < 1000; i++) {
            WeakHashMap<byte[][], Object> d = new WeakHashMap<byte[][], Object>();
            d.put(new byte[1000][1000], new Object());
            maps.add(d);
            System.gc();
            System.out.println(i);
        }
    }

    private class IdentityWeakReference extends WeakReference<Object> {

        // 对应的对象ID
        private final Integer objectID;

        private IdentityWeakReference(final Integer objectID,
                                      final Object referent) {
            super(referent, rQueue);
            this.objectID = objectID;
        }

    }
}
