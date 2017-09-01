package cn.com.mangopi.android.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TrendDetailBean implements Serializable {

    private long id;
    private String publisher_mobile;
    private String publisher_name;
    private String content;
    private List<String> pic_rsurls;
    private Date publish_time;
    private List<Comment> comments;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPublisher_mobile() {
        return publisher_mobile;
    }

    public void setPublisher_mobile(String publisher_mobile) {
        this.publisher_mobile = publisher_mobile;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPic_rsurls() {
        return pic_rsurls;
    }

    public void setPic_rsurls(List<String> pic_rsurls) {
        this.pic_rsurls = pic_rsurls;
    }

    public Date getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(Date publish_time) {
        this.publish_time = publish_time;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static class Comment implements Serializable {
        private String member_name;
        private Date comment_time;
        private String content;
        private String reply;

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public Date getComment_time() {
            return comment_time;
        }

        public void setComment_time(Date comment_time) {
            this.comment_time = comment_time;
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
