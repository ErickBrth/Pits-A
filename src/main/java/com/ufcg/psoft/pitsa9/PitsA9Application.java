package com.ufcg.psoft.pitsa9;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PitsA9Application {

    @Bean
    public ModelMapper getModelMapper() {
    	ModelMapper modelMapper = new ModelMapper();
    	modelMapper.getConfiguration()
    		.setFieldMatchingEnabled(true)
    		.setFieldAccessLevel(AccessLevel.PRIVATE);
    	
    	return modelMapper;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(PitsA9Application.class, args);
    }
}