package com.shopkeeper.model;

import com.shopkeeper.db.DatabaseDriver;
import com.shopkeeper.db.DatabaseDriverFactory;
import com.shopkeeper.exception.ModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-10-10
 * Time: 下午3:43
 * To change this template use File | Settings | File Templates.
 */
public class Model {
    protected DatabaseDriver db = null;

    protected Map<String, String> options = new HashMap<String, String>();

    protected String table = null;

    protected String pk = "id";

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private boolean isNeedParse;

    public Model() throws ModelException {
        try {
            db = DatabaseDriverFactory.getInstance().defaultDriver();
        } catch (SQLException e) {
            throw new ModelException(e.getMessage());
        }
    }

    public String getPk() {
        return pk;
    }

    public Model table(String table) {
        this.table = table;
        options.put("table", table);
        return this;
    }

    public Model field(String field) {
        options.put("field", field);
        return this;
    }

    public Model where(String where) {
        options.put("where", where);
        return this;
    }

    public Model order(String order) {
        options.put("order", order);
        return this;
    }

    public Model limit(String limit) {
        options.put("limit", limit);
        return this;
    }

    public Model having(String having) {
        options.put("having", having);
        return this;
    }

    public Model group(String group) {
        options.put("group", group);
        return this;
    }

    public Model lock(String lock) {
        options.put("lock", lock);
        return this;
    }

    public Model distinct(String distinct) {
        options.put("distinct", distinct);
        return this;
    }

    protected Map<String, String> parseOptions (Map<String, String> options) {
        if (options == null) {
            options = new HashMap<String, String>();
        }
        options.putAll(this.options);
        this.options.clear();
        table(table);
        return options;
    }

    public List<Map<String, Object>> query(String str) throws ModelException {
        try {
            return db.query(str);
        } catch (SQLException e) {
            throw new ModelException(e.getMessage());
        }
    }

    public boolean execute(String sql) throws ModelException{
        try {
            return db.execute(sql);
        } catch (SQLException e) {
            throw new ModelException(e.getMessage());
        }
    }

    public int add(Map<String, Object> data) throws ModelException {
        return add(data, false);
    }

    public int add(Map<String, Object> data, boolean replace) throws ModelException {
        return add(data, new HashMap<String, String>(), replace);
    }

    public int add(Map<String, Object> data, Map<String, String> options, boolean replace) throws ModelException {
        try {
            if (data != null && data.size() > 0) {
                options = parseOptions(options);
                return db.insert(data, options, replace);
            }
            else {
                return 0;
            }
        } catch (SQLException e) {
            throw new ModelException(e.getMessage());
        }
    }

    public int add(List<Map<String, Object>> datas, boolean replace) throws ModelException {
        return add(datas, new HashMap<String, String>(), replace);
    }

    public int add(List<Map<String, Object>> datas, Map<String, String> options, boolean replace) throws ModelException {
        if (datas != null && datas.size() > 0) {
            options = parseOptions(options);
            try {
                return db.insert(datas, options, replace);
            } catch (SQLException e) {
                throw new ModelException(e.getMessage());
            }
        }
        else {
            return 0;
        }
    }
    // update
    public int save(Map<String, Object> data, Map<String, String> options) throws ModelException{
        if (data != null && data.size() > 0 ) {
            options = parseOptions(options);
            if (options.get("where") == null) {
                Object pkValue = data.get(pk);
                if (pkValue != null) {
                    options.put("where", pk + " = " + pkValue.toString());
                }
                else {
                    return 0;
                }
            }
            try {
                return db.update(data, options);
            } catch (SQLException e) {
                throw new ModelException(e.getMessage());
            }
        }
        else {
            return 0;
        }
    }

    public List<Map<String, Object>> select(Map<String, String> options) throws ModelException{
        try {
            options = parseOptions(options);
            if (options != null && options.size() == 0) {
                options = null;
            }
            return db.select(options);
        } catch (SQLException e) {
            throw new ModelException(e.getMessage());
        }
    }

    public List<Map<String, Object>> select() throws ModelException {
        return select(null);
    }

    public void startTrans() throws ModelException {
        try {
            db.startTrans();
        } catch (SQLException e) {
            throw new ModelException(e.getMessage());
        }
    }

    public void commit() throws ModelException {
        try{
            db.commit();
        } catch (SQLException e) {
            throw new ModelException(e.getMessage());
        }
    }

    public boolean rollback() throws ModelException {
        try {
            return db.rollback();
        } catch (SQLException e) {
            throw new ModelException(e.getMessage());
        }
    }

    public String getLastSql() {
        return db.getLastSql();
    }

    public void updateFromTop(String accessToken) throws ModelException {

    }

    public boolean isNeedParse() {
        return isNeedParse;
    }

    public void setNeedParse(boolean needParse) {
        isNeedParse = needParse;
    }
}
