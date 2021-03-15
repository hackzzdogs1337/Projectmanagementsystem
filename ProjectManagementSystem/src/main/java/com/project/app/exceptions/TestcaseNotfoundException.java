package com.project.app.exceptions;

public class TestcaseNotfoundException extends Exception {
	public TestcaseNotfoundException(String testcaseid) {
		super(testcaseid);
	}
}
