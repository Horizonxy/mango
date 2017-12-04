package cn.com.mangopi.android.model.bean;

import java.io.Serializable;

public class CompanyDetailBean implements Serializable {

    private static final long serialVersionUID = -5391360085842853200L;
    private long id;
    private String company_no;
    private String company_name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompany_no() {
        return company_no;
    }

    public void setCompany_no(String company_no) {
        this.company_no = company_no;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
