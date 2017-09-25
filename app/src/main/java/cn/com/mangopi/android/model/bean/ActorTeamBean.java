package cn.com.mangopi.android.model.bean;

import java.io.Serializable;
import java.util.List;

public class ActorTeamBean implements Serializable {

    private long id;
    private String team_name;
    private String bulletin;
    private int member_count;
    private String actor_name;
    private String cipher;
    private List<TeamMember> members;

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

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public List<TeamMember> getMembers() {
        return members;
    }

    public void setMembers(List<TeamMember> members) {
        this.members = members;
    }

    public static class TeamMember implements Serializable {
        private long team_id;
        private long member_id;
        private String nick_name;
        private String role_name;
        private String avatar_rsurl;

        public long getMember_id() {
            return member_id;
        }

        public void setMember_id(long member_id) {
            this.member_id = member_id;
        }

        public long getTeam_id() {
            return team_id;
        }

        public void setTeam_id(long team_id) {
            this.team_id = team_id;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }

        public String getAvatar_rsurl() {
            return avatar_rsurl;
        }

        public void setAvatar_rsurl(String avatar_rsurl) {
            this.avatar_rsurl = avatar_rsurl;
        }
    }

}
