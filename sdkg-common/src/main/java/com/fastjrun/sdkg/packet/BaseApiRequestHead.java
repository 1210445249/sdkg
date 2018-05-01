package com.fastjrun.sdkg.packet;

import java.io.Serializable;

public class BaseApiRequestHead implements Serializable {
    
    private static final long serialVersionUID = 6691961233600648081L;

    private String accessKey;

    private Long txTime;

    //由accessSn和txTime拼接
    private String md5Hash;

    public String getMd5Hash() {
        return md5Hash;
    }

    public void setMd5Hash(String md5Hash) {
        this.md5Hash = md5Hash;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public Long getTxTime() {
        return txTime;
    }

    public void setTxTime(Long txTime) {
        this.txTime = txTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(("BaseApiRequestHead"+" ["));
        sb.append("accessKey");
        sb.append("=");
        sb.append(this.accessKey);
        sb.append(",");
        sb.append("txTime");
        sb.append("=");
        sb.append(this.txTime);
        sb.append(",");
        sb.append("md5Hash");
        sb.append("=");
        sb.append(this.md5Hash);
        sb.append("]");
        return sb.toString();
    }
}
