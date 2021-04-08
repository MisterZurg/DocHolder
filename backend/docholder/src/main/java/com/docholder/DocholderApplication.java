package com.docholder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


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
