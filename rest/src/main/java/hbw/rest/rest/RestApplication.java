package hbw.rest.rest;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"hbw.rest.rest.controller"})
public class RestApplication {

	public static void main(String[] args) {
//		SpringApplication.run(RestApplication.class, args);
		
		SpringApplication app = new SpringApplication(RestApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
}
