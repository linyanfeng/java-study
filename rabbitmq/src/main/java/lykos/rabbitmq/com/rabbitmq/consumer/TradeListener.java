package lykos.rabbitmq.com.rabbitmq.consumer;

import lykos.rabbitmq.com.rabbitmq.config.MQConstant;
import lykos.rabbitmq.com.rabbitmq.sevice.MQSender;
import lykos.rabbitmq.com.rabbitmq.sevice.MQSender.DLXMessage;
import lykos.rabbitmq.com.rabbitmq.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author yanfenglin
 * @version 2018/4/3 18:07
 */
@Service
public class TradeListener {
    private Logger logger = LoggerFactory.getLogger(HelloListener.class);
    @Resource
    private MQSender mqSender;

    @Transactional
    @RabbitListener(queues = MQConstant.DEFAULT_REPEAT_TRADE_QUEUE_NAME)
    public void helloProcess(Message message) {
        String body = new String(message.getBody());
        logger.info("rev msg:{}", body);
        DLXMessage dlxMessage = JsonUtil.parseJson(body, DLXMessage.class).orElseThrow(() -> new RuntimeException("parse json error"));

        mqSender.send(dlxMessage.getQueueName(), dlxMessage.getMessage());
    }
}
