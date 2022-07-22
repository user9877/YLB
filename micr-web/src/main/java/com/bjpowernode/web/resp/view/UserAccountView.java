package com.bjpowernode.web.resp.view;

import com.bjpowernode.api.model.UserAccountModel;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;

/**
 * ClassName:UserAccountView
 * Package:com.bjpowernode.web.resp.view
 * Date:2022/7/22 15:39
 * Description:
 * Autor:FH
 */

public class UserAccountView {
    private Integer uid;
    private String name;
    private String phone;
    private String loginTime;
    private String headerImage;
    private BigDecimal accountMoney;

    public UserAccountView(UserAccountModel userAccountModel) {
        this.uid = userAccountModel.getId();
        this.name = userAccountModel.getName();
        this.phone = userAccountModel.getPhone();
        this.headerImage = userAccountModel.getHeaderImage();
        this.accountMoney = userAccountModel.getAvailableMoney();
        if(userAccountModel.getLastLoginTime() != null){
            try {
                this.loginTime = DateFormatUtils.format(userAccountModel.getLastLoginTime(),"yyyy-MM-dd HH:mm:ss");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public UserAccountView() {
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public BigDecimal getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(BigDecimal accountMoney) {
        this.accountMoney = accountMoney;
    }
}
