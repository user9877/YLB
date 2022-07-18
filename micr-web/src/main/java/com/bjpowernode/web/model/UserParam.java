package com.bjpowernode.web.model;

import com.bjpowernode.util.AppUtil;

/**
 * ClassName:UserParam
 * Package:com.bjpowernode.web.model
 * Date:2022/7/18 14:56
 * Description:
 * Autor:FH
 */

public class UserParam {
    private String phone;
    private String loginPassword;
    private String verificationCode;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public boolean checkData(){
        boolean flag = false;
        if(AppUtil.checkPhone(this.phone) &&
                (this.loginPassword != null && this.loginPassword.length() == 32) &&
                (this.verificationCode != null && this.verificationCode.length() ==6 )){
            flag = true;
        }
        return flag;
    }
}
