package com.vnmaker.entity;

import lombok.Data;

@Data
public class PreConditions<T> {
    private T conditionValue; // 条件值
    private String conditionType; // 条件类型
    private boolean isMet; // 条件是否满足

    public PreConditions(T conditionValue) {
        this.conditionValue = conditionValue;
    }

    public boolean checkCondition() {
        // 具体逻辑根据业务需求实现
        return isMet;
    }
}
