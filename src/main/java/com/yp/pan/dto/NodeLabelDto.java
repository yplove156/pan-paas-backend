package com.yp.pan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeLabelDto {

    private String name;
    private Map<String,String> labels;
}
