package com.project.app.exceptions;

public class RequirementNotFoundexception extends Exception{
	public RequirementNotFoundexception(String reqid) {
		super(reqid);
	}
}
