package lykos.rabbitmq.com.rabbitmq.consumer;

import lykos.rabbitmq.com.rabbitmq.config.MQConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yanfenglin
 * @version 2018/4/3 17:20
 */
@Service
public class HelloListener {
    private Logger logger = LoggerFactory.getLogger(HelloListener.class);

    @RabbitListener(queues = MQConstant.HELLO_QUEUE_NAME)
    public void helloProcess(Message message) {
        String body = new String(message.getBody());
        logger.info("rev msg:{}", body);
    }
}
