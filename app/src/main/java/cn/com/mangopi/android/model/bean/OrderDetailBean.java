package cn.com.mangopi.android.model.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDetailBean implements Serializable {

    private static final long serialVersionUID = 3533561276957892644L;
    private long id;
    private String order_no;
    private long course_id;
    private String order_name;
    private int order_count;
    private Date order_time;
    private List<String> pay_channels;
    private long member_id;
    private String member_name;
    private String member_mobile;
    private long tutor_id;
    private String tutor_name;
    private BigDecimal pay_price;
    private BigDecimal sale_price;
    private BigDecimal total_price;
    private BigDecimal discount_price;
    private String promotion_code;
    private Integer state;
    private String state_label;
    private String order_message;
    private String order_memos;
    private String after_sale_info;
    private List<String> material_rsurls;
    private List<Comment> comments;
    private int pay_state;
    private String pay_state_label;

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

    public String getMember_mobile() {
        return member_mobile;
    }

    public void setMember_mobile(String member_mobile) {
        this.member_mobile = member_mobile;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getOrder_message() {
        return order_message;
    }

    public void setOrder_message(String order_message) {
        this.order_message = order_message;
    }

    public String getOrder_memos() {
        return order_memos;
    }

    public void setOrder_memos(String order_memos) {
        this.order_memos = order_memos;
    }

    public String getAfter_sale_info() {
        return after_sale_info;
    }

    public void setAfter_sale_info(String after_sale_info) {
        this.after_sale_info = after_sale_info;
    }

    public List<String> getMaterial_rsurls() {
        return material_rsurls;
    }

    public void setMaterial_rsurls(List<String> material_rsurls) {
        this.material_rsurls = material_rsurls;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    public List<String> getPay_channels() {
        return pay_channels;
    }

    public void setPay_channels(List<String> pay_channels) {
        this.pay_channels = pay_channels;
    }

    public String getState_label() {
        return state_label;
    }

    public void setState_label(String state_label) {
        this.state_label = state_label;
    }

    public int getPay_state() {
        return pay_state;
    }

    public void setPay_state(int pay_state) {
        this.pay_state = pay_state;
    }

    public String getPay_state_label() {
        return pay_state_label;
    }

    public void setPay_state_label(String pay_state_label) {
        this.pay_state_label = pay_state_label;
    }

    public static class Comment implements Serializable {
        private static final long serialVersionUID = 2827934095569867548L;
        private long id;
        private long course_id;
        private long member_id;
        private String member_name;
        private String content;
        private String reply;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getCourse_id() {
            return course_id;
        }

        public void setCourse_id(long course_id) {
            this.course_id = course_id;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }
    }
}
