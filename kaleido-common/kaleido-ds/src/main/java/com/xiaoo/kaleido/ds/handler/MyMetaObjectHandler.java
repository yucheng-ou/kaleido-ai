package com.xiaoo.kaleido.ds.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * MyBatis Plus元对象处理器
 * 
 * <p>该类实现了{@link MetaObjectHandler}接口，用于自动填充实体类的公共字段。</p>
 * 
 * <p>主要功能包括：
 * <ul>
 *   <li>在插入操作时自动填充创建时间、更新时间、删除状态和乐观锁版本号</li>
 *   <li>在更新操作时自动填充更新时间</li>
 *   <li>提供智能填充机制，仅在字段值为null时进行填充，避免覆盖已有值</li>
 * </ul>
 * </p>
 * 
 * @author ouyucheng
 * @date 2025/11/5 11:40
 * @version 1.0
 */
public class MyMetaObjectHandler implements MetaObjectHandler {
    
    /**
     * 插入操作时的自动填充处理
     * 
     * <p>在实体类插入数据库时自动填充以下字段：</p>
     * <ul>
     *   <li>createdAt - 创建时间，如果字段值为null则设置为当前时间</li>
     *   <li>updatedAt - 更新时间，如果字段值为null则设置为当前时间</li>
     *   <li>deleted - 删除状态，强制设置为0（未删除）</li>
     *   <li>lockVersion - 乐观锁版本号，强制设置为0（初始版本）</li>
     * </ul>
     * 
     * @param metaObject 元对象，包含实体类的字段信息
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 填充创建时间（仅在字段值为null时填充）
        this.setFieldValByNameIfNull("createdAt", new Date(), metaObject);
        // 填充更新时间（仅在字段值为null时填充）
        this.setFieldValByNameIfNull("updatedAt", new Date(), metaObject);
        // 强制设置删除状态为0（未删除）
        this.setFieldValByName("deleted", 0, metaObject);
        // 强制设置乐观锁版本号为0（初始版本）
        this.setFieldValByName("lockVersion", 0, metaObject);
    }

    /**
     * 条件设置字段值（仅在字段值为null时设置）
     * 
     * <p>该方法提供智能填充机制，只有在字段值为null时才进行填充，
     * 避免在单元测试或其他场景中覆盖已经设置的值。</p>
     *
     * @param fieldName 字段名称
     * @param fieldVal 字段值
     * @param metaObject 元对象
     */
    private void setFieldValByNameIfNull(String fieldName, Object fieldVal, MetaObject metaObject) {
        if (metaObject.getValue(fieldName) == null) {
            this.setFieldValByName(fieldName, fieldVal, metaObject);
        }
    }

    /**
     * 更新操作时的自动填充处理
     * 
     * <p>在实体类更新数据库时自动填充更新时间字段：</p>
     * <ul>
     *   <li>updatedAt - 更新时间，强制设置为当前时间</li>
     * </ul>
     * 
     * @param metaObject 元对象，包含实体类的字段信息
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 强制设置更新时间为当前时间
        this.setFieldValByName("updatedAt", new Date(), metaObject);
    }
}
