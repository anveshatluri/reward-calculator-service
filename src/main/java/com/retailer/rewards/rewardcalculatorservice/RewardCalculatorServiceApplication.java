package com.retailer.rewards.rewardcalculatorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan("com.retailer.rewards.rewardcalculatorservice.service")
@EntityScan("com.retailer.rewards.rewardcalculatorservice.entity")
public class RewardCalculatorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardCalculatorServiceApplication.class, args);
	}

}
