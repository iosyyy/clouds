package com.jhx.payment.po;

import java.io.Serializable;

public class MyTb implements Serializable {
    private Integer mId;

    private String mName;

    private String mSex;

    private Integer mAns;

    private static final long serialVersionUID = 1L;

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmSex() {
        return mSex;
    }

    public void setmSex(String mSex) {
        this.mSex = mSex;
    }

    public Integer getmAns() {
        return mAns;
    }

    public void setmAns(Integer mAns) {
        this.mAns = mAns;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", mId=").append(mId);
        sb.append(", mName=").append(mName);
        sb.append(", mSex=").append(mSex);
        sb.append(", mAns=").append(mAns);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}