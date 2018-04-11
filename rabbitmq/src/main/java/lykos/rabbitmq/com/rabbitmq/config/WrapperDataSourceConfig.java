package lykos.rabbitmq.com.rabbitmq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

/**
 * @author yanfenglin
 * @version 2017/11/27 11:17
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "wrapperEntityManagerFactory",
        transactionManagerRef = "wrapperTransactionManager",
        basePackages = {"lykos.rabbitmq.com.rabbitmq.config"})
public class WrapperDataSourceConfig {

    @Resource
    private DataSource dataSource;


    @Primary
    @Bean(name = "wrapperEntityManager")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return wrapperEntityManagerFactory(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = "wrapperEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean wrapperEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .properties(jpaProperties.getProperties())
                .packages("lykos.rabbitmq.com.rabbitmq.model") //设置实体类所在位置
                .persistenceUnit("cmsWrapperPersistenceUnit")
                .build();
    }

    @Autowired
    private JpaProperties jpaProperties;

    @Bean(name = "wrapperTransactionManager")
    @Primary
    PlatformTransactionManager transactionManagerSecondary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(wrapperEntityManagerFactory(builder).getObject());
    }

}
