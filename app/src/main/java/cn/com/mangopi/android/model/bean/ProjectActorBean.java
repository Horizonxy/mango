package cn.com.mangopi.android.model.bean;

import java.io.Serializable;
import java.util.List;

public class ProjectActorBean implements Serializable {
    private long id;
    private String project_name;
    private long project_id;
    private String actor_type;
    private String actor_name;
    private long actor_id;
    private int member_count;
    private int vote_count;
    private float tutor_score;
    private float company_score;
    private float integrated_score;
    private int integrated_ranking;
    private String awards;
    private String tutor_comments;
    private String company_comments;
    private List<String> works_photo_rsurls;
    private List<String> scheme_doc_rsurls;
    private List<String> team_photo_rsurls;
    private Integer state;
    private String state_label;
    private ActorTeamBean team;

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

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public String getActor_type() {
        return actor_type;
    }

    public void setActor_type(String actor_type) {
        this.actor_type = actor_type;
    }

    public String getActor_name() {
        return actor_name;
    }

    public void setActor_name(String actor_name) {
        this.actor_name = actor_name;
    }

    public long getActor_id() {
        return actor_id;
    }

    public void setActor_id(long actor_id) {
        this.actor_id = actor_id;
    }

    public int getMember_count() {
        return member_count;
    }

    public void setMember_count(int member_count) {
        this.member_count = member_count;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public float getTutor_score() {
        return tutor_score;
    }

    public void setTutor_score(float tutor_score) {
        this.tutor_score = tutor_score;
    }

    public float getCompany_score() {
        return company_score;
    }

    public void setCompany_score(float company_score) {
        this.company_score = company_score;
    }

    public float getIntegrated_score() {
        return integrated_score;
    }

    public void setIntegrated_score(float integrated_score) {
        this.integrated_score = integrated_score;
    }

    public int getIntegrated_ranking() {
        return integrated_ranking;
    }

    public void setIntegrated_ranking(int integrated_ranking) {
        this.integrated_ranking = integrated_ranking;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getTutor_comments() {
        return tutor_comments;
    }

    public void setTutor_comments(String tutor_comments) {
        this.tutor_comments = tutor_comments;
    }

    public String getCompany_comments() {
        return company_comments;
    }

    public void setCompany_comments(String company_comments) {
        this.company_comments = company_comments;
    }

    public List<String> getWorks_photo_rsurls() {
        return works_photo_rsurls;
    }

    public void setWorks_photo_rsurls(List<String> works_photo_rsurls) {
        this.works_photo_rsurls = works_photo_rsurls;
    }

    public List<String> getScheme_doc_rsurls() {
        return scheme_doc_rsurls;
    }

    public void setScheme_doc_rsurls(List<String> scheme_doc_rsurls) {
        this.scheme_doc_rsurls = scheme_doc_rsurls;
    }

    public List<String> getTeam_photo_rsurls() {
        return team_photo_rsurls;
    }

    public void setTeam_photo_rsurls(List<String> team_photo_rsurls) {
        this.team_photo_rsurls = team_photo_rsurls;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getState_label() {
        return state_label;
    }

    public void setState_label(String state_label) {
        this.state_label = state_label;
    }

    public ActorTeamBean getTeam() {
        return team;
    }

    public void setTeam(ActorTeamBean team) {
        this.team = team;
    }
}
