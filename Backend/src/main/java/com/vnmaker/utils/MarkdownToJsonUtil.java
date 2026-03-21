package com.vnmaker.utils;

import com.alibaba.fastjson2.JSON;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import com.vnmaker.entity.Dialogue;

public class MarkdownToJsonUtil {

    /**
     * 读取markdown文件内容
     * @param filePath markdown文件路径
     * @return markdown文件内容
     * @throws IOException 读取文件异常
     */
    public static String readMarkdownFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath),StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    /**
     * 将markdown文本转换为JSON格式
     * @param markdownContent markdown文本内容
     * @return 转换后的JSON字符串
     */
    public static String markdownToJson(String markdownContent) {
        List<Dialogue> dialogues = parseMarkdownToDialogues(markdownContent);
        return JSON.toJSONString(dialogues);
    }

    /**
     * 解析markdown文本为对话列表
     * @param markdownContent markdown文本内容
     * @return 对话列表
     */
    private static List<Dialogue> parseMarkdownToDialogues(String markdownContent) {
        List<Dialogue> dialogues = new ArrayList<>();
        String[] lines = markdownContent.split("\n");
        
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            
            // 解析角色对话格式：角色名：对话内容
            if (line.contains("：")) {
                int colonIndex = line.indexOf("：");
                if (colonIndex > 0) {
                    String character = line.substring(0, colonIndex).trim();
                    String content = line.substring(colonIndex + 1).trim();
                    if (!character.isEmpty() && !content.isEmpty()) {
                        Dialogue dialogue = new Dialogue();
                        dialogue.setCharacter(character);
                        dialogue.setContent(content);
                        dialogues.add(dialogue);
                    }
                }
            }
        }
        
        return dialogues;
    }

}