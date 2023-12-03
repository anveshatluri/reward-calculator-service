package com.retailer.rewards.rewardcalculatorservice.service;

import com.retailer.rewards.rewardcalculatorservice.entity.Customer;
import com.retailer.rewards.rewardcalculatorservice.models.Rewards;
import org.springframework.stereotype.Service;

@Service
public interface RewardService {
    public Rewards getRewardsByCustomerId(Long customerId);

    Customer addAmountToCustomer(String customerName, double amount);
}
