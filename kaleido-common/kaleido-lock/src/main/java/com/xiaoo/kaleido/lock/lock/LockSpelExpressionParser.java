package com.xiaoo.kaleido.lock.lock;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * SpEL 表达式解析器
 *
 * @author ouyucheng
 * @date 2026/1/27
 */
@Component
public class LockSpelExpressionParser {
    
    private final ExpressionParser parser = new SpelExpressionParser();
    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    
    /**
     * 解析 SpEL 表达式
     *
     * @param expression SpEL 表达式
     * @param method 方法
     * @param args 方法参数
     * @return 解析后的字符串
     */
    public String parse(String expression, Method method, Object[] args) {
        // 创建评估上下文
        MethodBasedEvaluationContext context = new MethodBasedEvaluationContext(
            null, method, args, parameterNameDiscoverer);
        
        // 解析表达式
        Expression expr = parser.parseExpression(expression);
        Object value = expr.getValue(context);
        
        return value != null ? value.toString() : "";
    }
    
    /**
     * 解析 SpEL 表达式，返回指定类型
     *
     * @param expression SpEL 表达式
     * @param method 方法
     * @param args 方法参数
     * @param clazz 返回类型
     * @return 解析后的值
     */
    public <T> T parse(String expression, Method method, Object[] args, Class<T> clazz) {
        // 创建评估上下文
        MethodBasedEvaluationContext context = new MethodBasedEvaluationContext(
            null, method, args, parameterNameDiscoverer);
        
        // 解析表达式
        Expression expr = parser.parseExpression(expression);
        return expr.getValue(context, clazz);
    }
}
