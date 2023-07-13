package rgo.tt.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import rgo.tt.common.rest.api.config.WebCommonConfig;

@SpringBootApplication
@Import(WebCommonConfig.class)
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }
}
