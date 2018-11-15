package com.yp.pan.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoginDto class
 *
 * @author Administrator
 * @date 2018/11/15
 */
@Data
@NoArgsConstructor
public class LoginDto {
    private String username;
    private String password;
}
