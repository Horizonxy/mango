package cn.com.mangopi.android.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TrendDetailBean implements Serializable {

    private static final long serialVersionUID = -3385738536378682281L;
    private long id;
    private long publisher_id;
    private String publisher_mobile;
    private String publisher_name;
    private String content;
    private List<String> pic_rsurls;
    private String avatar_rsurl;
    private Date publish_time;
    private String publish_time_label;
    private String city;
    private int comment_count;
    private int praise_count;
    private int faword_count;
    private List<Comment> comments;
    private TrendBean forward_trend;

    public TrendBean getForward_trend() {
        return forward_trend;
    }

    public void setForward_trend(TrendBean forward_trend) {
        this.forward_trend = forward_trend;
    }

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

    public long getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(long publisher_id) {
        this.publisher_id = publisher_id;
    }

    public String getAvatar_rsurl() {
        return avatar_rsurl;
    }

    public void setAvatar_rsurl(String avatar_rsurl) {
        this.avatar_rsurl = avatar_rsurl;
    }

    public String getPublish_time_label() {
        return publish_time_label;
    }

    public void setPublish_time_label(String publish_time_label) {
        this.publish_time_label = publish_time_label;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getPraise_count() {
        return praise_count;
    }

    public void setPraise_count(int praise_count) {
        this.praise_count = praise_count;
    }

    public int getFaword_count() {
        return faword_count;
    }

    public void setFaword_count(int faword_count) {
        this.faword_count = faword_count;
    }

    public static class Comment implements Serializable {
        private static final long serialVersionUID = 6600125626858211365L;
        private String member_name;
        private Date comment_time;
        private String content;
        private String reply;
        private long id;
        private String avatar_rsurl;
        private String publish_time_label;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getAvatar_rsurl() {
            return avatar_rsurl;
        }

        public void setAvatar_rsurl(String avatar_rsurl) {
            this.avatar_rsurl = avatar_rsurl;
        }

        public String getPublish_time_label() {
            return publish_time_label;
        }

        public void setPublish_time_label(String publish_time_label) {
            this.publish_time_label = publish_time_label;
        }

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
