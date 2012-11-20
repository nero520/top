package com.shopkeeper.service.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-18
 * Time: 上午5:37
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "top_user")
public class TopUser
{
    @XmlElement(name = "user_id")
    private Long userId;
    @XmlElement
    private String uid;
    @XmlElement
    private String nick;
    @XmlElement
    private String sex;
    @XmlElement
    private Date created;
    @XmlElement(name = "last_visit")
    private Date lastVisit;
    @XmlElement
    private Date birthday;
    @XmlElement
    private String type;
    @XmlElement(name = "has_more_pic")
    private Boolean hasMorePic;
    @XmlElement(name = "item_img_num")
    private Integer itemImgNum;
    @XmlElement(name = "item_img_size")
    private Integer itemImgSize;
    @XmlElement(name = "prop_img_num")
    private Integer propImgNum;
    @XmlElement(name = "prop_img_size")
    private Integer propImgSize;
    @XmlElement(name = "auto_repost")
    private String autoRepost;
    @XmlElement(name = "promoted_type")
    private String promotedType;
    @XmlElement
    private String status;
    @XmlElement(name = "alipay_bind")
    private String alipayBind;
    @XmlElement(name = "consumer_protection")
    private Boolean consumerProtection;
    @XmlElement
    private String avatar;
    @XmlElement
    private Boolean liangpin;
    @XmlElement(name = "sign_food_seller_promise")
    private Boolean signFoodSellerPromise;
    @XmlElement(name = "has_shop")
    private Boolean hasShop;
    @XmlElement(name = "is_lightning_consignment")
    private Boolean isLightningConsignment;
    @XmlElement(name = "has_sub_stock")
    private Boolean hasSubStock;
    @XmlElement(name = "is_golden_seller")
    private Boolean isGoldenSeller;
    @XmlElement(name = "vip_info")
    private String vipInfo;
    @XmlElement
    private String email;
    @XmlElement(name = "magazine_subscribe")
    private Boolean magazineSubscribe;
    @XmlElement(name = "vertical_market")
    private String verticalMarket;
    @XmlElement(name = "online_game")
    private Boolean onlineGame;
    @XmlElement(name = "buyer_credit")
    private BuyerCredit buyerCredit;
    @XmlElement(name = "seller_credit")
    private SellerCredit sellerCredit;

    public BuyerCredit getBuyerCredit() {
        return buyerCredit;
    }

    public void setBuyerCredit(BuyerCredit buyerCredit) {
        this.buyerCredit = buyerCredit;
    }

    public SellerCredit getSellerCredit() {
        return sellerCredit;
    }

    public void setSellerCredit(SellerCredit sellerCredit) {
        this.sellerCredit = sellerCredit;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getHasMorePic() {
        return hasMorePic;
    }

    public void setHasMorePic(Boolean hasMorePic) {
        this.hasMorePic = hasMorePic;
    }

    public Integer getItemImgNum() {
        return itemImgNum;
    }

    public void setItemImgNum(Integer itemImgNum) {
        this.itemImgNum = itemImgNum;
    }

    public Integer getItemImgSize() {
        return itemImgSize;
    }

    public void setItemImgSize(Integer itemImgSize) {
        this.itemImgSize = itemImgSize;
    }

    public Integer getPropImgNum() {
        return propImgNum;
    }

    public void setPropImgNum(Integer propImgNum) {
        this.propImgNum = propImgNum;
    }

    public Integer getPropImgSize() {
        return propImgSize;
    }

    public void setPropImgSize(Integer propImgSize) {
        this.propImgSize = propImgSize;
    }

    public String getAutoRepost() {
        return autoRepost;
    }

    public void setAutoRepost(String autoRepost) {
        this.autoRepost = autoRepost;
    }

    public String getPromotedType() {
        return promotedType;
    }

    public void setPromotedType(String promotedType) {
        this.promotedType = promotedType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlipayBind() {
        return alipayBind;
    }

    public void setAlipayBind(String alipayBind) {
        this.alipayBind = alipayBind;
    }

    public Boolean getConsumerProtection() {
        return consumerProtection;
    }

    public void setConsumerProtection(Boolean consumerProtection) {
        this.consumerProtection = consumerProtection;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getLiangpin() {
        return liangpin;
    }

    public void setLiangpin(Boolean liangpin) {
        this.liangpin = liangpin;
    }

    public Boolean getSignFoodSellerPromise() {
        return signFoodSellerPromise;
    }

    public void setSignFoodSellerPromise(Boolean signFoodSellerPromise) {
        this.signFoodSellerPromise = signFoodSellerPromise;
    }

    public Boolean getHasShop() {
        return hasShop;
    }

    public void setHasShop(Boolean hasShop) {
        this.hasShop = hasShop;
    }

    public Boolean getLightningConsignment() {
        return isLightningConsignment;
    }

    public void setLightningConsignment(Boolean lightningConsignment) {
        isLightningConsignment = lightningConsignment;
    }

    public Boolean getHasSubStock() {
        return hasSubStock;
    }

    public void setHasSubStock(Boolean hasSubStock) {
        this.hasSubStock = hasSubStock;
    }

    public Boolean getGoldenSeller() {
        return isGoldenSeller;
    }

    public void setGoldenSeller(Boolean goldenSeller) {
        isGoldenSeller = goldenSeller;
    }

    public String getVipInfo() {
        return vipInfo;
    }

    public void setVipInfo(String vipInfo) {
        this.vipInfo = vipInfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getMagazineSubscribe() {
        return magazineSubscribe;
    }

    public void setMagazineSubscribe(Boolean magazineSubscribe) {
        this.magazineSubscribe = magazineSubscribe;
    }

    public String getVerticalMarket() {
        return verticalMarket;
    }

    public void setVerticalMarket(String verticalMarket) {
        this.verticalMarket = verticalMarket;
    }

    public Boolean getOnlineGame() {
        return onlineGame;
    }

    public void setOnlineGame(Boolean onlineGame) {
        this.onlineGame = onlineGame;
    }
}
