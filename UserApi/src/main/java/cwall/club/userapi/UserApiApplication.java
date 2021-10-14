package cwall.club.userapi;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(
        scanBasePackages = {"cwall.club.common","cwall.club.core","cwall.club.userapi"}
)
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "cwall.club.core.Repository")
@EnableScheduling
@Slf4j
@EntityScan(basePackages = "cwall.club.common.Item")
@EnableTransactionManagement
public class UserApiApplication {
    @Bean("jackson2ObjectMapperBuilderCustomizer")
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        Jackson2ObjectMapperBuilderCustomizer customizer = new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance)
                        .serializerByType(Long.TYPE, ToStringSerializer.instance);
            }
        };
        return customizer;
    }
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(UserApiApplication.class, args);
        ConfigurableEnvironment env = context.getEnvironment();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        log.info("-------------------------------------------------------");
        log.info("访问地址: http://127.0.0.1:{}{}", port, path);
        log.info("接口文档: http://127.0.0.1:{}{}/swagger-ui.html", port, path);
        log.info("-------------------------------------------------------");
    }

}
