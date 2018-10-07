package com.zemoso.mockSight.Service;

import com.zemoso.mockSight.Modal.Hotspot;
import com.zemoso.mockSight.Modal.Image;
import com.zemoso.mockSight.Repository.HotspotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class HotspotService {

    @Autowired
    private HotspotRepository hotspotRepository;

    public Hotspot save(Hotspot hotspot, Image image) {
        hotspot.setImage(image);
        return hotspotRepository.save(hotspot);
    }

    @Transactional
    public List<Hotspot> getHotspots(Image id)

    {
        return hotspotRepository.getHotspotsByImageId(id);
    }


}