package com.mango.model.bean;

import java.io.Serializable;

public class RegistBean implements Serializable {
    private String lst_sessid;
    private MemberBean member;

    public String getLst_sessid() {
        return lst_sessid;
    }

    public void setLst_sessid(String lst_sessid) {
        this.lst_sessid = lst_sessid;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }
}
