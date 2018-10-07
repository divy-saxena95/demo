package com.zemoso.mockSight.controller;


import com.zemoso.mockSight.Modal.Image;
import com.zemoso.mockSight.Modal.Project;
import com.zemoso.mockSight.Service.ImageService;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ImageController.BASE_URL)
@Configuration
@PropertySource("classpath:application.properties")
public class ImageController {
    public static final String BASE_URL = "/mocksight";

    @Value("${upload.file.directory}")
    private String uploadDirectory;
    @Autowired
    private ImageService imageService;

    @RequestMapping(value="/projects/{id}/images", method= RequestMethod.POST)
    String fileUploads( HttpServletRequest request, @PathVariable("id") Project id) {
        MultipartHttpServletRequest mRequest;
        mRequest = (MultipartHttpServletRequest) request;
        mRequest.getParameterMap();
        Iterator<String> itr = mRequest.getFileNames();


        try {
            while (itr.hasNext()){
                MultipartFile file=((MultipartHttpServletRequest)request).getFile(itr.next());
                Image uploadedFile = imageService.uploadFile(file, uploadDirectory);
                uploadedFile.setProject(id);
                LocalDateTime dateTime = LocalDateTime.now();
                uploadedFile.setDateTime(dateTime);
                uploadedFile.setIndex(1L);
               Image imageId =  imageService.save(uploadedFile);
                 uploadedFile.setIndex(imageId.getId());

                 imageService.save(uploadedFile);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Image Uploaded Successfully";
    }


    /**
     * Api for retrieving all images of an product
     * @return map either with images or with error string
     */
    @GetMapping(path = "/projects/{id}/images")
    @ResponseBody
    public ResponseEntity<Map> fetchImages(@PathVariable(value = "id") Project id)
    {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        Map responseBody = new HashMap();

        try
        {
            List images = imageService.getImages(id);
            responseBody.put("Images",images);
            httpStatus = HttpStatus.OK;
        }
        catch (Exception e)
        {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseBody.put("error",e.getMessage());

        }
        return new ResponseEntity<>(responseBody, httpStatus);
    }


    @RequestMapping(value = "images/{imagename}", method = RequestMethod.GET,
            produces = MediaType.ALL_VALUE)

    public void getImage(HttpServletResponse response,@PathVariable(value="imagename") String imagename) throws IOException {


        File file = new File(uploadDirectory+"/"+imagename);
        String absolutePath = file.getAbsolutePath();
        response.setContentType(MediaType.ALL_VALUE);
        StreamUtils.copy(new FileInputStream(file), response.getOutputStream());



    }

    @RequestMapping(value = "/projects/{projectId}/images/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateImage( @PathVariable(value="id") Long id, @RequestBody Image image)
    {   String name = image.getName();
        Long index = image.getIndex();
        HttpStatus httpStatus;
        try {
            System.out.println("changed project");
            imageService.updateImage(id,name,index);
            httpStatus = HttpStatus.OK;
        }
        catch (Exception e)
        {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(null,httpStatus);
    }

    @RequestMapping(value = "/projects/{projectId}/images/{id}", method = RequestMethod.DELETE)
    public ResponseEntity updateImage(@PathVariable(value="projectId") Project project,
                                      @PathVariable(value="id")  Long id)
    {
        HttpStatus httpStatus;
        try {
            System.out.println("changed project");
            imageService.deleteImage(id,project);
            httpStatus = HttpStatus.OK;
        }
        catch (Exception e)
        {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }

        return new ResponseEntity<>(null,httpStatus);
    }

    @GetMapping(path = "/projects/{id}/images/{imageId}")
    @ResponseBody
    public Image fetchImages(@PathVariable(value = "imageId") Long imageId,
                             @PathVariable( value = "id") Project id)
    {
        Image image = imageService.getImage(id,imageId);
        return image;
    }


}
