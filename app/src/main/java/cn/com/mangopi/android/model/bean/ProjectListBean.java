package cn.com.mangopi.android.model.bean;

import java.io.Serializable;
import java.util.Date;

public class ProjectListBean implements Serializable {

    private long id;
    private String project_name;
    private String logo_rsurl;
    private Date publish_time;
    private Date apply_abort_time;
    private Date works_abort_time;
    private Date appraise_abort_time;
    private int reach_apply_count;
    private int applied_count;
    private int focus_count;
    private int want_count;
    private String banner_rsurls;
    private String introduction;
    private String location;
    private String prizes_content;
    private String entry_instructions;
    private String evaluation_standard;
    private int apply_max_limit;
    private int state;
    private String state_label;
    private int progress;
    private long actor_id;
    private String actor_name;
    /**
     * actor_member_type为team的时候actor_member_id就是团队ID
     */
    private long actor_member_id;
    private String actor_member_type;
    private Date member_join_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getLogo_rsurl() {
        return logo_rsurl;
    }

    public void setLogo_rsurl(String logo_rsurl) {
        this.logo_rsurl = logo_rsurl;
    }

    public Date getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(Date publish_time) {
        this.publish_time = publish_time;
    }

    public Date getApply_abort_time() {
        return apply_abort_time;
    }

    public void setApply_abort_time(Date apply_abort_time) {
        this.apply_abort_time = apply_abort_time;
    }

    public Date getWorks_abort_time() {
        return works_abort_time;
    }

    public void setWorks_abort_time(Date works_abort_time) {
        this.works_abort_time = works_abort_time;
    }

    public Date getAppraise_abort_time() {
        return appraise_abort_time;
    }

    public void setAppraise_abort_time(Date appraise_abort_time) {
        this.appraise_abort_time = appraise_abort_time;
    }

    public int getReach_apply_count() {
        return reach_apply_count;
    }

    public void setReach_apply_count(int reach_apply_count) {
        this.reach_apply_count = reach_apply_count;
    }

    public int getApplied_count() {
        return applied_count;
    }

    public void setApplied_count(int applied_count) {
        this.applied_count = applied_count;
    }

    public int getFocus_count() {
        return focus_count;
    }

    public void setFocus_count(int focus_count) {
        this.focus_count = focus_count;
    }

    public int getWant_count() {
        return want_count;
    }

    public void setWant_count(int want_count) {
        this.want_count = want_count;
    }

    public String getBanner_rsurls() {
        return banner_rsurls;
    }

    public void setBanner_rsurls(String banner_rsurls) {
        this.banner_rsurls = banner_rsurls;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrizes_content() {
        return prizes_content;
    }

    public void setPrizes_content(String prizes_content) {
        this.prizes_content = prizes_content;
    }

    public String getEntry_instructions() {
        return entry_instructions;
    }

    public void setEntry_instructions(String entry_instructions) {
        this.entry_instructions = entry_instructions;
    }

    public String getEvaluation_standard() {
        return evaluation_standard;
    }

    public void setEvaluation_standard(String evaluation_standard) {
        this.evaluation_standard = evaluation_standard;
    }

    public int getApply_max_limit() {
        return apply_max_limit;
    }

    public void setApply_max_limit(int apply_max_limit) {
        this.apply_max_limit = apply_max_limit;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getState_label() {
        return state_label;
    }

    public void setState_label(String state_label) {
        this.state_label = state_label;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getActor_id() {
        return actor_id;
    }

    public void setActor_id(long actor_id) {
        this.actor_id = actor_id;
    }

    public String getActor_name() {
        return actor_name;
    }

    public void setActor_name(String actor_name) {
        this.actor_name = actor_name;
    }

    public long getActor_member_id() {
        return actor_member_id;
    }

    public void setActor_member_id(long actor_member_id) {
        this.actor_member_id = actor_member_id;
    }

    public String getActor_member_type() {
        return actor_member_type;
    }

    public void setActor_member_type(String actor_member_type) {
        this.actor_member_type = actor_member_type;
    }

    public Date getMember_join_time() {
        return member_join_time;
    }

    public void setMember_join_time(Date member_join_time) {
        this.member_join_time = member_join_time;
    }
}
