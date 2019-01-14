package com.yp.pan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PwdDto {

    private String pwd;
    private String newPwd;
    private String rePwd;
}
