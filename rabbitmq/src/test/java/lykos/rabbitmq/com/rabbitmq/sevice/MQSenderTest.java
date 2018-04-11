package lykos.rabbitmq.com.rabbitmq.sevice;

import lykos.rabbitmq.com.rabbitmq.config.MQConstant;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author yanfenglin
 * @version 2018/4/3 18:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MQSenderTest {
    @Resource
    private MQSender sender;
    @Resource
    private ServiceWithTransaction serviceWithTransaction;

    @Test
    public void MQsender(){
        
    }
    @Test
    public void send() throws Exception {
        try {
            serviceWithTransaction.sendWithTransaction( MQConstant.HELLO_QUEUE_NAME,"this is a delay msg",1);
        }finally {
            TimeUnit.SECONDS.sleep(20);
        }
    }

}