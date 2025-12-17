package com.xiaoo.kaleido.notice.domain.model.valobj;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 通知内容值对象
 * <p>
 * 不可变的通知内容，包含标题、正文、变量数据
 * </p>
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Data
@Builder
public class NoticeContent {
    /**
     * 通知标题
     */
    private final String title;

    /**
     * 通知正文
     */
    private final String content;

    /**
     * 模板变量数据
     */
    private final Map<String, Object> variables;

    /**
     * 渲染后的内容
     */
    private final String renderedContent;

    /**
     * 创建通知内容
     *
     * @param title           标题
     * @param content         内容模板
     * @param variables       变量
     * @param renderedContent 渲染后的内容
     */
    public NoticeContent(String title, String content, Map<String, Object> variables, String renderedContent) {
        this.title = title;
        this.content = content;
        this.variables = variables;
        this.renderedContent = renderedContent;
    }

    /**
     * 渲染通知内容
     *
     * @return 渲染后的内容
     */
    public String render() {
        if (renderedContent != null) {
            return renderedContent;
        }

        if (content == null || variables == null || variables.isEmpty()) {
            return content;
        }

        // 简单的变量替换
        String result = content;
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            String key = "\\{" + entry.getKey() + "\\}";
            String value = entry.getValue() != null ? entry.getValue().toString() : "";
            result = result.replaceAll(key, value);
        }
        return result;
    }
}
