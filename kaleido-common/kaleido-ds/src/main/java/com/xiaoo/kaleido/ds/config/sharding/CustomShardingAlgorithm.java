package com.xiaoo.kaleido.ds.config.sharding;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Properties;

/**
 * 自定义分片算法
 * 用于处理2库4表的分片逻辑
 */
public class CustomShardingAlgorithm implements StandardShardingAlgorithm<Comparable<?>> {

    private Properties props = new Properties();

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Comparable<?>> shardingValue) {
        // 获取分片键的值
        Comparable<?> value = shardingValue.getValue();
        String shardingColumn = shardingValue.getColumnName();
        
        // 计算分片值
        long shardingValueLong = getShardingValue(value);
        
        // 总表数 = 2库 × 4表 = 8
        int totalTables = 8;
        long tableIndex = shardingValueLong % totalTables;
        
        // 计算数据库索引 (0或1)
        int databaseIndex = (int) (tableIndex / 4);
        
        // 计算表索引 (0-3)
        int tableSuffix = (int) (tableIndex % 4);
        
        // 构建目标表名
        String databaseName = "ds_" + databaseIndex;
        String tableName = shardingValue.getLogicTableName() + "_" + tableSuffix;
        
        // 返回完整表名
        return databaseName + "." + tableName;
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Comparable<?>> shardingValue) {
        // 范围查询返回所有表
        return availableTargetNames;
    }

    @Override
    public void init(Properties props) {
        this.props = props;
    }

    @Override
    public String getType() {
        return "CLASS_BASED";
    }

    /**
     * 获取分片键的long值
     */
    private long getShardingValue(Comparable<?> value) {
        if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            String strValue = (String) value;
            try {
                // 尝试解析为long
                return Long.parseLong(strValue);
            } catch (NumberFormatException e) {
                // 如果是字符串，使用hashCode
                return Math.abs(strValue.hashCode());
            }
        } else {
            // 其他类型使用hashCode
            return Math.abs(value.hashCode());
        }
    }
}
