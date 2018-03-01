package lykos.sandbox.study.com;

/**
 * @author lykoy
 * @create 2017/9/5 上午12:54
 * <p>
 * 通过自定义的MyClassLoader类加载器的loadClass()方法来加载类，称其为初始类加载器。
 * 但实际加载类的加载器却是AppClassLoader，因此AppClassLoader被称为定义类加载器。
 * <p>
 * 类加载器的命名空间
 * 每个类加载器有自己的命名空间，命名空间由所有以此加载器为初始加装载器的类组成。
 * 不同类加载器的命名空间关系：
 * 1、同一个命名空间内的类是相互可见的，即可以互相访问。
 * 2、父加载器的命名空间对子加载器可见。
 * 3、子加载器的命名空间对父加载器不可见。
 * 4、如果两个加载器之间没有直接或间接的父子关系，那么它们各自加载的类相互不可见。
 *
 * wiki:http://blog.csdn.net/sunxianghuang/article/details/52093189
 */
public class MainTest {

    public static void main(String[] args) throws Exception {

        MyClassLoader myClassLoader = new MyClassLoader();
        Object newInstance = myClassLoader.loadClass("ObjectA").newInstance();
        ObjectA objectA = new ObjectA();
        System.out.println("load by MyClassLoader :" + newInstance.getClass().getClassLoader());
        System.out.println("load by system :" + newInstance.getClass().getClassLoader());
    }
}
