package com.xiaoo.kaleido.ds.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.xiaoo.kaleido.ds.handler.MyMetaObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 数据源自动配置类
 * <p>
 * 该类是Spring Boot自动配置类，用于配置MyBatis Plus的相关组件。
 * 通过{@link AutoConfiguration}注解，Spring Boot会自动加载该配置。
 * <p>
 * 主要功能包括：
 * <ul>
 *   <li>配置乐观锁拦截器，支持乐观锁功能</li>
 *   <li>配置防止全表更新拦截器，增强数据安全</li>
 *   <li>配置分页拦截器，支持MySQL数据库的分页查询</li>
 *   <li>通过{@link Import}注解导入{@link MyMetaObjectHandler}，实现自动填充功能</li>
 * </ul>
 *
 * @author ouyucheng
 * @version 1.0
 * @date 2025/11/5 11:19
 */
@AutoConfiguration
@Import({MyMetaObjectHandler.class})
@MapperScan(basePackages = "com.xiaoo.kaleido.*.infrastructure.dao")
public class DataSourceAutoConfiguration {

    /**
     * 配置MyBatis Plus拦截器
     * <p>
     * 该方法创建并配置MyBatis Plus的拦截器链，包含以下拦截器：
     * <ul>
     *   <li>{@link OptimisticLockerInnerInterceptor} - 乐观锁拦截器，用于实现乐观锁功能</li>
     *   <li>{@link BlockAttackInnerInterceptor} - 防止全表更新拦截器，避免误操作导致全表数据被修改</li>
     *   <li>{@link PaginationInnerInterceptor} - 分页拦截器，配置为MySQL数据库类型，支持分页查询</li>
     * </ul>
     *
     * @return MybatisPlusInterceptor 配置好的拦截器实例
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();

        // 添加乐观锁拦截器
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        // 添加防止全表更新拦截器
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        // 添加分页拦截器，配置为MySQL数据库
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        return mybatisPlusInterceptor;
    }
}
