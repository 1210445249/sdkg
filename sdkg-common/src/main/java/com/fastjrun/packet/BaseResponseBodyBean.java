package com.fastjrun.packet;

import com.fastjrun.common.AbstractEntity;

public class BaseResponseBodyBean extends BaseResponseBody {

    private AbstractEntity abstractEntity;

    public AbstractEntity getAbstractEntity() {
        return abstractEntity;
    }

    public void setAbstractEntity(AbstractEntity abstractEntity) {
        this.abstractEntity = abstractEntity;
    }
}
