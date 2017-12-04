package cn.com.mangopi.android.model.bean;

import java.io.Serializable;

public class ProjectJoinBean implements Serializable {

    private static final long serialVersionUID = -6086943473522744203L;
    private long actor_id;

    public long getActor_id() {
        return actor_id;
    }

    public void setActor_id(long actor_id) {
        this.actor_id = actor_id;
    }
}
