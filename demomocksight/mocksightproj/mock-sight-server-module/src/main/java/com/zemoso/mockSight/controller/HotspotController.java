package com.zemoso.mockSight.controller;


import com.zemoso.mockSight.Modal.Hotspot;
import com.zemoso.mockSight.Modal.Image;
import com.zemoso.mockSight.Service.HotspotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(HotspotController.BASE_URL)
public class HotspotController {

    public static final String BASE_URL = "/mocksight";

    @Autowired
    private HotspotService hotspotService;

    @RequestMapping(value = "/hotspot/{imageId}", method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Hotspot saveHotspot(@RequestBody Hotspot hotspot, @PathVariable(value = "imageId") Image imageId)

    {   return hotspotService.save(hotspot,imageId);
    }

    @GetMapping(path = "/hotspot/{Id}")
    @ResponseBody
    public ResponseEntity<Map> fetchHotspots(@PathVariable(value = "Id") Image Id)
    {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        Map responseBody = new HashMap();

        try
        {
            List hotspots = hotspotService.getHotspots(Id);
            responseBody.put("Hotspots",hotspots);
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