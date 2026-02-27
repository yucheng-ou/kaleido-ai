package com.xiaoo.langchain4j.demo.controller.mcp;

import com.xiaoo.langchain4j.demo.service.ChatService;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * MCP演示控制器
 * 演示如何通过 HTTP SSE 连接到 MCP 服务器并使用其工具
 *
 * @author ouyucheng
 * @date 2026-02-27
 */
@Slf4j
@Validated
@RequestMapping("/langchain/mcp")
@RestController
@RequiredArgsConstructor
public class McpController {


    /**
     * MCP 服务器配置
     */
    private static final String MCP_SSE_URL = "http://localhost:3001/sse";

    private final OpenAiChatModel chatModel;

    /**
     * 基础 MCP 工具调用示例
     *
     * @return AI 使用 MCP 工具调用后的回复
     */
    @PostMapping("/chat")
    public String calculate(@RequestParam @NotBlank String question) {

        StreamableHttpMcpTransport transport = StreamableHttpMcpTransport
                .builder()
                .url(MCP_SSE_URL)
                .logRequests(true)
                .logResponses(true)
                .build();

        McpClient mcpClient = new DefaultMcpClient.Builder()
                .transport(transport)
                .build();

        ToolProvider toolProvider = McpToolProvider.builder()
                .mcpClients(List.of(mcpClient))
                .build();

        ChatService chatService = AiServices.builder(ChatService.class)
                .chatModel(chatModel)
                .toolProvider(toolProvider)
                .build();
            String response = chatService.chat("深圳今天天气怎么样");
            System.out.println(response);

            return response;
    }
}
