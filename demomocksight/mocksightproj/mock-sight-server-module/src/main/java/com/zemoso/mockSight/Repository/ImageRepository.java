package com.zemoso.mockSight.Repository;

import com.zemoso.mockSight.Modal.Image;
import com.zemoso.mockSight.Modal.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("select img from Image img where img.project = :id order by img.imageIndex ASC")
    List<Image> getImagesByProjectId(@Param("id") Project id);

    void deleteByIdAndProject(Long id,Project project);

    List<Image> findAll();

    Optional<Image> findByIdAndProject(Long id, Project project);
}
