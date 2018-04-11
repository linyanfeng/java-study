package lykos.rabbitmq.com.rabbitmq.config;

import lykos.rabbitmq.com.rabbitmq.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author zhiyingyang2
 * @version 2017/12/07 12:55
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}
