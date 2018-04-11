package lykos.rabbitmq.com.rabbitmq.sevice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @author yanfenglin
 * @version 2018/4/7 22:43
 */
@Component
public class MQServerDone implements BeanPostProcessor {
    private Logger logger = LoggerFactory.getLogger(MQServerDone.class);

    @Nullable
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MQSender) {
            logger.info("BeforeInitialization...");
        }
        return bean;
    }

    @Nullable
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MQSender) {
            logger.info("AfterInitialization...");
        }
        return bean;
    }
}
