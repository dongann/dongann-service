package com.dongann.app.entity.cuser;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * @FileName: Cusers
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2018/6/5 下午11:26
 * @Version: v1.0
 * @description:
 */
public class Cusers implements Serializable {
    private Integer cuserId;       //会员id

    private Integer accountId;     //帐户ID

    private String  token;         //登录token

    private String  cuserName;     //会员名称

    private Integer cuserSex;      //会员性别 0-无 1-男 2-女

    private String  realName;      //真实姓名

    private Date    cuserBirthday; //会员生日

    private String  portrait;      //会员头像

    private String  cuserMobile;   //手机号

    private String  jPushId;       //jpushId

    private String  loginPassword; //会员密码

    private String  payPassword;   //支付密码

    private String  cuserWxOpenId; //微信的openid

    private String  cuserEmail;    //会员邮箱

    private Integer cuserPoints;   //会员积分

    private Integer cuserState;    //会员的开启状态 0 开启 1 关闭

    private String  inviteCode;    //邀请代码 创客邀请码或会员ID

    private String  inviteBy;      //邀请人代码 创客邀请码或会员ID

    private Date    createTime;    //帐号创建时间

    private Date    updateTime;    //更新时间

    public void setCuserId(Integer cuserId) {
        this.cuserId = cuserId;
    }

    public Integer getCuserId() {
        return this.cuserId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getAccountId() {
        return this.accountId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setCuserName(String cuserName) {
        this.cuserName = cuserName;
    }

    public String getCuserName() {
        return this.cuserName;
    }

    public void setCuserSex(Integer cuserSex) {
        this.cuserSex = cuserSex;
    }

    public Integer getCuserSex() {
        return this.cuserSex;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setCuserBirthday(Date cuserBirthday) {
        this.cuserBirthday = cuserBirthday;
    }

    public Date getCuserBirthday() {
        return this.cuserBirthday;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getPortrait() {
        return this.portrait;
    }

    public void setCuserMobile(String cuserMobile) {
        this.cuserMobile = cuserMobile;
    }

    public String getCuserMobile() {
        return this.cuserMobile;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getLoginPassword() {
        return this.loginPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getPayPassword() {
        return this.payPassword;
    }

    public void setCuserWxOpenId(String cuserWxOpenId) {
        this.cuserWxOpenId = cuserWxOpenId;
    }

    public String getCuserWxOpenId() {
        return this.cuserWxOpenId;
    }

    public void setCuserEmail(String cuserEmail) {
        this.cuserEmail = cuserEmail;
    }

    public String getCuserEmail() {
        return this.cuserEmail;
    }

    public void setCuserPoints(Integer cuserPoints) {
        this.cuserPoints = cuserPoints;
    }

    public Integer getCuserPoints() {
        return this.cuserPoints;
    }

    public void setCuserState(Integer cuserState) {
        this.cuserState = cuserState;
    }

    public Integer getCuserState() {
        return this.cuserState;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getInviteCode() {
        return this.inviteCode;
    }

    public void setInviteBy(String inviteBy) {
        this.inviteBy = inviteBy;
    }

    public String getInviteBy() {
        return this.inviteBy;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public String getjPushId() {
        return jPushId;
    }

    public void setjPushId(String jPushId) {
        this.jPushId = jPushId;
    }
}