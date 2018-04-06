package lykos.rabbitmq.com.rabbitmq.sevice;

import lykos.rabbitmq.com.rabbitmq.config.MQConstant;
import lykos.rabbitmq.com.rabbitmq.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author yanfenglin
 * @version 2018/4/3 17:29
 */
@Service
public class MQSender {
    private Logger logger = LoggerFactory.getLogger(MQSender.class);

//    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private AmqpTemplate amqpTemplate;

    public void send(String queueName, String msg) {
        logger.info("begin to send to queue[{}] msg:{}", queueName, msg);
//        transactionTemplate.execute(new TransactionCallback<String>() {
//            @Nullable
//            @Override
//            public String doInTransaction(TransactionStatus transactionStatus) {
//                amqpTemplate.convertAndSend(queueName,content);
//                return "";
//            }
//        });
        amqpTemplate.convertAndSend(MQConstant.DEFAULT_EXCHANGE,queueName, msg);
    }

    /**
     * 延迟发送消息到队列
     *
     * @param queueName 队列名称
     * @param message   消息内容
     * @param times     延迟时间 单位毫秒
     */
    public void send(String queueName, String message, long times) {
        logger.info("begin to send to queue[{}] msg:{}", queueName, message);

        DLXMessage dlxMessage = new DLXMessage(queueName, message, times);
        MessagePostProcessor processor = message1 -> {
            message1.getMessageProperties().setExpiration(times + "");
            return message1;
        };
        dlxMessage.setExchange(MQConstant.DEFAULT_EXCHANGE);
        amqpTemplate.convertAndSend(MQConstant.DEFAULT_EXCHANGE, MQConstant.DEFAULT_DEAD_LETTER_QUEUE_NAME, JsonUtil
                .toJsonString(dlxMessage), processor);

//        transactionTemplate.execute(new TransactionCallback<String>() {
//            @Nullable
//            @Override
//            public String doInTransaction(TransactionStatus transactionStatus) {
//                if(transactionStatus.isCompleted()){
//                    logger.info("tr is completed...");
//                    amqpTemplate.convertAndSend(MQConstant.DEFAULT_EXCHANGE,MQConstant.DEFAULT_DEAD_LETTER_QUEUE_NAME,JsonUtil
//                            .toJsonString(dlxMessage), processor);
//                }
//                return "";
//            }
//        });

    }

    public static class DLXMessage implements Serializable {
        private static final long serialVersionUID = -6710319353465345855L;

        private String queueName;
        private String message;
        private long times;
        private String exchange;

        public DLXMessage() {
        }

        public DLXMessage(String queueName, String message, long times) {
            this.queueName = queueName;
            this.message = message;
            this.times = times;
        }

        public String getQueueName() {
            return queueName;
        }

        public void setQueueName(String queueName) {
            this.queueName = queueName;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public long getTimes() {
            return times;
        }

        public void setTimes(long times) {
            this.times = times;
        }

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }
    }
}
