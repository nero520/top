package com.shopkeeper.db;

import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-9-25
 * Time: 上午3:54
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractDatabaseDriver implements DatabaseDriver
{
    protected static final String selectSql = "SELECT%DISTINCT% %FIELD% FROM %TABLE%%JOIN%%WHERE%%GROUP%%HAVING%%ORDER%%LIMIT% %UNION%";

    protected String parseValue(Object value) {
        String ret = null;
        if (value == null) {
            ret = "null";
        }else if (value instanceof String) {
            if (((String) value).length() > 0) {
                ret = "'" + escapeString((String)value) + "'";
            }
            else {
                ret = "null";
            }
        }
        else if (value instanceof Number) {
            ret = value.toString();
        }

        return ret;
    }

    protected String parseKey(String key) {
        return key;
    }

    protected String parseField(Object fields) {
        String ret = null;
        if (fields instanceof String && ((String) fields).lastIndexOf(",") > -1) {
            String[] fieldList = StringUtils.split((String)fields, ",", 0);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < fieldList.length; i++) {
                String field = fieldList[i];
                field = parseKey(field);
                sb.append(field + ",");
            }
            if (sb.length() > 0) {
                ret = sb.substring(0, sb.length() - 1);
            }
        }
        else if (fields instanceof Map) {
            Map<String, String> fieldMap = (Map<String, String>)fields;
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, String> kvp : fieldMap.entrySet()) {
                String key = kvp.getKey();
                String value = kvp.getValue();
                if (value != null && value.length() > 0) {
                    sb.append(parseKey(key) + " AS " + parseValue(value));
                }
                else {
                    sb.append(parseKey(key));
                }
                sb.append(",");
            }
            ret = sb.substring(0, sb.length() - 1);
        }
        else if (fields instanceof String) {
            ret = parseKey((String)fields);
        }
        else {
            ret = "*";
        }
        return ret;
    }

    protected String parseTable(Object tables) {
        String ret = null;
        if (tables instanceof Map) {
            Map<String, String> tableMap = (Map<String, String>)tables;
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, String> kvp : tableMap.entrySet()) {
                String key = kvp.getKey();
                String value = kvp.getValue();
                if (value != null && value.length() > 0) {
                    sb.append(parseKey(key) + " " + parseValue(value));
                }
                else {
                    sb.append(parseKey(key));
                }
                sb.append(",");
            }
            ret = sb.substring(0, sb.length() - 1);
        }
        else if (tables instanceof String) {
            String[] fieldList = StringUtils.split((String)tables, ",", 0);
            if (fieldList.length > 0) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < fieldList.length; i++) {
                    String field = fieldList[i];
                    field = parseKey(field);
                    sb.append(field + ",");
                }
                if (sb.length() > 0) {
                    ret = sb.substring(0, sb.length() - 1);
                }
            }
            else {
                ret = parseKey((String)tables);
            }
        }
        return ret;
    }

    protected String parseGroup(String group) {
        return (group != null && group.length() > 0) ? " GROUP BY " + group : "";
    }

    protected String parseHaving(String having) {
        return (having != null && having.length() > 0) ? " HAVING " + having : "";
    }

    protected String parseDistinct(String distinct) {
        return (distinct != null && distinct.length() > 0) ? " DISTINCT " + distinct : "";
    }

    protected String parseUnion(String union) {
        //if (union == null )
        return "";
    }

    protected String parseJoin(Object join) {
        String joinStr = null;
        if (join instanceof String) {
            String tJoin = ((String) join).trim();
            if (!(tJoin.indexOf("JOIN") == 0 ||
                  tJoin.indexOf("LEFT JOIN") == 0 ||
                  tJoin.indexOf("RIGHT JOIN") == 0)) {
                joinStr = " LEFT JOIN " + join;
            }
        }
        else if (join instanceof List) {
            List<String> joinList = (List<String>)join;
            for (String item : joinList) {
                String tJoin = item.trim();
                if (!(tJoin.indexOf("JOIN") == 0 ||
                        tJoin.indexOf("LEFT JOIN") == 0 ||
                        tJoin.indexOf("RIGHT JOIN") == 0)) {
                    joinStr = joinStr + " LEFT JOIN " + tJoin;
                }
                else {
                    joinStr = joinStr + tJoin;
                }
            }
        }
        else {
            joinStr = "";
        }
        return joinStr;
    }

    protected String parseLimit(String limit) {
        return (limit != null && limit.length() > 0) ? " LIMIT " + limit + " " : "";
    }

    protected String parseOrder(String order) {
        return (order != null && order.length() > 0) ? " ORDER BY " + order + " " : "";
    }

    protected String parseLock(String lock) {
        return "";
    }

    protected String parseSet(Map<String, Object> data) {
        StringBuffer sb = new StringBuffer();
        if (data.isEmpty()) {
            return "";
        }
        else {
            sb.append(" SET ");

        }
        for (Map.Entry kvp : data.entrySet()) {
            String key = (String)kvp.getKey();
            String value = (String)kvp.getValue();
            value = parseValue(value);
            key = parseKey(key);
            if (key != null && value != null) {
                sb.append(key + "=" + value + ",");
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    protected String parseWhere(String where) {
        return (where != null && where.length() > 0) ? " WHERE " + where : "";
    }

    protected String parseSql(String sql, Map<String, String> options) {
        String tableStr = parseTable(options.get("table"));
        String[] replacedStrs = {"%TABLE%","%DISTINCT%","%FIELD%","%JOIN%","%WHERE%","%GROUP%","%HAVING%","%ORDER%","%LIMIT%","%UNION%"};
        String[] replaceStrs = {
                tableStr,
                options.get("distinct") != null && options.get("distinct").length() > 0 ? parseDistinct(options.get("distinct")) : "",
                options.get("field") != null && options.get("field").length() > 0 ? parseField(options.get("field")) : "*",
                options.get("join") != null && options.get("join").length() > 0 ? parseJoin(options.get("join")) : "",
                options.get("where") != null && options.get("where").length() > 0 ? parseWhere(options.get("where")) : "",
                options.get("group") != null && options.get("group").length() > 0 ? parseGroup(options.get("group")) : "",
                options.get("having") != null && options.get("having").length() > 0 ? parseHaving(options.get("having")) : "",
                options.get("order") != null && options.get("order").length() > 0 ? parseOrder(options.get("order")) : "",
                options.get("limit") != null && options.get("limit").length() > 0 ? parseLimit(options.get("limit")) : "",
                options.get("union") != null && options.get("union").length() > 0 ? parseUnion(options.get("union")) : "",
        };
        return StringUtils.replaceEach(sql, replacedStrs, replaceStrs);
    }

    protected String buildSelectSql(Map<String, String> options) {
        String sql = parseSql(selectSql, options);
        return sql;
    }

    public List<Map<String, Object>> select(Map<String, String> options) throws SQLException {
        String sqlStr = buildSelectSql(options);
        return query(sqlStr);
    }

    @Override
    public int update(Map<String, Object> data, Map<String, String> options) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        String option = options.get("table");
        sb.append(parseTable(option));
        sb.append(parseSet(data));
        option = options.get("where");
        sb.append(option != null && option.length() > 0 ? parseWhere(option) : "");
        option = options.get("order");
        sb.append(option != null && option.length() > 0 ? parseOrder(option) : "");
        option = options.get("limit");
        sb.append(option != null && option.length() > 0 ? parseLimit(option) : "");
        option = options.get("lock");
        sb.append(option != null && option.length() > 0 ? parseLock(option) : "");
        return executeUpdate(sb.toString());  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean replace(Map<String, Object> data, Map<String, Object> options) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean replace(List<Map<String, Object>> datas, Map<String, Object> options) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int insert(Map<String, Object> data, Map<String, String> options, boolean replace) throws SQLException {
        List<Map<String, Object>> datas = new LinkedList<Map<String, Object>>();
        datas.add(data);
        return insert(datas, options, replace);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int insert(List<Map<String, Object>> datas, Map<String, String> options, boolean replace) throws SQLException {
        Set<String> keys = datas.get(0).keySet();
        Object[] keyArray = keys.toArray();
        List<String> allValue = new LinkedList<String>();

        for (Map<String, Object> data : datas) {
            StringBuffer sb = new StringBuffer();
            sb.append("(");
            for (int i = 0; i < keyArray.length; i++) {
                Object obj = data.get(keyArray[i]);
                String value = parseValue(obj);
                sb.append(value + ",");
            }
            allValue.add(sb.substring(0, sb.length() - 1) + ")");
        }

        String keyStr = StringUtils.join(keyArray, ",");
        String valuesStr = StringUtils.join(allValue.toArray(), ",");
        String sql = (replace ? "REPLACE INTO " : "INSERT INTO ") + parseTable(options.get("table")) + " (" +
                keyStr + ") VALUES " + valuesStr;
        return executeUpdate(sql);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean delete(Map<String, Object> options) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM ");
        String option = (String)options.get("table");
        sb.append(parseTable(option));
        option = (String)options.get("where");
        sb.append(option != null && option.length() > 0 ? parseWhere(option) : "");
        option = (String)options.get("order");
        sb.append(option != null && option.length() > 0 ? parseOrder(option) : "");
        option = (String)options.get("limit");
        sb.append(option != null && option.length() > 0 ? parseLimit(option) : "");
        option = (String)options.get("lock");
        sb.append(option != null && option.length() > 0 ? parseLock(option) : "");
        return execute(sb.toString());  //To change body of implemented methods use File | Settings | File Templates.
    }
}
