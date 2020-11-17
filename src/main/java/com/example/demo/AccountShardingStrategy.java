package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

@Slf4j
public class AccountShardingStrategy implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        log.info(availableTargetNames.toString());
        log.info(shardingValue.toString());
        Long curValue  = shardingValue.getValue();
        StringBuilder sb=new StringBuilder();
        if(shardingValue.getLogicTableName().equals("account")){
            Long i=curValue%availableTargetNames.size();
            sb.append("account_").append(i);
        }
        return sb.toString();
    }
}
