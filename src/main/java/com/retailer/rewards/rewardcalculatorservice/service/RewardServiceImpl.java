package com.retailer.rewards.rewardcalculatorservice.service;

import com.retailer.rewards.rewardcalculatorservice.constants.Constants;
import com.retailer.rewards.rewardcalculatorservice.entity.Customer;
import com.retailer.rewards.rewardcalculatorservice.entity.Transaction;
import com.retailer.rewards.rewardcalculatorservice.models.Rewards;
import com.retailer.rewards.rewardcalculatorservice.repository.CustomerRepository;
import com.retailer.rewards.rewardcalculatorservice.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.retailer.rewards.rewardcalculatorservice.constants.Constants.userNotFound;

@Service
@Slf4j
public class RewardServiceImpl implements RewardService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CustomerRepository customerRepository;

    public Rewards getRewardsByCustomerId(Long customerId) {

        Timestamp lastMonthTimestamp = getDateBasedOnOffSetDays(Constants.daysInMonths);
        Timestamp lastSecondMonthTimestamp = getDateBasedOnOffSetDays(2*Constants.daysInMonths);
        Timestamp lastThirdMonthTimestamp = getDateBasedOnOffSetDays(3*Constants.daysInMonths);

        List<Transaction> lastMonthTransactions = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(
                customerId, lastMonthTimestamp, Timestamp.from(Instant.now()));
        List<Transaction> lastSecondMonthTransactions = transactionRepository
                .findAllByCustomerIdAndTransactionDateBetween(customerId, lastSecondMonthTimestamp, lastMonthTimestamp);
        List<Transaction> lastThirdMonthTransactions = transactionRepository
                .findAllByCustomerIdAndTransactionDateBetween(customerId, lastThirdMonthTimestamp,
                        lastSecondMonthTimestamp);

        Long lastMonthRewardPoints = getRewardsPerMonth(lastMonthTransactions);
        Long lastSecondMonthRewardPoints = getRewardsPerMonth(lastSecondMonthTransactions);
        Long lastThirdMonthRewardPoints = getRewardsPerMonth(lastThirdMonthTransactions);

        Rewards customerRewards = new Rewards();
        customerRewards.setCustomerId(customerId);
        customerRewards.setLastMonthRewardPoints(lastMonthRewardPoints);
        customerRewards.setLastSecondMonthRewardPoints(lastSecondMonthRewardPoints);
        customerRewards.setLastThirdMonthRewardPoints(lastThirdMonthRewardPoints);
        customerRewards.setTotalRewards(lastMonthRewardPoints + lastSecondMonthRewardPoints + lastThirdMonthRewardPoints);

        return customerRewards;

    }

    public Customer addAmountToCustomer(String customerName, double amount){

        Transaction transaction = new Transaction();
        Customer customer= customerRepository.findByCustomerName(customerName);
        if(customer == null){
            log.info(userNotFound);
            Customer customer1 = new Customer();
            customer1.setCustomerName(customerName);
            customerRepository.save(customer1);
            transaction.setTransactionAmount(amount);
            transaction.setCustomerId(customer1.getCustomerId());
            transaction.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));
            transactionRepository.save(transaction);
            return customer1;
        } else {
            transaction.setTransactionAmount(amount);
            transaction.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));
            transaction.setCustomerId(customer.getCustomerId());
            transactionRepository.save(transaction);
            return customer;
        }
    }

    private Long getRewardsPerMonth(List<Transaction> transactions) {
        return transactions.stream().map(transaction -> calculateRewards(transaction))
                .collect(Collectors.summingLong(r -> r.longValue()));
    }

    private Long calculateRewards(Transaction t) {
        if (t.getTransactionAmount() > Constants.firstRewardLimit && t.getTransactionAmount() <= Constants.secondRewardLimit) {
            return Math.round(t.getTransactionAmount() - Constants.firstRewardLimit);
        } else if (t.getTransactionAmount() > Constants.secondRewardLimit) {
            return Math.round(t.getTransactionAmount() - Constants.secondRewardLimit) * 2
                    + (Constants.secondRewardLimit - Constants.firstRewardLimit);
        } else
            return 0l;

    }

    public Timestamp getDateBasedOnOffSetDays(int days) {
        return Timestamp.valueOf(LocalDateTime.now().minusDays(days));
    }
}
