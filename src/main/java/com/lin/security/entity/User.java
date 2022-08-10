package com.lin.security.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1352098L;

    /**用户id*/
    private Long id;

    /**用户姓名*/
    private String username;

    /**用户昵称*/
    private String nickname;

    /**用户密码*/
    private String password;

    /**账号状态（0-正常，1-停用）*/
    private String status;

    /**用户邮箱*/
    private String email;

    /**用户电话*/
    private String phoneNumber;

    /**用户性别（0-男，1-女，2-未知）*/
    private String sex;

    /**头像*/
    private String avatar;

    /**用户类型（0-管理员，1-普通用户）*/
    private String userType;

    /**创建人用户id*/
    private Long createBy;

    /**创建时间*/
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    /**更新时间*/
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**是否被删除（0-否，1-是）*/
    private Integer delFlag;

}
