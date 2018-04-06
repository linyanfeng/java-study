package lykos.rabbitmq.com.rabbitmq.config;

/**
 * @author yanfenglin
 * @version 2018/4/3 16:48
 */
public class MQConstant {
    private MQConstant() {
    }

    public static final String DEFAULT_EXCHANGE = "default_exchange";
    public static final String DEFAULT_REPEAT_TRADE_QUEUE_NAME = "default_repeat_trade_queue_name";
    public static final String DEFAULT_DEAD_LETTER_QUEUE_NAME = "default_dead_letter_queue_name";
    public static final String HELLO_QUEUE_NAME = "hello_queue_name";
}
