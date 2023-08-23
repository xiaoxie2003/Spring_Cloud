package com.yc.resfood.model;

import com.yc.ResFood;

import java.io.Serializable;

public class CartItem implements Serializable {
    private ResFood food;
    private Integer num;
    public Double smallCount;

    public ResFood getFood() {
        return food;
    }

    public void setFood(ResFood food) {
        this.food = food;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getSmallCount() {
        if(food != null){
            smallCount = this.food.getRealprice() * this.num;
        }
        return smallCount;
    }

    public void setSmallCount(Double smallCount) {
        this.smallCount = smallCount;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "food=" + food +
                ", num=" + num +
                ", smallCount=" + smallCount +
                '}';
    }
}
