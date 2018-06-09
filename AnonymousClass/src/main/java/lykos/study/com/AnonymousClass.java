package lykos.study.com;

/**
 * Hello world!
 */
public class AnonymousClass {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        int j = 0;
        final Integer outValue = -1 * (1 + j);

        /**
         * final class AnonymousClass$1 implements AnonymousClassInterface {
         *     AnonymousClass$1(Integer var1) {
         *         this.val$outValue = var1;
         *     }
         *
         *     public Integer exe(Integer var1) {
         *         return var1 * this.val$outValue;
         *     }
         * }
         * this.val$outValue 在构造函数中传入
         * 如果在这个内部类里面修改this.val$outValue的值，并不能改变外部类中outValue的值，造成数据不一致
         * 结论：
         * Java为了避免数据不同步的问题，做出了匿名内部类只可以访问final的局部变量的限制。
         */
        AnonymousClassInterface classInterface = new AnonymousClassInterface() {
            @Override
            public Integer exe(Integer val) {
                /**
                 *class:
                 *
                 */
                return val * outValue;
            }
        };
        System.out.println(classInterface.exe(10));
    }
}
