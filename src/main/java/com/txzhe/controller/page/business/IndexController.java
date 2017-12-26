package com.txzhe.controller.page.business;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.txzhe.controller.page.AbstractController;
import com.txzhe.dao.system.IPrivilegeDao;
import com.txzhe.dao.system.impl.PrivilegeDaoImpl;
import com.txzhe.entity.base.DataRow;
import com.txzhe.entity.system.Privilege;
import com.txzhe.utils.LoggerUtils;
import com.txzhe.utils.RequestUtils;

public class IndexController extends AbstractController {

	@Override
	public DataRow returnMapModel(HttpServletRequest req, HttpServletResponse resp) {
		LoggerUtils.info("index controller start");

		String userId = RequestUtils.getCookie("userId", req);
		IPrivilegeDao pDao = new PrivilegeDaoImpl();
		userId = "1";
		List<Privilege> privilegeList = pDao.queryPrivilegeListByUserId(userId);
		//���β˵��ṹ
		/**
		 * 
		 * ���ڵ�
		 * |
		 * һ���˵�
		 * | |
		 * | �����˵�1
		 * | �����˵�2
		 * | 	|
		 * |	�����˵�
		 * һ���˵�
		 *			
		 */
		/**
		 * privilegeList 
		 * [id:1;pid=0,name:one]
		 * [id:2;pid=1,name:2j]
		 * [id:3;pid=1,name:2j]
		 * [id:4;pid=1,name:2j]
		 */
		
		
		LoggerUtils.info("index controller end");
		return null;
	}
}
