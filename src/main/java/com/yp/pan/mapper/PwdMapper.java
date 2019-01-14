package com.yp.pan.mapper;

import com.yp.pan.model.PwdInfo;
import com.yp.pan.provider.PwdProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.Map;

/**
 * PwdMapper class
 *
 * @author Administrator
 * @date 2018/11/17
 */
public interface PwdMapper {

    @SelectProvider(type = PwdProvider.class, method = "findByUsername")
    @Results({
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "delete_flag", property = "deleteFlag")
    })
    PwdInfo findByUsername(String username);

    @InsertProvider(type = PwdProvider.class, method = "addPwd")
    int addPwd(PwdInfo pwdInfo);

    @UpdateProvider(type = PwdProvider.class, method = "updatePwd")
    int updatePwd(Map<String, String> params);
}
