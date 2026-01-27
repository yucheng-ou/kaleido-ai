package com.xiaoo.kaleido.recommend.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.recommend.infrastructure.dao.po.RecommendRecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 推荐记录数据访问接口
 * <p>
 * 负责推荐记录表的CRUD操作
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Mapper
public interface RecommendRecordDao extends BaseMapper<RecommendRecordPO> {

    /**
     * 根据用户ID查询推荐记录列表
     *
     * @param userId 用户ID
     * @return 推荐记录列表
     */
    List<RecommendRecordPO> findByUserId(@Param("userId") String userId);

    /**
     * 根据穿搭ID查询推荐记录
     *
     * @param outfitId 穿搭ID
     * @return 推荐记录持久化对象
     */
    RecommendRecordPO findByOutfitId(@Param("outfitId") String outfitId);

    /**
     * 根据用户ID和是否有关联穿搭查询推荐记录
     *
     * @param userId 用户ID
     * @param hasOutfit 是否有关联穿搭
     * @return 推荐记录列表
     */
    List<RecommendRecordPO> findByUserIdAndHasOutfit(@Param("userId") String userId, @Param("hasOutfit") boolean hasOutfit);
}
