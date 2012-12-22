package com.shopkeeper.service.response;

import com.shopkeeper.service.domain.User;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-12-23
 * Time: 上午2:36
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "user_delete_response")
public class UserDeleteResponse
{
	@XmlElement
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
