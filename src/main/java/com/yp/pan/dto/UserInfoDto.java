package com.yp.pan.dto;

import com.yp.pan.model.UserInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserInfoDto class
 *
 * @author Administrator
 * @date 2018/11/15
 */
@Data
@NoArgsConstructor
public class UserInfoDto {
    private String username;
    private String nick;
    private String photo;
    private String role;
    private String token;
}
