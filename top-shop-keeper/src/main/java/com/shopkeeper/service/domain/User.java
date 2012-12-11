package com.shopkeeper.service.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-7
 * Time: 上午10:13
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "user")
public class User
{
    @XmlElement(name = "user_id")
    private Long userId;

    @XmlElement(name = "nick")
    private String nick;

    @XmlElement(name = "last_login")
    private String lastLogin;

    @XmlElement(name = "score")
    private Integer score;

	@XmlElement(name = "is_init")
	private Boolean isInit;

    @XmlElement(name = "is_shared_weibo")
    private Boolean isSharedWeibo;

    @XmlElement(name = "is_shared_tweibo")
    private Boolean isSharedTweibo;

    @XmlElement(name = "is_shared_renren")
    private Boolean isSharedRenren;

    @XmlElement(name = "is_start_mission")
    private Boolean isStartMission;

    @XmlElement(name = "num_feedback")
    private Boolean numFeedback;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getSharedWeibo() {
        return isSharedWeibo;
    }

    public void setSharedWeibo(Boolean sharedWeibo) {
        isSharedWeibo = sharedWeibo;
    }

    public Boolean getSharedTweibo() {
        return isSharedTweibo;
    }

    public void setSharedTweibo(Boolean sharedTweibo) {
        isSharedTweibo = sharedTweibo;
    }

    public Boolean getSharedRenren() {
        return isSharedRenren;
    }

    public void setSharedRenren(Boolean sharedRenren) {
        isSharedRenren = sharedRenren;
    }

    public Boolean getStartMission() {
        return isStartMission;
    }

    public void setStartMission(Boolean startMission) {
        isStartMission = startMission;
    }

    public Boolean getNumFeedback() {
        return numFeedback;
    }

    public void setNumFeedback(Boolean numFeedback) {
        this.numFeedback = numFeedback;
    }

	public Boolean setInit() {
		return isInit;
	}

	public void setInit(Boolean init) {
		isInit = init;
	}
}
