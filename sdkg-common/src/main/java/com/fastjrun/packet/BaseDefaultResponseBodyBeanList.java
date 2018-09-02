package com.fastjrun.packet;

import java.util.List;

import com.fastjrun.entity.BaseEntity;

public class BaseDefaultResponseBodyBeanList<T extends BaseEntity> extends BaseBody {

    private int totalCount;

    private List<T> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
