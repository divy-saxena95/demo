package com.zemoso.mockSight.controller;


import com.zemoso.mockSight.Modal.Comment;
import com.zemoso.mockSight.Modal.Project;
import com.zemoso.mockSight.Repository.ProjectRepository;
import com.zemoso.mockSight.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(CommentController.BASE_URL)
public class CommentController {

    public static final String BASE_URL = "/mocksight";

    @Autowired
    private CommentService commentService;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping(path = "/projects/{id}/comment")
    @ResponseBody
    public ResponseEntity<Map> getComments(@PathVariable(value = "id") Project id)
    {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        Map responseBody = new HashMap();
        try
        {
            List comments = commentService.getComments(id);
            responseBody.put("Comments",comments);
            httpStatus = HttpStatus.OK;
        }
        catch (Exception e)
        {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseBody.put("error",e.getMessage());

        }
        return new ResponseEntity<>(responseBody, httpStatus);
    }


    @RequestMapping(value = "/projects/{id}/images/{imageId}/comment", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity addComment(@PathVariable(value="id") Project id,
                                        @PathVariable(value ="imageId") Long imageId,
                                        @RequestBody Comment comment)
    {
        System.out.println("controller");
        HttpStatus httpStatus;
        try{
             commentService.addComment(id,imageId,comment);
             httpStatus = HttpStatus.OK;
           }
        catch (Exception e)
        {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(null,httpStatus);
    }

    @GetMapping(path = "/projects/{id}/images/{imageId}/comment")
    @ResponseBody
    public ResponseEntity<Map> getCommentsOfImage(@PathVariable(value = "id") Project id,
                                                  @PathVariable(value = "imageId") Long imageId)
    {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        Map responseBody = new HashMap();
        try
        {
            List comments = commentService.getCommentsOfImage(id,imageId);
            responseBody.put("Comments",comments);
            httpStatus = HttpStatus.OK;
        }
        catch (Exception e)
        {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseBody.put("error",e.getMessage());

        }
        return new ResponseEntity<>(responseBody, httpStatus);
    }

}
