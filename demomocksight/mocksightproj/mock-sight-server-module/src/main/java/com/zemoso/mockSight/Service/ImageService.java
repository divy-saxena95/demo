package com.zemoso.mockSight.Service;

import com.zemoso.mockSight.Modal.Image;
import com.zemoso.mockSight.Modal.Project;
import com.zemoso.mockSight.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image uploadFile(MultipartFile file, String uploadDirectory)
            throws IOException {
        String fileName = file.getOriginalFilename();
        Path path = Paths.get(uploadDirectory, fileName);
        Files.copy(file.getInputStream(), path);
        return new Image(uploadDirectory, fileName);
    }

    public Image save(Image uploadedFile) {
        return imageRepository.save(uploadedFile);
    }

    @Transactional
    public List<Image> getImages(Project id)
    {
        return imageRepository.getImagesByProjectId(id);
    }

    public void updateImage(Long id, String name, Long index)
    {
        Image image = imageRepository.findById(id).get();
        image.setIndex(index);
        image.setName(name);
        imageRepository.save(image);
    }

    @Transactional
    public void deleteImage(Long id, Project project)
    {
        imageRepository.deleteByIdAndProject(id,project);
    }

    @Transactional
    public Image getImage(Project project, Long imageId)
    {
        return imageRepository.findByIdAndProject(imageId,project).get();
    }
}
