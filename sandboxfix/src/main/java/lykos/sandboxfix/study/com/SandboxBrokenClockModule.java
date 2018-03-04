package lykos.sandboxfix.study.com;

import com.alibaba.jvm.sandbox.api.Information;
import com.alibaba.jvm.sandbox.api.Module;
import com.alibaba.jvm.sandbox.api.ProcessControlException;
import com.alibaba.jvm.sandbox.api.event.Event;
import com.alibaba.jvm.sandbox.api.filter.NameRegexFilter;
import com.alibaba.jvm.sandbox.api.http.Http;
import com.alibaba.jvm.sandbox.api.listener.EventListener;
import com.alibaba.jvm.sandbox.api.resource.ModuleEventWatcher;

import javax.annotation.Resource;

/**
 * @author lykos
 * @create 2018/2/28 下午11:30
 */
@Information(id = "broken-clock-tinker")
public class SandboxBrokenClockModule implements Module{
    @Resource
    private ModuleEventWatcher moduleEventWatcher;

    @Http("/repairCheckState")
    public void repairCheckState() {
        moduleEventWatcher.watch(
                new NameRegexFilter(".*BrokenClock", "checkState"),
                new EventListener() {
                    public void onEvent(Event event) throws Throwable {
                        ProcessControlException.throwReturnImmediately(null);
                    }
                },
                Event.Type.THROWS
        );
    }


}
