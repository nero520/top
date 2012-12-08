package com.shopkeeper.service.request.converter;

import com.rop.request.RopConverter;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-22
 * Time: 上午6:10
 */
public class ListConverter implements RopConverter<String, List<String>>
{
    @Override
    public String unconvert(List<String> target) {
        StringBuilder sb = new StringBuilder();
        if (target != null) {
            for (String str : target) {
                sb.append(str + ",");
            }
            if (sb.length() > 0) {
                return sb.substring(0, sb.length() - 1);
            }
            else {
                return "";
            }
        }
        return null;
    }

    @Override
    public Class<String> getSourceClass() {
        return String.class;
    }

    @Override
    public Class<List<String>> getTargetClass() {
        List<String> list = new LinkedList<String>();
        return (Class<List<String>>) list.getClass();
    }

    @Override
    public List<String> convert(String source) {
        String[] strArray = StringUtils.split(source, ",");
        List<String> strList = new LinkedList<String>();
        if (strArray != null) {
            for (String str : strArray) {
                strList.add(str);
            }
            return strList;
        }
        return null;
    }
}
