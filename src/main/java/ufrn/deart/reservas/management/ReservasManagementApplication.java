package ufrn.deart.reservas.management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
public class ReservasManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservasManagementApplication.class, args);
    }


}
