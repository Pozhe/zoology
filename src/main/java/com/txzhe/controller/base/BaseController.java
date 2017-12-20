package com.txzhe.controller.base;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.txzhe.controller.business.IAbstractController;

public abstract class BaseController {

	private static Logger logger = Logger.getLogger(BaseController.class);

	private static Map<String, IAbstractController> classNameByInterface = null;

	static {
		classNameByInterface = getClassNameByInterface();
	}

	/**
	 * @return ���ݽӿڻ�ȡ������ʵ�������
	 */
	private static Map<String, IAbstractController> getClassNameByInterface() {
		String packageName = IAbstractController.class.getPackage().getName();
		String path = packageName.replace(".", "/");
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource(path);
		File dir = new File(url.getFile());
		return getClasses(dir, packageName);

	}

	/**
	 * ��dir�»�ȡ����classes����
	 * 
	 * @param dir
	 *            Ŀ¼
	 * @param packageName
	 *            ����
	 * @param classes
	 *            ���ʵ��
	 */
	private static Map<String, IAbstractController> getClasses(File dir, String packageName) {
		Map<String, IAbstractController> classesMap = new HashMap<>();
		if (!dir.exists()) {
			logger.warn("ҵ����ڲ�����" + dir.getAbsoluteFile());
		}
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				// �ݹ����
				classesMap.putAll(getClasses(f, packageName + "." + f.getName()));
			}
			String fileName = f.getName();
			if (fileName.endsWith(".class") && !fileName.contains(IAbstractController.class.getSimpleName())) {
				String pkName = fileName.substring(0, fileName.length() - 6);
				String wipeSuffixClassName = pkName.substring(0, pkName.length() - 10).toLowerCase();
				String classPath = packageName + "." + pkName;
				try {
					classesMap.put(wipeSuffixClassName, (IAbstractController) Class.forName(classPath).newInstance());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					logger.error(fileName + "δ�ҵ�", e);
				} catch (InstantiationException e) {
					e.printStackTrace();
					logger.error("ʵ�������쳣", e);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					logger.error("ʵ�������쳣", e);
				}
			}
		}
		return classesMap;
	}

	public static void main(String[] args) {
		// getClassNameByInterface();
	}

	/**
	 * ���Ŀ��Ʒַ� identity ʵ����Ψһid
	 */
	public static IAbstractController sysCoreControl(String identity) {
		// ����ĳ��������controllerʵ����
		return classNameByInterface.get(identity);
	}
}
