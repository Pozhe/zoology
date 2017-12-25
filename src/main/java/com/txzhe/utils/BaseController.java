package com.txzhe.utils;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.txzhe.controller.api.AbstractSysApiController;
import com.txzhe.controller.page.AbstractController;

@SuppressWarnings("unused")

public class BaseController<T> {

	private static Logger logger = Logger.getLogger(BaseController.class);

	private static Map<String, AbstractController> classNameByPageSuperClass = null;

	private static Map<String, AbstractSysApiController> classNameByAPISuperClass = null;

	static {
		classNameByPageSuperClass = getClassNameByInterface();
		classNameByAPISuperClass = getSysAPIClassNameByInterface();
	}

	/**
	 * @return ���ݽӿڻ�ȡ������ʵ�������
	 */
	private static Map<String, AbstractController> getClassNameByInterface() {
		String packageName = AbstractController.class.getPackage().getName();
		String path = packageName.replace(".", "/");
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource(path);
		File dir = new File(url.getFile());
		return getClasses(dir, packageName);

	}

	private static Map<String, AbstractSysApiController> getSysAPIClassNameByInterface() {
		String packageName = AbstractSysApiController.class.getPackage().getName();
		String path = packageName.replace(".", "/");
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource(path);
		File dir = new File(url.getFile());
		return getApiClasses(dir, packageName);

	}

	private static Map<String, AbstractSysApiController> getApiClasses(File dir, String packageName) {
		Map<String, AbstractSysApiController> classesMap = new HashMap<>();
		if (!dir.exists()) {
			logger.warn("ҵ����ڲ�����" + dir.getAbsoluteFile());
		}
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				// �ݹ����
				classesMap.putAll(getApiClasses(f, packageName + "." + f.getName()));
			}
			String fileName = f.getName();
			if (fileName.endsWith(".class") && !fileName.contains(AbstractSysApiController.class.getSimpleName())) {
				String pkName = fileName.substring(0, fileName.length() - 6);
				String wipeSuffixClassName = pkName.substring(0, pkName.length() - 10).toLowerCase();
				String classPath = packageName + "." + pkName;
				try {
					classesMap.put(wipeSuffixClassName,
							(AbstractSysApiController) Class.forName(classPath).newInstance());
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

	/**
	 * ��dir�»�ȡ����classes����
	 * 
	 * @param <T>
	 * 
	 * @param dir
	 *            Ŀ¼
	 * @param packageName
	 *            ����
	 * @param classes
	 *            ���ʵ��
	 */
	private static Map<String, AbstractController> getClasses(File dir, String packageName) {
		Map<String, AbstractController> classesMap = new HashMap<>();
		if (!dir.exists()) {
			logger.warn("ҵ����ڲ�����" + dir.getAbsoluteFile());
		}
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				// �ݹ����
				classesMap.putAll(getClasses(f, packageName + "." + f.getName()));
			}
			String fileName = f.getName();
			if (fileName.endsWith(".class") && !fileName.contains(AbstractController.class.getSimpleName())) {
				String pkName = fileName.substring(0, fileName.length() - 6);
				String wipeSuffixClassName = pkName.substring(0, pkName.length() - 10).toLowerCase();
				String classPath = packageName + "." + pkName;
				try {
					classesMap.put(wipeSuffixClassName, (AbstractController) Class.forName(classPath).newInstance());
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
		// System.out.println(AbstractSysApiController.class.getPackage().getName());
	}

	/**
	 * ���Ŀ��Ʒַ� identity ʵ����Ψһid
	 */
	public static AbstractController sysCoreControl(String identity) {
		// ����ĳ��������controllerʵ����
		return classNameByPageSuperClass.get(identity);
	}

	/**
	 * ���Ŀ��Ʒַ� identity ʵ����Ψһid
	 */
	public static AbstractSysApiController sysApiCoreControl(String identity) {
		// ����ĳ��������controllerʵ����
		return classNameByAPISuperClass.get(identity);
	}

}
