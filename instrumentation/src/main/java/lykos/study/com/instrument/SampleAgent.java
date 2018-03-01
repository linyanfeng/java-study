package lykos.study.com.instrument;

import java.lang.instrument.Instrumentation;

/**
 * Created by fengfu.qu on 2016/4/24.
 */
public class SampleAgent {
    static private Instrumentation inst = null;

    public static void premain(String agentArgs, Instrumentation _inst){
        inst = _inst;

        System.out.println("Adding a SampleTransformer instance to the JVM.");
        inst.addTransformer(new SampleTransformer());
    }
}
