package com.txzhe.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.txzhe.controller.page.AbstractController;
import com.txzhe.utils.BaseController;
import com.txzhe.utils.PropertiesUtils;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

/**
 * ϵͳ������
 * 
 * @author heyangda-bizcent
 */
public class SysTrackerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SysTrackerServlet.class);
	public static final String JUMP_PAGE_INDEX_URL = "views/index.ftl";
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
		configuration.setServletContextForTemplateLoading(getServletContext(), "/Txzhe/");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String operation = req.getParameter("method");
		Map<String, Object> dataModel = new HashMap<>();
		if (id == null || operation == null) {
			template = configuration.getTemplate(JUMP_PAGE_INDEX_URL);
		} else {
			AbstractController abstractController = BaseController.sysCoreControl(id.toLowerCase());
			if (abstractController == null) {
				template = configuration.getTemplate(JUMP_PAGE_INDEX_URL);
			} else {
				dataModel = handleReq(operation, req, resp, abstractController);
			}
		}
		try {
			// ʹ��ģ���ļ���Charset��Ϊ��ҳ���charset
			// ʹ��text/html MIME-type
			resp.setContentType("text/html; charset=" + template.getEncoding());
			template.process(dataModel, resp.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("ģ��δ�ҵ�", e);
		} catch (TemplateException e) {
			e.printStackTrace();
		}

	}

	private Map<String, Object> handleReq(String operation, HttpServletRequest req, HttpServletResponse resp,
			AbstractController abstractController) {
		Map<String, Object> dataModel = abstractController.returnMapModel(req, resp);
		if (dataModel == null) {
			dataModel = new HashMap<>();
		}
		try {
			template = configuration.getTemplate(abstractController.jumpToPageUrl(operation));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataModel;
	}
}
