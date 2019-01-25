package com.yp.pan.provider;

import com.yp.pan.model.UserNoticeInfo;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@SuppressWarnings("Duplicates")
public class UserNoticeProvider {

    public String userNoticeList() {
        return new SQL() {
            {
                SELECT("id", "title", "content",
                        "sender", "receiver", "status", "notice_id", "create_time");
                FROM("user_notice_info");
                WHERE("receiver=#{userId}");
                WHERE("delete_flag=0");
                ORDER_BY("create_time desc");
            }
        }.toString() + " limit #{start},#{limit}";
    }

    public String userNotReadNoticeList() {
        return new SQL() {
            {
                SELECT("id", "title", "content",
                        "sender", "receiver", "status", "notice_id", "create_time");
                FROM("user_notice_info");
                WHERE("receiver=#{userId}");
                WHERE("status=0");
                WHERE("delete_flag=0");
                ORDER_BY("create_time desc");
            }
        }.toString() + " limit 10";
    }

    public String countUserNotice(String userId) {
        return new SQL() {
            {
                SELECT("count(1)");
                FROM("user_notice_info");
                WHERE("receiver=#{userId}");
                WHERE("delete_flag=0");
            }
        }.toString();
    }

    public String addUserNotice(List<UserNoticeInfo> userNoticeInfos) {
        StringBuffer buffer = new StringBuffer();
        userNoticeInfos.forEach(userNoticeInfo -> {
            String sql = new SQL() {
                {
                    INSERT_INTO("user_notice_info");
                    VALUES("id", "#{id}");
                    VALUES("title", "#{title}");
                    VALUES("content", "#{content}");
                    VALUES("sender", "#{sender}");
                    VALUES("receiver", "#{receiver}");
                    VALUES("status", "#{status}");
                    VALUES("notice_id", "#{noticeId}");
                    VALUES("create_time", "#{createTime}");
                    VALUES("update_time", "#{updateTime}");
                    VALUES("delete_flag", "#{deleteFlag}");
                }
            }.toString();
            buffer.append(sql);
        });
        return buffer.toString();
    }

    public String deleteUserNotice(String id) {
        return new SQL() {
            {
                UPDATE("user_notice_info");
                SET("delete_flag=1");
                WHERE("id=#{id}");
            }
        }.toString();
    }

    public String deleteByNoticeId(List<String> ids) {
        StringBuffer buffer = new StringBuffer();
        ids.forEach((id) -> {
            String sql = new SQL() {
                {
                    UPDATE("user_notice_info");
                    SET("delete_flag=1");
                    WHERE("notice_id=#{id}");
                }
            }.toString();
            buffer.append(sql);
        });
        return buffer.toString();
    }
}
