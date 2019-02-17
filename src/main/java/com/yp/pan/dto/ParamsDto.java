package com.yp.pan.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * ParamsDto class
 *
 * @author Administrator
 * @date 2019/02/14
 */
@Data
@NoArgsConstructor
public class ParamsDto {
    private Integer page;
    private Map<String, String> params;
    private String sortKey;
    private String sortValue;
}
