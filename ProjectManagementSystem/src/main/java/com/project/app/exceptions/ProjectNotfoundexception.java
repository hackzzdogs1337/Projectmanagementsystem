package com.project.app.exceptions;

public class ProjectNotfoundexception extends Exception{

	public ProjectNotfoundexception(String projectid) {
		super(projectid);
	}
}
