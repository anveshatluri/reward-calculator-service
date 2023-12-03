package com.retailer.rewards.rewardcalculatorservice.controller;

import com.retailer.rewards.rewardcalculatorservice.entity.Customer;
import com.retailer.rewards.rewardcalculatorservice.models.Rewards;
import com.retailer.rewards.rewardcalculatorservice.repository.CustomerRepository;
import com.retailer.rewards.rewardcalculatorservice.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class RewardsController {

    private final RewardService rewardService;
    @Autowired
    public RewardsController(RewardService rewardService){
        this.rewardService = rewardService;
    }

    @Autowired
    CustomerRepository customerRepository;
    @GetMapping(value = "/{customerId}/rewards",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rewards> getRewardsByCustomerId(@PathVariable("customerId") Long customerId){
        Customer customer = customerRepository.findByCustomerId(customerId);
        if(customer == null)
        {
            throw new RuntimeException("Invalid / Missing customer Id ");
        }
        Rewards customerRewards = rewardService.getRewardsByCustomerId(customerId);
        return new ResponseEntity<>(customerRewards, HttpStatus.OK);
    }

    @GetMapping(value = "/add/amount",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> addCustomerAmountToCaliculateRewards(@RequestParam String customerName, @RequestParam double amount){

        Customer findOrAddCustomer = rewardService.addAmountToCustomer(customerName, amount);
        return new ResponseEntity<>(findOrAddCustomer, HttpStatus.CREATED);
    }
}
