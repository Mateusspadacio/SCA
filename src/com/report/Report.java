package com.report;

import java.io.File;
import java.util.List;
import java.util.Vector;

import com.def.methods.DefaultMethods;
import com.exceptions.PathNotSelected;

public class Report {

	private List<Object> dataList;
	private Vector<File> pathList;
	private ReportAdapter ra;

	public Report(List<Object> dataList, Vector<File> pathList) {
		this.dataList = dataList;
		this.pathList = pathList;
		ra = new ReportAdapter();
	}

	public void generate() {
		try {
			ra.openDocument();
			sendData();
			ra.generate();
		} catch (PathNotSelected e) {
		}
	}
	
	private void sendData() {
		int size = pathList.size();
		SendDataReport sendReportData = new SendDataReport(ra);
		ra.setFileList(pathList);

		for (int i = 0; i < size; i++) {
			ra.setFile(pathList.get(i).getName());
			List<DefaultMethods> list = (List<DefaultMethods>) dataList.get(i);
			for (DefaultMethods objList : list) {
				try {
					sendReportData.verifyData(objList);
				} catch (IllegalArgumentException | SecurityException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
