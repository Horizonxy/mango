package cn.com.mangopi.android.model.bean;

import java.io.Serializable;

public class MemberCardBean implements Serializable {

    private static final long serialVersionUID = -5451590357599291551L;
    private long id;
    private long member_id;
    private String bank_name;
    private String card_no;
    private Integer state;
    private String state_label;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMember_id() {
        return member_id;
    }

    public void setMember_id(long member_id) {
        this.member_id = member_id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getDealCardNo(){
        if(card_no != null && card_no.length() > 4){
            String prefix = card_no.substring(0, card_no.length() - 4);
            return prefix.replaceAll("\\d", "*").concat(card_no.substring(card_no.length() - 4));
        }
        return  "";
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
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
}
