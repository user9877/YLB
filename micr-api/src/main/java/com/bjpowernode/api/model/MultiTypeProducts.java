package com.bjpowernode.api.model;

import com.bjpowernode.api.domain.Product;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName:MultiTypeProducts
 * Package:com.bjpowernode.api.model
 * Date:2022/7/6 19:59
 * Description:
 * Autor:FH
 */

public class MultiTypeProducts implements Serializable {
    private static final long serialVersionUID = -5616982463920146798L;
    private List<Product> xsbList;
    private List<Product> yxList;
    private List<Product> sbList;

    public List<Product> getXsbList() {
        return xsbList;
    }

    public void setXsbList(List<Product> xsbList) {
        this.xsbList = xsbList;
    }

    public List<Product> getYxList() {
        return yxList;
    }

    public void setYxList(List<Product> yxList) {
        this.yxList = yxList;
    }

    public List<Product> getSbList() {
        return sbList;
    }

    public void setSbList(List<Product> sbList) {
        this.sbList = sbList;
    }

    public MultiTypeProducts() {
    }

    public MultiTypeProducts(List<Product> xsbList, List<Product> yxList, List<Product> sbList) {
        this.xsbList = xsbList;
        this.yxList = yxList;
        this.sbList = sbList;
    }
}
