package com.shopkeeper.service.request.converter;

import com.rop.request.RopConverter;
import com.shopkeeper.utils.Utils;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-20
 * Time: 上午2:15
 * To change this template use File | Settings | File Templates.
 */
public class DateConverter implements RopConverter<String, Date> {
    @Override
    public String unconvert(Date target) {
        String str = Utils.dateToString(target);
        return str;
    }

    @Override
    public Class<String> getSourceClass() {
        return String.class;
    }

    @Override
    public Class<Date> getTargetClass() {
        return Date.class;
    }

    @Override
    public Date convert(String source) {
        Date date = Utils.stringToDate(source);
        return date;
    }
}
