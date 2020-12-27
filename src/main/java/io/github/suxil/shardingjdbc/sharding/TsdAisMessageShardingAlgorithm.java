package io.github.suxil.shardingjdbc.sharding;

import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingValue;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

public class TsdAisMessageShardingAlgorithm implements HintShardingAlgorithm {

    @Getter
    @Setter
    private Properties properties = new Properties();

    @Override
    public Collection<String> doSharding(Collection collection, HintShardingValue hintShardingValue) {
        System.out.println("doSharding");
        return Collections.emptyList();
    }

//    @Override
//    public Collection<String> doSharding(Collection collection, ComplexKeysShardingValue complexKeysShardingValue) {
//        System.out.println("doSharding");
//        return null;
//    }

    @Override
    public void init() {
        System.out.println("init");
    }

    @Override
    public String getType() {
        System.out.println("getType");
        return "CLASS_BASED";
    }

    @Override
    public Properties getProps() {
        System.out.println("getProps");
        return properties;
    }

    @Override
    public void setProps(Properties properties) {
        System.out.println("properties");
        this.properties = properties;
    }

}
