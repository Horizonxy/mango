package cn.com.mangopi.android.model.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderBean implements Serializable {

    private static final long serialVersionUID = -997391003365078711L;
    private long id;
    private String order_no;
    private long course_id;
    private String order_name;
    private int order_count;
    private long member_id;
    private String member_name;
    private String member_mobile;
    private String course_name;
    private long tutor_id;
    private String tutor_name;
    private BigDecimal pay_price;
    private BigDecimal sale_price;
    private BigDecimal total_price;
    private BigDecimal discount_price;
    private String promotion_code;
    private Integer state;
    private String state_label;
    private Integer pay_state;
    private String pay_state_label;
    private Date order_time;
    private String pay_channel;
    private Date sct_date;
    private Integer sct_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFiveLenOrderNo(){
        if(order_no != null){
            return order_no.length() > 5 ? order_no.substring(order_no.length() - 5) : order_no;
        }
        return "";
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(long course_id) {
        this.course_id = course_id;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public int getOrder_count() {
        return order_count;
    }

    public void setOrder_count(int order_count) {
        this.order_count = order_count;
    }

    public long getMember_id() {
        return member_id;
    }

    public void setMember_id(long member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public long getTutor_id() {
        return tutor_id;
    }

    public void setTutor_id(long tutor_id) {
        this.tutor_id = tutor_id;
    }

    public String getTutor_name() {
        return tutor_name;
    }

    public void setTutor_name(String tutor_name) {
        this.tutor_name = tutor_name;
    }

    public BigDecimal getPay_price() {
        return pay_price;
    }

    public void setPay_price(BigDecimal pay_price) {
        this.pay_price = pay_price;
    }

    public BigDecimal getSale_price() {
        return sale_price;
    }

    public void setSale_price(BigDecimal sale_price) {
        this.sale_price = sale_price;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getState_label() {
        return state_label;
    }

    public void setState_label(String state_label) {
        this.state_label = state_label;
    }

    public Integer getPay_state() {
        return pay_state;
    }

    public void setPay_state(Integer pay_state) {
        this.pay_state = pay_state;
    }

    public String getPay_state_label() {
        return pay_state_label;
    }

    public void setPay_state_label(String pay_state_label) {
        this.pay_state_label = pay_state_label;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    public String getPay_channel() {
        return pay_channel;
    }

    public void setPay_channel(String pay_channel) {
        this.pay_channel = pay_channel;
    }

    public Date getSct_date() {
        return sct_date;
    }

    public void setSct_date(Date sct_date) {
        this.sct_date = sct_date;
    }

    public Integer getSct_time() {
        return sct_time;
    }

    public void setSct_time(Integer sct_time) {
        this.sct_time = sct_time;
    }

    public String getMember_mobile() {
        return member_mobile;
    }

    public void setMember_mobile(String member_mobile) {
        this.member_mobile = member_mobile;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public BigDecimal getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(BigDecimal discount_price) {
        this.discount_price = discount_price;
    }

    public String getPromotion_code() {
        return promotion_code;
    }

    public void setPromotion_code(String promotion_code) {
        this.promotion_code = promotion_code;
    }
}
