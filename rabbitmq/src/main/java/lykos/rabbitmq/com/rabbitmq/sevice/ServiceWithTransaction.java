package lykos.rabbitmq.com.rabbitmq.sevice;

import lykos.rabbitmq.com.rabbitmq.config.UserRepository;
import lykos.rabbitmq.com.rabbitmq.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author yanfenglin
 * @version 2018/4/4 11:19
 */
@Service
public class ServiceWithTransaction {
    private Logger logger = LoggerFactory.getLogger(ServiceWithTransaction.class);
    @Resource
    private MQSender mqSender;
    @Resource
    private UserRepository userRepository;

    @Transactional
    public void sendWithTransaction(String queue, String msg, long time) {
        //store db
        userRepository.save(getUser());

        logger.info("begin to send msg....");
        mqSender.send(queue, msg, time);
        logger.info("send msg done...");
        int i = 0, j = 3;
        if (i + 3 == j) {
            logger.info("ex  occur...");
            throw new RuntimeException("test send msg ex");
        }
    }

    private User getUser() {
        User s = new User();
        s.setAge(100);
        s.setId(1L);
        s.setName("test_name");
        return s;
    }
}
