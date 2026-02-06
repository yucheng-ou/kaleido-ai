package com.xiaoo.kaleido.aop.aspect;

import com.xiaoo.kaleido.aop.annotation.CheckNullReturn;
import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * 空返回值检查切面
 * <p>
 * 拦截带有 {@link CheckNullReturn} 注解的方法，检查返回值是否为空或空集合/数组，
 * 如果为空则抛出 {@link BizException}
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Slf4j
@Aspect
@Component
public class NullReturnCheckAspect {

    /**
     * 环绕通知，拦截带有 @CheckNullReturn 注解的方法
     */
    @Around("@annotation(checkNullReturn)")
    public Object checkNullReturn(ProceedingJoinPoint joinPoint, CheckNullReturn checkNullReturn) throws Throwable {
        // 执行原方法
        Object result = joinPoint.proceed();

        // 检查返回值
        if (isNullReturn(result, checkNullReturn.checkEmptyCollection())) {
            // 抛出业务异常 错误码默认为数据不存在
            throw BizException.of(BizErrorCode.DATA_NOT_EXIST.getCode(), checkNullReturn.errorMessage());
        }

        return result;
    }

    /**
     * 判断返回值是否为空
     *
     * @param result               返回值
     * @param checkEmptyCollection 是否检查空集合/数组
     * @return true 表示空返回值，应抛出异常
     */
    private boolean isNullReturn(Object result, boolean checkEmptyCollection) {
        if (result == null) {
            return true;
        }

        if (!checkEmptyCollection) {
            return false;
        }

        // 检查集合类型
        if (result instanceof Collection) {
            return CollectionUtils.isEmpty((Collection<?>) result);
        }
        // 检查数组类型
        if (result.getClass().isArray()) {
            Object[] array = (Object[]) result;
            return array.length == 0;
        }
        // 检查Map类型
        if (result instanceof Map) {
            return ((Map<?, ?>) result).isEmpty();
        }

        // 其他类型不检查空集合
        return false;
    }
}