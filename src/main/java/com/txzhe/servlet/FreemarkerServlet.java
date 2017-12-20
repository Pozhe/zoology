package com.txzhe.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.txzhe.entity.User;
import com.txzhe.utils.PropertiesUtils;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

public class FreemarkerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(FreemarkerServlet.class);
	public Configuration configuration = null;

	protected Template template = null;
	
	@Override
	public void init() throws ServletException {
		// ����һ��FreeMarkerʵ��
		configuration = new Configuration();
		try {
			configuration.setSettings(PropertiesUtils.freemarkerMap.get("settings"));
			configuration.setAllSharedVariables(
					new SimpleHash(PropertiesUtils.freemarkerMap.get("variables"), configuration.getObjectWrapper()));
		} catch (TemplateModelException e) {
			e.printStackTrace();
			logger.error("ģ��setAllSharedVariables�쳣", e);
		} catch (TemplateException e) {
			e.printStackTrace();
			logger.error("ģ��setSettings�쳣", e);
		}
		// ָ��FreeMarkerģ���ļ���λ��
		configuration.setServletContextForTemplateLoading(getServletContext(), "/Hui/");
		//ͳһģ��
		try {
			template = configuration.getTemplate("index.ftl");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("ģ��index.ftlδ�ҵ�", e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ʹ��ģ���ļ���Charset��Ϊ��ҳ���charset
		// ʹ��text/html MIME-type
		resp.setContentType("text/html; charset=" + template.getEncoding());
		Map<String, Object> dataModel = new HashMap<String, Object>();
		List<User> userList = new ArrayList<User>();
		User user = null;
		for (int i = 0; i < 10; i++) {
			user = new User();
			user.setId("" + i);
			user.setUserName("Name-" + i);
			user.setPassword(UUID.randomUUID().toString().replaceAll("-", ""));
			userList.add(user);
		}
		dataModel.put("userList", userList);
		dataModel.put("toRouter", "views/index/home.ftl");
		try {
			template.process(dataModel, resp.getWriter());
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
