package com.shopkeeper.service.domain;

import com.rop.marshaller.JaxbXmlRopMarshaller;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-18
 * Time: 上午6:32
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "group")
public class Group
{
    @XmlElement
    private String id;

    @XmlElement(name = "user_id")
    private Long userId;

    @XmlElement
    private String name;

    @XmlElement
    private String description = "";

    @XmlElement(name = "item_count")
    private Integer itemCount = 0;

    @XmlElement
    private String category;

    @XmlElement
    private Date created;

    public String getId() {
        return id;
    }

    public void setId(String groupId) {
        this.id = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
