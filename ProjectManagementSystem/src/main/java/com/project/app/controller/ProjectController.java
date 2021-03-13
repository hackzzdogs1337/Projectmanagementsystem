package com.project.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.app.model.Projectmodel;
import com.project.app.model.Requirementmodel;
import com.project.app.services.Projectservices;
import com.project.app.services.Requirementservices;
import com.project.app.services.Testcaseservices;
import com.project.app.model.Testcasemodel;

@RestController
@RequestMapping("/project")
public class ProjectController {
	
	private Projectservices service;
	@Autowired
	public ProjectController(Projectservices service) {
		this.service=service;
	}
	
	@Autowired
	private Requirementservices reqservice;
	
	@Autowired
	private Testcaseservices testcaseservice;
	
	//Create a new project
	@PostMapping(value="/")
	public ResponseEntity<Projectmodel> createnewproject(@RequestBody Projectmodel project){
		service.createproject(project);
		return ResponseEntity.ok(project);
	}
	//Get a project by id;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Projectmodel> getprojectbyid(@PathVariable(name="id") String id){
		return ResponseEntity.ok(service.getprojectbyid(id));
	}
	
	//List all projects
	@GetMapping(value="/")
	public ResponseEntity<List> getallprojects(){
		return ResponseEntity.ok(service.getallprojects());
	}
	
	//Edit project by id
	@PutMapping(value="/{id}")
	public ResponseEntity<String> editprojectbyid(@PathVariable(name="id") String id,@RequestBody Projectmodel project ){
		if(service.editprojectbyid(id,project)) {
			return ResponseEntity.ok("Project edited successfully");
		}
		return ResponseEntity.ok("Project not found");
	}
	
	//Create new requirement
	@PostMapping(value="/{projectid}/req")
	public ResponseEntity<Requirementmodel> createrequirement(@PathVariable(name="projectid")String projectid,@RequestBody Requirementmodel requirement)
	{
		return ResponseEntity.ok(reqservice.createrequirement(projectid,requirement));
		
	}
	
	//Get all requirements by project id
	@GetMapping(value="/{projectid}/req")
	public ResponseEntity<List> getrequirementsbyprojectid(@PathVariable(name="projectid")String projectid){
		return ResponseEntity.ok(reqservice.getreqsbyprojectid(projectid));
	}
	
	//Get requirement by id
	@GetMapping(value="/{projectid}/req/{reqid}")
	public ResponseEntity<Requirementmodel> getrequirementbyid(@PathVariable(name="reqid") String reqid){
		return ResponseEntity.ok(reqservice.getrequirementbyid(reqid));
	}
	
	//Edit requirement by id
	
	@PutMapping(value="/{projectid}/req/{reqid}")
	public ResponseEntity<String> editrequirementbyid(@PathVariable(name="reqid") String reqid,@PathVariable(name="projectid") String projectid,@RequestBody Requirementmodel requirement){
		if(reqservice.editrequirementbyid(reqid, projectid,requirement)) {
			return ResponseEntity.ok("Requirement edited successfully");
		}
		return (ResponseEntity<String>) ResponseEntity.notFound();
	}
	
	//Delete requirement by id
	@DeleteMapping(value="/{projectid}/req/{reqid}")
	public  ResponseEntity<String> deleterequirementbyid(@PathVariable(name="reqid") String reqid) {
		reqservice.deletereqbyid(reqid);
		return  ResponseEntity.ok("Requirement deleted");
	}
	
	//Create testcase for a requirement
	@PostMapping(value="/{projectid}/req/{reqid}/testcase")
	public ResponseEntity<Testcasemodel> createtestcase(@PathVariable(name="projectid") String projectid,@PathVariable(name="reqid") String reqid,@RequestBody Testcasemodel testcase){
		return ResponseEntity.ok(testcaseservice.createtestcase(projectid, reqid, testcase));
	}
	
	//Get all testcases associated with a project
	@GetMapping(value="/{projectid}/testcase")
	public ResponseEntity gettestcasesbyprojectid(@PathVariable(name="projectid") String projectid){
		return ResponseEntity.ok(testcaseservice.gettestcasebyprojectid(projectid));
	}
	
	//Get all testcases by requirement id
	@GetMapping(value="/{projectid}/req/{reqid}/testcase")
	public ResponseEntity gettestcasesbyrequirementid(@PathVariable(name="reqid") String reqid) {
		return ResponseEntity.ok(testcaseservice.gettestcasesbyrequirementid(reqid));
	}
	
	//Get testcase by testcase id
	
	@GetMapping(value="/{projectid}/req/{reqid}/testcase/{testcaseid}")
	public ResponseEntity gettestcasebyid(@PathVariable(name = "testcaseid") String testid) {
		return ResponseEntity.ok(testcaseservice.gettestcasebyid(testid));
	}
	
	//Edit Testcase by testcaseid
	@PutMapping(value="/{projectid}/req/{reqid}/testcase/{testcaseid}")
	public ResponseEntity edittestcase(@PathVariable(name="testcaseid") String testid,@RequestBody Testcasemodel testcase)
	{
		return ResponseEntity.ok(testcaseservice.edittestcase(testid, testcase));
	}
	
}