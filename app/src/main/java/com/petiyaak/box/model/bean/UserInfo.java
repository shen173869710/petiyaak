package com.petiyaak.box.model.bean;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private int id;
    // '登录名'
    private String username;
    // 登录名
    private String nickname;
    // 邮件地址
    private String email;
    // 登录密码
    private String password;
    // 手机号码
    private String phonenumber;
    // 性别:1=男,2=女
    private int sex;
    // 状态1=正常,2=停用
    private int status;
    // 头像地址
    private String avatar;
    // 头像id
    private int avatarId;
    private long in_time;
    private int role_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getIn_time() {
        return in_time;
    }

    public void setIn_time(long in_time) {
        this.in_time = in_time;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getAvatarid() {
        if (avatarId < 1 || avatarId > 10) {
            return 1;
        }
        return avatarId;
    }

    public void setAvatarid(int avatarid) {
        this.avatarId = avatarid;
    }
}
