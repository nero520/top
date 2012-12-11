package com.shopkeeper.service.domain;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-18
 * Time: 上午5:48
 */

abstract public class PeopleCredit
{
    @XmlElement(name = "user_id")
    private Long userId;

    @XmlElement(name = "good_num")
    private Integer goodNum;

    @XmlElement(name = "level")
    private Integer level;

    @XmlElement(name = "score")
    private Integer score;

    @XmlElement(name = "total_num")
    private Integer totalNum;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }
}
