package cwall.club.common.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class BaseConfig {
    @Value("${settings.url}")
    private String url;
}
