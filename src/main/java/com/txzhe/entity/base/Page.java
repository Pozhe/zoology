package com.txzhe.entity.base;

import java.io.Serializable;

public class Page implements Serializable {
	private static final long serialVersionUID = 1L;
	// �ܼ�¼��
	private int totalRecords;
	// ��ҳ��
	private int totalPages;

	public Page() {
		totalRecords = 100;
		totalPages = 10;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

}
