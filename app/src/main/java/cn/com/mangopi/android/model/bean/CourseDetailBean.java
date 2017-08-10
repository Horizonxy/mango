package cn.com.mangopi.android.model.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CourseDetailBean implements Serializable {

    private long id;
    private String course_title;
    private String logo_rsurl;
    private String city;
    private String course_content;
    private String service_time;
    private String each_time;
    private Integer state;
    private String state_label;
    private String type_name;
    private BigDecimal sale_price;
    private String type_explains;
    private int type_id;
    private String type_method;
    private String avatar_rsurl;
    private String member_name;
    private long member_id;
    private List<String> material_rsurls;
    private boolean is_favor;
    private Integer approve_state;
    private String approve_state_label;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getLogo_rsurl() {
        return logo_rsurl;
    }

    public void setLogo_rsurl(String logo_rsurl) {
        this.logo_rsurl = logo_rsurl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCourse_content() {
        return course_content;
    }

    public void setCourse_content(String course_content) {
        this.course_content = course_content;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    public String getEach_time() {
        return each_time;
    }

    public void setEach_time(String each_time) {
        this.each_time = each_time;
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

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public BigDecimal getSale_price() {
        return sale_price;
    }

    public void setSale_price(BigDecimal sale_price) {
        this.sale_price = sale_price;
    }

    public String getType_method() {
        return type_method;
    }

    public void setType_method(String type_method) {
        this.type_method = type_method;
    }

    public String getAvatar_rsurl() {
        return avatar_rsurl;
    }

    public void setAvatar_rsurl(String avatar_rsurl) {
        this.avatar_rsurl = avatar_rsurl;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public long getMember_id() {
        return member_id;
    }

    public void setMember_id(long member_id) {
        this.member_id = member_id;
    }

    public List<String> getMaterial_rsurls() {
        return material_rsurls;
    }

    public void setMaterial_rsurls(List<String> material_rsurls) {
        this.material_rsurls = material_rsurls;
    }

    public boolean is_favor() {
        return is_favor;
    }

    public void setIs_favor(boolean is_favor) {
        this.is_favor = is_favor;
    }

    public String getType_explains() {
        return type_explains;
    }

    public void setType_explains(String type_explains) {
        this.type_explains = type_explains;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public Integer getApprove_state() {
        return approve_state;
    }

    public void setApprove_state(Integer approve_state) {
        this.approve_state = approve_state;
    }

    public String getApprove_state_label() {
        return approve_state_label;
    }

    public void setApprove_state_label(String approve_state_label) {
        this.approve_state_label = approve_state_label;
    }
}
