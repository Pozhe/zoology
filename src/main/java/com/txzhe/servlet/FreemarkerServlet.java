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

import com.txzhe.entity.User;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

public class FreemarkerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Configuration configuration = null;

	@Override
	public void init() throws ServletException {
		// ����һ��FreeMarkerʵ��
		configuration = new Configuration();

		try {
			configuration.setAllSharedVariables(new SimpleHash(new HashMap<>(), configuration.getObjectWrapper()));
		} catch (TemplateModelException e) {
			e.printStackTrace();
		}
		// ָ��FreeMarkerģ���ļ���λ��
		configuration.setServletContextForTemplateLoading(getServletContext(), "/WEB-INF/templates");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Template template = configuration.getTemplate("list.ftl");
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
		try {
			template.process(dataModel, resp.getWriter());
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
