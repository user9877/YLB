package com.bjpowernode.web.model;

import com.bjpowernode.util.AppUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * ClassName:RealNameVO
 * Package:com.bjpowernode.web.model
 * Date:2022/7/21 15:47
 * Description:
 * Autor:FH
 */

public class RealNameVO {
    private String name;
    private String idCard;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean checkData(){
        if(!AppUtil.checkPhone(this.phone)){
            return false;
        }else if(StringUtils.isBlank(this.name) || this.name.length()<2){
            return false;
        }else if(StringUtils.isBlank(idCard) || this.idCard.length()<15 ){
            return false;
        }
        return true;
    }
}
