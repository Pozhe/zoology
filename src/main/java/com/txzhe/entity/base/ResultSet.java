package com.txzhe.entity.base;

/**
 * �������
 * 
 * @author heyangda-bizcent
 * 
 *         <pre>
         ��һ�֣�����Ĭ��ʵ�����
   {
		"error_no":100,
		"error_msg" : "���óɹ�",
		"data" : {
			"id" : 1,
			"username" : "tianxingzhe",
			"age":20
		}
	}
 *         </pre>
 * 
 *         <pre>
   �ڶ��֣����ؼ�����ʽ
   {
		"error_no":100,
		"error_msg" : "���óɹ�",
		"data" : {
			"id" : 1,
			"username" : "tianxingzhe",
			"age":20
		}
	}
 *         </pre>
 * 
 */
public class ResultSet {
	protected String errorNo = "0";
	private String errorMessage = "���óɹ�";
	private Object data;// ֧����չ

	public String getErrorNo() {
		return errorNo;
	}

	public void setErrorNo(String errorNo) {
		this.errorNo = errorNo;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
