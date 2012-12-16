package com.shopkeeper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12/13/12
 * Time: 12:20 上午
 */

public class ApiTool extends HttpServlet
{
	protected Map<String, Object> apiCategoryMap;
	protected Map<String, Object> apiParamMap;

	protected String serviceApiContent = null;

	private String readFile(String fileName){
		String appRootPath = System.getProperty("webapp.root");
		File file = new File(appRootPath + fileName);

		try {
			int length = (int) file.length();
			if (length > 0) {
				byte [] buffer = new byte[length];
				FileInputStream is = new FileInputStream(file);
				int readLength = is.read(buffer, 0, length);
				return new String(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void init(ServletConfig servletConfig) throws ServletException   {
		if (serviceApiContent == null) {
			serviceApiContent = this.readFile("api_tool.json");
		}
	}
	@SuppressWarnings(value = "unchecked")
	protected  void doService(HttpServletRequest request, HttpServletResponse response) {
		try {
			OutputStream os = response.getOutputStream();
			os.write(serviceApiContent.getBytes());
		} catch (IOException e) {

		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doService(req, resp);
	}
}
