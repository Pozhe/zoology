package com.txzhe.controller.business.formManage;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.txzhe.controller.business.AbstractController;

/**
 * ��ת ��Ѷ����-�б�
 * 
 * @author heyangda-bizcent
 */
public class FormController extends AbstractController {

	@Override
	public Map<String, Object> returnMapModel(HttpServletRequest req, HttpServletResponse resp) {
		Map<String, Object> map = new HashMap<>();
		//String operation = req.getParameter("method");
		return map;
	}
}
