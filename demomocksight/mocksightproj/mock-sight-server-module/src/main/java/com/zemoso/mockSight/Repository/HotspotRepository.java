package com.zemoso.mockSight.Repository;

import com.zemoso.mockSight.Modal.Hotspot;
import com.zemoso.mockSight.Modal.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotspotRepository extends JpaRepository<Hotspot,Long> {

    @Query("select hotspot from Hotspot hotspot where hotspot.image = :id ")
    List<Hotspot> getHotspotsByImageId(@Param("id") Image id);



}
