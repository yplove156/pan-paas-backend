package com.yp.pan.provider;

import com.yp.pan.model.NoticeInfo;
import org.apache.ibatis.jdbc.SQL;

@SuppressWarnings("Duplicates")
public class NoticeProvider {

    public String addNotice(NoticeInfo noticeInfo) {
        return new SQL() {
            {
                INSERT_INTO("notice_info");
                VALUES("id", "id");
                VALUES("title", "title");
                VALUES("content", "content");
                VALUES("user_id", "userId");
                VALUES("create_time", "createTime");
                VALUES("update_time", "updateTime");
                VALUES("delete_flag", "deleteFlag");
            }
        }.toString();
    }

    public String noticeList(String userId) {
        return new SQL() {
            {
                SELECT("id", "title", "content", "user_id", "create_time");
                FROM("notice_info");
                WHERE("user_id=#{userId}");
                WHERE("delete_flag=0");
                ORDER_BY("create_time desc");
            }
        }.toString() + " limit #{start},#{limit}";
    }

    public String countNotice(String userId) {
        return new SQL() {
            {
                SELECT("count(1)");
                FROM("notice_info");
                WHERE("user_id=#{userId}");
                WHERE("delete_flag=0");
            }
        }.toString();
    }

    public String deleteNotice(String id) {
        return new SQL() {
            {
                UPDATE("notice_info");
                SET("delete_flag=1");
                WHERE("id=#{id}");
            }
        }.toString();
    }
}
