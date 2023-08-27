package com.yc.bean2;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Apple {
    private String name;
    private double price;
}
