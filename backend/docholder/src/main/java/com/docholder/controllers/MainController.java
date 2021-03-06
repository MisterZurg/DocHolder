package com.docholder.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

@RestController
public class MainController {
	
	@RequestMapping("/")
    public Map<String, String> home(){
		HashMap<String, String> map = new HashMap<>();
	    map.put("key", "value");
	    map.put("foo", "bar");
	    map.put("aa", "bb");
	    return map;
    }

	
	
	@Configuration
	public class WebConfig extends WebMvcConfigurerAdapter {

	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
	    }
	}

}