package cn.com.mangopi.android.model.bean;

import java.io.Serializable;

public class ProjectTeamBean implements Serializable {

    private long id;
    private String team_name;
    private String bulletin;
    private int member_count;
    private String actor_name;
    private String role_name;
    private int actor_state;
    private String actor_state_label;
    private String avatar_rsurl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getBulletin() {
        return bulletin;
    }

    public void setBulletin(String bulletin) {
        this.bulletin = bulletin;
    }

    public int getMember_count() {
        return member_count;
    }

    public void setMember_count(int member_count) {
        this.member_count = member_count;
    }

    public String getActor_name() {
        return actor_name;
    }

    public void setActor_name(String actor_name) {
        this.actor_name = actor_name;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public int getActor_state() {
        return actor_state;
    }

    public void setActor_state(int actor_state) {
        this.actor_state = actor_state;
    }

    public String getActor_state_label() {
        return actor_state_label;
    }

    public void setActor_state_label(String actor_state_label) {
        this.actor_state_label = actor_state_label;
    }

    public String getAvatar_rsurl() {
        return avatar_rsurl;
    }

    public void setAvatar_rsurl(String avatar_rsurl) {
        this.avatar_rsurl = avatar_rsurl;
    }
}
