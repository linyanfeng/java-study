package lykos.study.com;

import com.alibaba.jvm.sandbox.api.Information;
import com.alibaba.jvm.sandbox.api.Module;
import com.alibaba.jvm.sandbox.api.event.Event;
import com.alibaba.jvm.sandbox.api.filter.NameRegexFilter;
import com.alibaba.jvm.sandbox.api.http.Http;
import com.alibaba.jvm.sandbox.api.listener.EventListener;
import com.alibaba.jvm.sandbox.api.resource.ModuleEventWatcher;

import javax.annotation.Resource;

/**
 * @author yanfenglin
 * @version 2018/2/28 18:25
 */
@Information(id = "fieldQualifyController")
public class SandboxTest implements Module {
    @Resource
    private ModuleEventWatcher moduleEventWatcher;

    @Http("/repairCheckState")
    public void repairCheckState() {

        moduleEventWatcher.watch(

                // 匹配到Clock$BrokenClock#checkState()
                new NameRegexFilter("FieldQualifyAppOperatorController", "operate"),
                new EventListener() {
                    public void onEvent(Event event) throws Throwable {
                        System.out.println("hi,i am sandbox!");
                    }
                },
                Event.Type.BEFORE);
    }
}
