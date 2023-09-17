package rgo.tt.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;
import rgo.tt.common.rest.api.config.WebCommonConfig;

@SpringBootApplication
@ConfigurationPropertiesScan
@Import(WebCommonConfig.class)
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }
}
