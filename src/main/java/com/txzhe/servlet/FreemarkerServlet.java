package com.txzhe.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.txzhe.controller.base.BaseController;
import com.txzhe.controller.business.IAbstractController;
import com.txzhe.utils.PropertiesUtils;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

public class FreemarkerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(FreemarkerServlet.class);
	private static final String JUMP_PAGE_INDEX_URL = "views/index.ftl";
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
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String operation = req.getParameter("method");
		IAbstractController abstractController = null;
		Map<String, Object> dataModel = null;
		if (id != null) {
			abstractController = BaseController.sysCoreControl(id.toLowerCase());
		}
		String jumpToUrl = null;
		if (abstractController != null) {
			// ���Ϊ�գ�ȥ��ҳ
			dataModel = abstractController.returnMapModel(req, resp);
			if (dataModel == null) {
				dataModel = new HashMap<>();
			}
			jumpToUrl = abstractController.jumpToPageUrl(operation);
		} else {
			jumpToUrl = JUMP_PAGE_INDEX_URL;
		}
		try {
			template = configuration.getTemplate(jumpToUrl);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("ģ��" + abstractController.jumpToPageUrl(operation) + "δ�ҵ�", e);
		}
		// ʹ��ģ���ļ���Charset��Ϊ��ҳ���charset
		// ʹ��text/html MIME-type
		resp.setContentType("text/html; charset=" + template.getEncoding());
		try {
			template.process(dataModel, resp.getWriter());
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
