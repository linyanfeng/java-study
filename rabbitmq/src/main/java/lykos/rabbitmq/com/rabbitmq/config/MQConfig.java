package lykos.rabbitmq.com.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yanfenglin
 * @version 2018/4/3 16:42
 */
@Configuration
public class MQConfig {
    private Logger logger = LoggerFactory.getLogger(MQConfig.class);



    @Bean
    public Queue queue() {
        return new Queue(MQConstant.HELLO_QUEUE_NAME, true);
    }

    @Bean
    public Queue repeatTradeQueue() {
        return new Queue(MQConstant.DEFAULT_REPEAT_TRADE_QUEUE_NAME, true, false, false);
    }

    @Bean
    public Binding drepeatTradeBinding() {
        return BindingBuilder.bind(repeatTradeQueue()).to(defaultExchange()).with(MQConstant.DEFAULT_REPEAT_TRADE_QUEUE_NAME);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(defaultExchange()).with(MQConstant.DEFAULT_DEAD_LETTER_QUEUE_NAME);
    }

    @Bean
    public Queue deadLetterQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", MQConstant.DEFAULT_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", MQConstant.DEFAULT_REPEAT_TRADE_QUEUE_NAME);
        Queue queue = new org.springframework.amqp.core.Queue(MQConstant.DEFAULT_DEAD_LETTER_QUEUE_NAME, true, false, false, arguments);
        logger.info("arguments :{}", queue.getArguments());
        return queue;
    }

    @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(rabbitTemplate, "exchangemsxferror", "routingkeymsxferror");
    }

    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(MQConstant.DEFAULT_EXCHANGE, true, false);
    }
    @Bean
    public RabbitTransactionManager rabbitTransactionManager(ConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
    }


    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(MQConstant.HELLO_QUEUE_NAME);
    }

//    @Bean
//    public TransactionTemplate transactionTemplate(ConnectionFactory connectionFactory) {
//        RabbitTransactionManager transactionManager = new RabbitTransactionManager(connectionFactory);
//        TransactionTemplate template = new TransactionTemplate(transactionManager);
//        RetryTemplate retryTemplate = new RetryTemplate();
//        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
//        backOffPolicy.setInitialInterval(500);
//        backOffPolicy.setMultiplier(10.0);
//        backOffPolicy.setMaxInterval(10000);
//        retryTemplate.setBackOffPolicy(backOffPolicy);
//        template.setRetryTemplate(retryTemplate);

//        template.setConfirmCallback((correlationData, ack, cause) -> {
//            if (!ack) {
//                logger.warn("send message failed: " + cause + correlationData.toString());
//            }
//        });
//
//        template.setReturnCallback((message, replyCode, replyText, tmpExchange, tmpRoutingKey) -> {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                logger.error("sleep error:", e);
//            }
//
//            logger.error("send message failed: " + replyCode + " " + replyText);
//            template.send(message);
//        });
//        template.setChannelTransacted(true);
//        return template;
//    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        RetryTemplate retryTemplate = new RetryTemplate();
//        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
//        backOffPolicy.setInitialInterval(500);
//        backOffPolicy.setMultiplier(10.0);
//        backOffPolicy.setMaxInterval(10000);
//        retryTemplate.setBackOffPolicy(backOffPolicy);
//        template.setRetryTemplate(retryTemplate);

//        template.setConfirmCallback((correlationData, ack, cause) -> {
//            if (!ack) {
//                logger.warn("send message failed: " + cause + correlationData.toString());
//            }
//        });
//
        template.setReturnCallback((message, replyCode, replyText, tmpExchange, tmpRoutingKey) -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                logger.error("sleep error:", e);
            }

            logger.error("send message failed: " + replyCode + " " + replyText);
            template.send(message);
        });
        template.setChannelTransacted(true);
        return template;
    }
}
