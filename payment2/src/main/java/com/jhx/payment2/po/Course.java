package com.jhx.payment2.po;

import java.io.Serializable;

public class Course implements Serializable {
    private String courseId;

    private String title;

    private String deptName;

    private Short credits;

    private static final long serialVersionUID = 1L;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Short getCredits() {
        return credits;
    }

    public void setCredits(Short credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", courseId=").append(courseId);
        sb.append(", title=").append(title);
        sb.append(", deptName=").append(deptName);
        sb.append(", credits=").append(credits);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}