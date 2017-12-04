package cn.com.mangopi.android.model.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class MemberWalletBean implements Serializable {

    private static final long serialVersionUID = -5566786568185491921L;
    private long member_id;
    private BigDecimal total_amount;
    private BigDecimal freezing_amount;
    private BigDecimal available_amount;

    public long getMember_id() {
        return member_id;
    }

    public void setMember_id(long member_id) {
        this.member_id = member_id;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public BigDecimal getFreezing_amount() {
        return freezing_amount;
    }

    public void setFreezing_amount(BigDecimal freezing_amount) {
        this.freezing_amount = freezing_amount;
    }

    public BigDecimal getAvailable_amount() {
        return available_amount;
    }

    public void setAvailable_amount(BigDecimal available_amount) {
        this.available_amount = available_amount;
    }
}
