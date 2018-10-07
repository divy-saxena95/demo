package com.zemoso.mockSight.Repository;

import com.zemoso.mockSight.Modal.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer> {

    @Query("select p.id ,p.name ,DATE_FORMAT(p.creationDate, '%b %e, %Y') from Project p order by p.creationDate DESC")
    List<Project> getAllProjects();

    void deleteById(Integer id);

    Project findById(Long id);
}
