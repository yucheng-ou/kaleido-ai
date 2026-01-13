package com.xiaoo.kaleido.admin.domain.user.service;

import com.xiaoo.kaleido.admin.types.exception.AdminException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 领域服务抽象基类
 * 提供通用的模板方法，减少重复代码
 *
 * @param <T> 聚合根类型
 * @param <ID> ID类型
 * @author ouyucheng
 * @date 2026/1/9
 */
@Slf4j
public abstract class AbstractAdminDomainService<T, ID> {


    /**
     * 执行有返回值的操作（模板方法）
     * 提供统一的异常处理和日志记录
     *
     * @param id 实体ID
     * @param operation 操作函数
     * @param operationName 操作名称（用于日志）
     * @return 操作结果
     * @throws AdminException 当实体不存在或操作失败时抛出
     */
    protected T executeOperationWithResult(ID id, Function<T, T> operation, String operationName) {
        try {
            // 1. 根据ID查找实体
            T entity = findEntityById(id);
            log.info("开始{}，ID: {}", operationName, id);
            
            // 2. 执行操作
            T result = operation.apply(entity);
            
            // 3. 记录操作完成日志
            log.info("{}完成，ID: {}", operationName, id);
            return result;
        } catch (Exception e) {
            // 4. 记录操作失败日志并重新抛出异常
            log.error("{}失败，ID: {}", operationName, id, e);
            throw e;
        }
    }

    /**
     * 根据ID查找实体，不存在则抛出异常（抽象方法，子类实现）
     * 这个方法应该由子类实现，用于模板方法内部调用
     * 
     * @param id 实体ID
     * @return 实体对象
     * @throws AdminException 当实体不存在时抛出
     */
    protected abstract T findEntityById(ID id);
}
