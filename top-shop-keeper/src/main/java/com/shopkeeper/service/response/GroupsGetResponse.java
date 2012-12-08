package com.shopkeeper.service.response;

import com.shopkeeper.service.domain.Group;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-18
 * Time: 下午8:59
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "groups_get_response")
public class GroupsGetResponse
{
    @XmlElementWrapper(name = "groups")
    @XmlElement(name = "group")
    private List<Group> groups;

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
