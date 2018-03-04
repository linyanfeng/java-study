package sandbox.lykos.study.com;

/**
 * @author lykoy
 * @create 2017/9/5 上午12:51
 */
public class MyClassLoader extends ClassLoader{
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }
}
