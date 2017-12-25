package com.txzhe.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.txzhe.controller.api.AbstractSysApiController;
import com.txzhe.entity.base.ResultSet;
import com.txzhe.utils.BaseController;

/**
 * ϵͳ������
 * 
 * @author heyangda-bizcent
 */
public class SysAPIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SysAPIServlet.class);

	@Override
	public void init() throws ServletException {
		logger.info("System API init ...");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) {
		// ��������get\post��������
		ResultSet rs = null;
		String prefix = request.getParameter("id");
		if (prefix == null) {
			rs = new ResultSet();
			rs.setErrorNo("-2");
			rs.setErrorMessage("ϵͳ�쳣," + prefix + "��Ӧ������Ϊ�գ�");
			rs.setData(new HashMap<Object, Object>());
			writeJSON(response, rs);
			return;
		}
		AbstractSysApiController api = BaseController.sysApiCoreControl(prefix);
		if (api == null) {
			rs = new ResultSet();
			rs.setErrorNo("-1");
			rs.setErrorMessage("ϵͳ�쳣");
			rs.setData(new HashMap<Object, Object>());
			writeJSON(response, rs);
			return;
		}
		rs = api.execute(request, response);
		if (rs == null) {
			rs = new ResultSet();
			rs.setData(new HashMap<Object, Object>());
			logger.warn("�����Ϊ�գ���ȷ��");
		}
		writeJSON(response, rs);
	}

	public static void main(String[] args) {
		ResultSet rs = new ResultSet();
		rs.setData(new HashMap<Object, Object>());
		System.out.println(JSON.toJSON(rs));
	}

	/**
	 * ��������json��ʽ���
	 */
	private void writeJSON(HttpServletResponse response, ResultSet rs) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			// �����������
			out = response.getWriter();
			out.write(JSON.toJSONString(rs));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("������ݳ���!", e);
		}
	}

	@Override
	public void destroy() {
		logger.info("System API destory...");
	}
}
