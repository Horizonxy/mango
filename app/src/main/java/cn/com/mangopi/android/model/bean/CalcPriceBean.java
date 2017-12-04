package cn.com.mangopi.android.model.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class CalcPriceBean implements Serializable {

    private static final long serialVersionUID = -5103592222864106101L;
    private long id;
    private BigDecimal total_price;
    private BigDecimal pay_price;
    private BigDecimal discount_price;
    private String discount_price_label;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public BigDecimal getPay_price() {
        return pay_price;
    }

    public void setPay_price(BigDecimal pay_price) {
        this.pay_price = pay_price;
    }

    public BigDecimal getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(BigDecimal discount_price) {
        this.discount_price = discount_price;
    }

    public String getDiscount_price_label() {
        return discount_price_label;
    }

    public void setDiscount_price_label(String discount_price_label) {
        this.discount_price_label = discount_price_label;
    }
}
