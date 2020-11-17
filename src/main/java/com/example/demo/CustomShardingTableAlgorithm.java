package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;


import java.util.Collection;

@Slf4j
public class CustomShardingTableAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        log.info(availableTargetNames.toString());
        log.info(shardingValue.toString());
        Long curValue  = shardingValue.getValue();
        StringBuilder sb=new StringBuilder();
        if(shardingValue.getLogicTableName().equals("orders")){
            Long i=curValue%availableTargetNames.size();
            sb.append("orders_").append(i);
        }
        return sb.toString();
    }
}
