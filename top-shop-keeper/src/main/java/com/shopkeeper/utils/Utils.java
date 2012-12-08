package com.shopkeeper.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.rop.Constants;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-18
 * Time: 上午4:07
 */
public class Utils
{
    private Utils() {}

    public static DBObject splitFilds(String fields) {
        if (fields == null) {
            return null;
        }
        String[] fieldsList = StringUtils.split(fields, ",");
        if (fieldsList.length == 0) {
            return null;
        }
        BasicDBObject object = new BasicDBObject();
        for(String field : fieldsList) {
            field = field.trim();
            object.put(field, 1);
        }
        return object;
    }

    public static <T> T split(String str, String separatorChars, Class<T> clazz) {
        if (str == null) {
            return null;
        }
        if (separatorChars == null) {
            separatorChars = ",";
        }
        String[] fieldsList = StringUtils.split(str, separatorChars);
        if (fieldsList.length == 0) {
            return null;
        }
        T object = null;
        try {
            object = clazz.newInstance();
            Class[] interfaces = clazz.getInterfaces();
            boolean isMap = false;
            boolean isList = false;
            for (Class implInterface : interfaces) {
                String name = implInterface.getName();
                if (name.compareTo("Map") == 0) {
                    isMap = true;
                    break;
                }
                else if (name.compareTo("List") == 0) {
                    isList = true;
                    break;
                }
            }
            if (isMap) {
                Map<String, Object> map = (Map<String, Object>)object;
                for(String field : fieldsList) {
                    field = field.trim();
                    map.put(field, 1);
                }
            }
            else if (isList) {
                List<Object> list = (List<Object>) object;
                for(String field : fieldsList) {
                    field = field.trim();
                    list.add(field);
                }
            }
            return object;
        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }

        return null;
    }

    public static String getDate() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String s = dateFormat.format(date);
        return s;
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String s = dateFormat.format(date);
        return s;
    }

    public static Date stringToDate(String str) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {

        }
        return date;
    }

    public static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes(Constants.UTF8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }

    public static byte[] getMD5Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes(Constants.UTF8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }

    /**
     * 二进制转十六进制字符串
     *
     * @param bytes
     * @return
     */
    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
}
