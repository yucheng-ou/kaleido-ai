package com.xiaoo.kaleido.aop.annotation;

import java.lang.annotation.*;

/**
 * 空返回值检查注解
 * 标注在方法上，当方法返回值为null或空集合/空数组时抛出业务异常
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckNullReturn {

    /**
     * 是否检查集合/数组类型的空值（size == 0）
     */
    boolean checkEmptyCollection() default false;

    /**
     * 错误信息
     */
    String errorMessage() default "数据不存在";
}