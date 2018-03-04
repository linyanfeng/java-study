package lykos.study.com.instrument;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Created by fengfu.qu on 2016/4/24.
 */
public class SampleTransformer implements ClassFileTransformer {
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {


        if (!"lykos/study/com/instrument/SampleApp".equals(className)){
            return classfileBuffer;
        }

        byte[] transformed = null;
        System.out.println("Transforming " + className);

        ClassPool pool = ClassPool.getDefault();
        CtClass cl = null;

        try {
            cl = pool.makeClass(new java.io.ByteArrayInputStream(classfileBuffer));

            if (cl.isInterface() == false) {
                CtBehavior[] methods = cl.getDeclaredBehaviors();
                for (int i = 0; i < methods.length; i++) {
                    if (!methods[i].isEmpty()) {
                        transformMethod(methods[i]);
                    }
                }
                transformed = cl.toBytecode();
            }
        } catch (Exception e) {
            System.err.println("Could not transform instrument  " + className + ",  exception : " + e.getMessage());
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
        return transformed;
    }

    private void transformMethod(CtBehavior method) throws NotFoundException,
            CannotCompileException {

        method.instrument(new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException {
                /**
                 * wiki:http://blog.csdn.net/u011425751/article/details/51917895
                 */
                m.replace("{ long stime = System.currentTimeMillis(); $_ = $proceed($$); System.out.println(\""
                                + m.getClassName()+"."+m.getMethodName()
                                + ":\"+(System.currentTimeMillis()-stime));}");
            }
        });
    }
}
