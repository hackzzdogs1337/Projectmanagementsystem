package com.project.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.app.model.Requirementmodel;
import com.project.app.model.SequenceGenerator;
import com.project.app.model.Testcasemodel;
import com.project.app.repositories.Requirementrepo;
import com.project.app.repositories.Testcaserepo;

@Service
public class Requirementservices {
	
	@Autowired
	private Requirementrepo mongorepo;
	
	@Autowired
	private Testcaserepo testcaserepo;
	
	//Create requirements
	public Requirementmodel createrequirement(String projectid,Requirementmodel requirement) {
		List<Requirementmodel> requirements=mongorepo.findlastinserteddocs(projectid);
		if(requirements.size()==0) {
			requirement.setProjectid(projectid);
			requirement.setReqid(projectid+"-R-1");
			mongorepo.insert(requirement);
		}
		else {
			requirement.setProjectid(projectid);
			requirement.setReqid(projectid+'-'+SequenceGenerator.generateID(requirements.get(0).getReqid()));
			mongorepo.insert(requirement);
			
		}
		
		return requirement;
	}
	
	//Get requirements by projectid
	public List<Requirementmodel> getreqsbyprojectid(String projectid){
		return mongorepo.findrequirementsbyProjectid(projectid);
	}
	
	//Get requirement by reqid
	public Requirementmodel getrequirementbyid(String reqid) {
		return mongorepo.findByReqid(reqid);
	}
	
	//Edit requirement by reqid
	public boolean editrequirementbyid(String reqid,String projectid,Requirementmodel requirement) {
		if(mongorepo.existsById(reqid)) {
			requirement.setReqid(reqid);
			requirement.setProjectid(projectid);
			mongorepo.save(requirement);
			return true;
			
		}
		return false;
	}
	
	//Delete requirement by reqid
	public void deletereqbyid(String reqid) {
		Requirementmodel requirement=mongorepo.findByReqid(reqid);
		requirement.setDeleted(true);
		//Cascade all testcases
		List<Testcasemodel> testcases=testcaserepo.findByReqid(reqid);
		for(Testcasemodel testcase:testcases) {
			testcase.setDeleted(true);
		}
		testcaserepo.saveAll(testcases);		
		mongorepo.save(requirement);
		
	}
	
}
