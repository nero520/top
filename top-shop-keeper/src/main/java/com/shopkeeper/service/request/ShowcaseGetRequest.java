package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;
import com.rop.annotation.AliasName;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-22
 * Time: 上午7:37
 */
public class ShowcaseGetRequest extends AbstractRopRequest
{
	@AliasName(name = "user_id")
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
