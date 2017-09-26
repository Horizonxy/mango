package cn.com.mangopi.android.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TrendBean implements Serializable {

    private long id;
    private Long publisher_id;
    private String publisher_mobile;
    private String publisher_name;
    private String content;
    private List<String> pic_rsurls;
    private Date publish_time;
    private String publish_time_label;
    private String avatar_rsurl;
    private String city;
    private int comment_count;
    private int reward_count;
    private int want_count;
    private int praise_count;
    private int faword_count;
    private boolean is_favor;
    private TrendBean fawordTrend;

    public TrendBean getFawordTrend() {
        return fawordTrend;
    }

    public void setFawordTrend(TrendBean fawordTrend) {
        this.fawordTrend = fawordTrend;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(Long publisher_id) {
        this.publisher_id = publisher_id;
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

    public String getPublish_time_label() {
        return publish_time_label;
    }

    public void setPublish_time_label(String publish_time_label) {
        this.publish_time_label = publish_time_label;
    }

    public String getAvatar_rsurl() {
        return avatar_rsurl;
    }

    public void setAvatar_rsurl(String avatar_rsurl) {
        this.avatar_rsurl = avatar_rsurl;
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

    public int getReward_count() {
        return reward_count;
    }

    public void setReward_count(int reward_count) {
        this.reward_count = reward_count;
    }

    public int getWant_count() {
        return want_count;
    }

    public void setWant_count(int want_count) {
        this.want_count = want_count;
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

    public boolean is_favor() {
        return is_favor;
    }

    public void setIs_favor(boolean is_favor) {
        this.is_favor = is_favor;
    }
}
