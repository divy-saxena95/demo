package com.zemoso.mockSight.Service;

import com.zemoso.mockSight.Modal.Project;
import com.zemoso.mockSight.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getProjects() {
        return projectRepository.getAllProjects();
    }

    @Transactional
    public void deleteImage(Integer id)
    {
        projectRepository.deleteById(id);
    }

    public Project getProject(Integer projectId){
        return projectRepository.findById(projectId).get();
    }

    public Project saveProject(Project project) {
        LocalDateTime localDateTime = LocalDateTime.now();
        project.setCreationDate(localDateTime);
        return projectRepository.save(project);

    }

    public Project findById(Integer id)
    {
       return projectRepository.findById(id).get();
    }

    public void updateProject(Integer id, String name)
    {   Project project = projectRepository.findById(id).get();
        project.setName(name);
        projectRepository.save(project);
    }
}
