package cn.itcast.itcaststore.domain;

import java.io.Serializable;
import java.util.Date;
/*
构造User的javaBean类--根据数据库表中对应的字段来建立
作用：将从页面中获取的数据封装到User对象中
 */
public class User implements Serializable{
    private int id; // 用户编号
    private String username; //用户姓名
    private String password;
    private String gender;
    private String email;
    private String telephone;
    private String introduce;
    private String activeCode; //激活码
    private int state;
    private String role; // 用户角色
    private Date registTime; // 注册时间

    public User(){}

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password="
                + password + ", gender=" + gender + ", email=" + email
                + ", telephone=" + telephone + ", introduce=" + introduce
                + ", role=" + role + "]";
    }


}
