package com.zemoso.mockSight.controller;

import com.zemoso.mockSight.Modal.Project;
import com.zemoso.mockSight.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(ProjectController.BASE_URL)
public class ProjectController {

    public static final String BASE_URL = "/mocksight";

    @Autowired
    private ProjectService projectService;

    /**
     * Api for retrieving all project
     * @return map either with projects or with error string
     */
    @GetMapping(path = "/projects")
    @ResponseBody
    public ResponseEntity<Map> fetchProjects()
    {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        Map responseBody = new HashMap();

        try
        {
            List projects = projectService.getProjects();
            responseBody.put("Project",projects);
            httpStatus = HttpStatus.OK;
        }
        catch (Exception e)
        {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseBody.put("error",e.getMessage());

        }
        return new ResponseEntity<>(responseBody, httpStatus);
    }

    @RequestMapping(value = "/projects", method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Project saveProject(@RequestBody Project project)

    {   return projectService.saveProject(project);
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public Project findById(@PathVariable Integer id) {

          return  projectService.findById(id);
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateProject( @PathVariable(value="id") Integer id, @RequestBody Project project)
    {   String name = project.getName();
        HttpStatus httpStatus;
        try {
            System.out.println("changed project");
            projectService.updateProject(id,name);
            httpStatus = HttpStatus.OK;
        }
        catch (Exception e)
        {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(null,httpStatus);
    }
    @RequestMapping(value = "/projects/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProject(@PathVariable(value="id")  Integer id)
    {
        HttpStatus httpStatus;
        try {
            System.out.println("changed project");
            projectService.deleteImage(id);
            httpStatus = HttpStatus.OK;
        }
        catch (Exception e)
        {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }

        return new ResponseEntity<>(null,httpStatus);
    }



}
