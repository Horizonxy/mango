package cn.com.mangopi.android.model.bean;

import java.io.Serializable;
import java.util.Date;

public class ReplyTrendBean implements Serializable {
    private String content;
    private Date createTime;
    private long id;
    private long memberId;
    private String memberName;
    private String reply;
    private int state;
    private long trendId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getTrendId() {
        return trendId;
    }

    public void setTrendId(long trendId) {
        this.trendId = trendId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
