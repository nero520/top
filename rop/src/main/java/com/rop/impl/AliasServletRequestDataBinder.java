package com.rop.impl;

import com.rop.annotation.AliasName;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.web.bind.ServletRequestDataBinder;

import javax.servlet.ServletRequest;
import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-9-29
 * Time: 上午4:42
 * To change this template use File | Settings | File Templates.
 */
public class AliasServletRequestDataBinder extends ServletRequestDataBinder {

    public AliasServletRequestDataBinder(Object target) {
        super(target);
    }

    public AliasServletRequestDataBinder(Object target, String objectName) {
        super(target, objectName);
    }

    protected void addBindValues(MutablePropertyValues mpvs, ServletRequest request) {
        Object target = getTarget();
        Field[] fields = target.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            AliasName aliasNameAnnotation = field.getAnnotation(AliasName.class);
            if (aliasNameAnnotation != null) {
                String aliasName = aliasNameAnnotation.aliasName();
                String fieldName = field.getName();
                PropertyValue pv = mpvs.getPropertyValue(aliasName);
                Object value = pv.getValue();
                if (pv != null && value != null) {
                    mpvs.addPropertyValue(fieldName, value);
                }
            }
        }
    }
}
