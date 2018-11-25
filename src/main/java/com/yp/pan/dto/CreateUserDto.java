package com.yp.pan.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CreateUserDto class
 *
 * @author Administrator
 * @date 2018/11/25
 */
@Data
@NoArgsConstructor
public class CreateUserDto {
    private String username;
    private String password;
    private String nick;
    private String role;
    private String photo;
    private String phone;
    private String name;
    private String position;
}
