package cwall.club.timerapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Calendar;

@SpringBootApplication(
        scanBasePackages = {"cwall.club.common","cwall.club.core","cwall.club.timerapi"}
)
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "cwall.club.core.Repository")
@EnableScheduling
@Slf4j
@EntityScan(basePackages = "cwall.club.common.Item")
@EnableTransactionManagement
public class TimerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimerApiApplication.class, args);
    }

}
