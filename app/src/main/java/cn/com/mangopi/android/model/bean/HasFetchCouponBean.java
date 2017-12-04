package cn.com.mangopi.android.model.bean;

import java.io.Serializable;
import java.util.List;

public class HasFetchCouponBean implements Serializable {

    private static final long serialVersionUID = -1083801668052946476L;
    private List<String> couponIds;

    public List<String> getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(List<String> couponIds) {
        this.couponIds = couponIds;
    }
}
