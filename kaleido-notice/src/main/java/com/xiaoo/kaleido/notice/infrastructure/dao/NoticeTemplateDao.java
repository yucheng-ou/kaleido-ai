package com.xiaoo.kaleido.notice.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoo.kaleido.api.notice.query.NoticeTemplatePageQueryReq;
import com.xiaoo.kaleido.notice.infrastructure.dao.po.NoticeTemplatePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知模板Mapper接口
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Mapper
public interface NoticeTemplateDao extends BaseMapper<NoticeTemplatePO> {

    /**
     * 根据模板编码查找模板
     *
     * @param templateCode 模板编码
     * @return 模板持久化对象
     */
    NoticeTemplatePO findByTemplateCode(@Param("templateCode") String templateCode);

    /**
     * 检查模板编码是否存在
     *
     * @param templateCode 模板编码
     * @return 是否存在
     */
    boolean existsByTemplateCode(@Param("templateCode") String templateCode);

    /**
     * 根据通知类型查找模板列表
     *
     * @param noticeType 通知类型
     * @return 模板列表
     */
    List<NoticeTemplatePO> findByNoticeType(@Param("noticeType") String noticeType);

    /**
     * 根据业务类型查找模板列表
     *
     * @param businessType 业务类型
     * @return 模板列表
     */
    List<NoticeTemplatePO> findByBusinessType(@Param("businessType") String businessType);

    /**
     * 根据条件分页查询模板
     *
     * @param req 查询条件
     * @return 模板列表
     */
    List<NoticeTemplatePO> selectByCondition(@Param("req") NoticeTemplatePageQueryReq req);

    /**
     * 查找启用的模板列表
     *
     * @return 启用的模板列表
     */
    List<NoticeTemplatePO> findEnabledTemplates();
}
