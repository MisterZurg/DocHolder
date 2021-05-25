package com.docholder;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DocholderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocholderApplication.class, args);
	}
	


//	ONLY FOR TESTING! DELETE IF PROJECT STARTING IN DOCKER
//	@Configuration
//	@EnableWebMvc
//	public class WebConfig extends WebMvcConfigurerAdapter {
//
//		@Override
//		public void addCorsMappings(CorsRegistry registry) {
//			registry.addMapping("/**");
//		}
//	}
}
