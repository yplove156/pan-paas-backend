package com.yp.pan.mapper;

import com.yp.pan.model.UserInfo;
import com.yp.pan.provider.UserProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * UserMapper class
 *
 * @author Administrator
 * @date 2018/11/17
 */
public interface UserMapper {
    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "findByUsername")
    @Results({
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "delete_flag", property = "deleteFlag")
    })
    UserInfo findByUsername(String username);

    @SelectProvider(type = UserProvider.class, method = "userList")
    @Results({
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "delete_flag", property = "deleteFlag")
    })
    List<UserInfo> userList();

    @InsertProvider(type = UserProvider.class, method = "addUser")
    int addUser(UserInfo userInfo);
}
