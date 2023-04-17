package ar.com.mercadolibre.ipgeolocation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
public class IpGeolocationApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpGeolocationApplication.class, args);
	}

}
