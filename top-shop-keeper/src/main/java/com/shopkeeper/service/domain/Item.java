package com.shopkeeper.service.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-19
 * Time: 上午7:17
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "item")
public class Item
{
    @XmlElement(name = "num_iid")
    private Long numIid;
    @XmlElement(name = "user_id")
    private Long userId;
    @XmlElement(name = "group_id")
    private String groupId;
    @XmlElement(name = "task_id")
    private String taskId;
    @XmlElement
    private String status; // value: onsale, inventory
    @XmlElement(name = "time_onsale")
    private Date timeOnsale;
    @XmlElement(name = "auto_showcase_status")
    private String autoShowcaseStatus;

    // top fields
    @XmlElement
    private String title;
    @XmlElement
    private String nick;
    @XmlElement
    private String type;
    @XmlElement
    private Long cid;
    @XmlElement(name = "seller_cids")
    private String sellerCids;
    @XmlElement
    private String props;
    @XmlElement(name = "pic_url")
    private String picUrl;
    @XmlElement
    private Integer num;
    @XmlElement(name = "valid_thru")
    private Integer validThru;
    @XmlElement(name = "list_time")
    private Date listTime;
    @XmlElement(name = "delist_time")
    private Date delistTime;
    @XmlElement
    private String price;
    @XmlElement(name = "has_discount")
    private Boolean hasDiscount;
    @XmlElement(name = "has_invoice")
    private Boolean hasInvoice;
    @XmlElement(name = "has_warranty")
    private Boolean hasWarranty;
    @XmlElement(name = "has_showcase")
    private Boolean hasShowcase;
    @XmlElement
    private Date modified;
    @XmlElement(name = "approve_status")
    private String approveStatus;
    @XmlElement(name = "postage_id")
    private Long postageId;
    @XmlElement(name = "outer_id")
    private String outerId;
    @XmlElement(name = "is_virtual")
    private Boolean isVirtual;
    @XmlElement(name = "is_taobao")
    private Boolean isTaobao;
    @XmlElement(name = "is_ex")
    private Boolean isEx;

    public Long getNumIid() {
        return numIid;
    }

    public void setNumIid(Long numIid) {
        this.numIid = numIid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getSellerCids() {
        return sellerCids;
    }

    public void setSellerCids(String sellerCids) {
        this.sellerCids = sellerCids;
    }

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getValidThru() {
        return validThru;
    }

    public void setValidThru(Integer validThru) {
        this.validThru = validThru;
    }

    public Date getListTime() {
        return listTime;
    }

    public void setListTime(Date listTime) {
        this.listTime = listTime;
    }

    public Date getDelistTime() {
        return delistTime;
    }

    public void setDelistTime(Date delistTime) {
        this.delistTime = delistTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getHasDiscount() {
        return hasDiscount;
    }

    public void setHasDiscount(Boolean hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    public Boolean getHasInvoice() {
        return hasInvoice;
    }

    public void setHasInvoice(Boolean hasInvoice) {
        this.hasInvoice = hasInvoice;
    }

    public Boolean getHasWarranty() {
        return hasWarranty;
    }

    public void setHasWarranty(Boolean hasWarranty) {
        this.hasWarranty = hasWarranty;
    }

    public Boolean getHasShowcase() {
        return hasShowcase;
    }

    public void setHasShowcase(Boolean hasShowcase) {
        this.hasShowcase = hasShowcase;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Long getPostageId() {
        return postageId;
    }

    public void setPostageId(Long postageId) {
        this.postageId = postageId;
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public Boolean getVirtual() {
        return isVirtual;
    }

    public void setVirtual(Boolean virtual) {
        isVirtual = virtual;
    }

    public Boolean getTaobao() {
        return isTaobao;
    }

    public void setTaobao(Boolean taobao) {
        isTaobao = taobao;
    }

    public Boolean getEx() {
        return isEx;
    }

    public void setEx(Boolean ex) {
        isEx = ex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTimeOnsale() {
        return timeOnsale;
    }

    public void setTimeOnsale(Date timeOnsale) {
        this.timeOnsale = timeOnsale;
    }

    public String getAutoShowcaseStatus() {
        return autoShowcaseStatus;
    }

    public void setAutoShowcaseStatus(String autoShowcaseStatus) {
        this.autoShowcaseStatus = autoShowcaseStatus;
    }
}
