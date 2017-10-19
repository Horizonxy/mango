package cn.com.mangopi.android.model.bean;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PayResultBean implements Serializable {

    private BillBean bill;
    private String payData;

    public BillBean getBill() {
        return bill;
    }

    public void setBill(BillBean bill) {
        this.bill = bill;
    }

    public String getPayData() {
        return payData;
    }

    public void setPayData(String payData) {
        this.payData = payData;
    }

    public static class BillBean implements Serializable {
        private float amount;
        private int channelId;
        private Date createTime;
        private List<Integer> entityIds;
        private int entityTypeId;
        private Date expireTime;
        private long id;
        private String name;
        private String payChannel;
        private String payNo;
        private String platform;
        private int state;
        private String transactionContent;
        private Date updateTime;
        private String userAgent;
        private String userIp;
        private int walletId;

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        public int getChannelId() {
            return channelId;
        }

        public void setChannelId(int channelId) {
            this.channelId = channelId;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public List<Integer> getEntityIds() {
            return entityIds;
        }

        public void setEntityIds(List<Integer> entityIds) {
            this.entityIds = entityIds;
        }

        public int getEntityTypeId() {
            return entityTypeId;
        }

        public void setEntityTypeId(int entityTypeId) {
            this.entityTypeId = entityTypeId;
        }

        public Date getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(Date expireTime) {
            this.expireTime = expireTime;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(String payChannel) {
            this.payChannel = payChannel;
        }

        public String getPayNo() {
            return payNo;
        }

        public void setPayNo(String payNo) {
            this.payNo = payNo;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getTransactionContent() {
            return transactionContent;
        }

        public void setTransactionContent(String transactionContent) {
            this.transactionContent = transactionContent;
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }

        public String getUserAgent() {
            return userAgent;
        }

        public void setUserAgent(String userAgent) {
            this.userAgent = userAgent;
        }

        public String getUserIp() {
            return userIp;
        }

        public void setUserIp(String userIp) {
            this.userIp = userIp;
        }

        public int getWalletId() {
            return walletId;
        }

        public void setWalletId(int walletId) {
            this.walletId = walletId;
        }
    }
}
