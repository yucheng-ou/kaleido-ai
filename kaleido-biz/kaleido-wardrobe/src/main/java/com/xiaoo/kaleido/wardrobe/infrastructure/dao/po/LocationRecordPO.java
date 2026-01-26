package com.xiaoo.kaleido.wardrobe.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 位置记录持久化对象
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_wardrobe_location_record")
public class LocationRecordPO extends BasePO {

    /**
     * 服装ID
     * 关联服装表t_wardrobe_clothing
     */
    private String clothingId;

    /**
     * 位置ID
     * 关联存储位置表t_wardrobe_storage_location
     */
    private String locationId;

    /**
     * 用户ID（冗余，便于查询）
     * 关联用户表
     */
    private String userId;

    /**
     * 记录时间
     * 记录服装放入位置的时间
     */
    private Date recordTime;

    /**
     * 是否为当前位置记录
     * 0-否，1-是
     * 一件衣服在同一时间只能有一个当前位置记录
     */
    private Integer isCurrent;
}
