/**
 * 日    期：12-3-21
 */
package com.rop;

import com.rop.security.AppSecretManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;


/**
 * <pre>
 *
 * HttpServlet是Rop框架的总入口，提供了多个定制ROP框架的配置参数：
 * 1.Rop会自己扫描Spring容器并加载之{@link SessionManager}、{@link AppSecretManager}及{@link Interceptor}的Bean。也可以通过
 * "sessionCheckerClassName"、"appSecretManagerClassName"、"interceptorClassNames"的Servlet参数指定实现类的类名。如果显式指定了Servlet
 * 参数，则Rop就不会扫描Spring容器中的Bean了。
 *
 *   如果既没有使用Servlet参数指定，也没有在Spring容器中配置，则Rop使用{@link com.rop.session.DefaultSessionManager}和{@link FileBaseAppSecretManager}
 * 作为{@link SessionManager}和{@link AppSecretManager}的实现类。
 *
 * 2.可通过"errorResourceBaseName"指定错误资源文件的基名，默认为“i18n/rop/ropError”.
 *
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 */
public class RopServlet extends HttpServlet {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private ServiceRouter serviceRouter;
/*
    private String aliasName(String name) {
        String[] subStr = StringUtils.split(name, "_");
        StringBuffer sb = new StringBuffer();
        sb.append(subStr[0]);
        for (int i = 1; i < subStr.length; i++) {
            sb.append(subStr[i].substring(0, 1).toUpperCase());
            sb.append(subStr[i].substring(1, subStr.length));
        }
        return sb.toString();
    }

    private void changeRequestParamName(HttpServletRequest req) {
        Map map = req.getParameterMap();
        req.
        Set<Map.Entry<K, V>> set = map.entrySet();
        while (enumeration.hasMoreElements()) {
            String paramName = (String)enumeration.nextElement();
            Object paramValue = req.getAttribute(paramName);
            String paramAliasName = aliasName(paramName);
            logger.info(paramAliasName);
            req.setAttribute(paramAliasName, paramValue);
        }
    }

*/
    /**
     * 将请求导向到Rop的框架中。
     *
     * @param HttpServletRequest req
     * @param HttpServletResponse resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Map paramsMap = req.getParameterMap();
//        String string = "aaaaaaaaaaaa";
//        resp.getOutputStream().write(string.getBytes());
        serviceRouter.service(req, resp);
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ApplicationContext ctx = getApplicationContext(servletConfig);
        this.serviceRouter = ctx.getBean(ServiceRouter.class);
        if (this.serviceRouter == null) {
            logger.error("在Spring容器中未找到" + ServiceRouter.class.getName() +
                    "的Bean,请在Spring配置文件中通过<aop:annotation-driven/>安装rop框架。");
        }
    }

    private ApplicationContext getApplicationContext(ServletConfig servletConfig) {
        return (ApplicationContext) servletConfig.getServletContext().getAttribute(
                WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
    }
}

