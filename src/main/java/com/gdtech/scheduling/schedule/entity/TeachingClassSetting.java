package com.gdtech.scheduling.schedule.entity;

import com.gdtech.core.base.entity.BaseEntity;

/**
 * 教学班分班配置
 * @author zhucy
 */
public class TeachingClassSetting extends BaseEntity {
    //选课活动id
    private String actId;
    //名称
    private String name;
    //班级容量
    private Integer quantity;
    //偏差值
    private Integer deviation;

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDeviation() {
        return deviation;
    }

    public void setDeviation(Integer deviation) {
        this.deviation = deviation;
    }
    
    public Integer getMaxQuantity() {
        return quantity + deviation;
    }
    
    public Integer getMinQuantity() {
        return quantity - deviation;
    }
}
