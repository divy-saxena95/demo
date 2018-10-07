package com.zemoso.mockSight.Service;

import com.zemoso.mockSight.Modal.Comment;
import com.zemoso.mockSight.Modal.Image;
import com.zemoso.mockSight.Modal.Project;
import com.zemoso.mockSight.Repository.CommentRepository;
import com.zemoso.mockSight.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;



import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ImageRepository imageRepository;

    public List<Comment> getComments(Project project)
    {
       return commentRepository.fetchComments(project);
    }

    @Transactional
    public void addComment(Project id, Long imageId, Comment comment)
    {
        System.out.println("service");
        Image image = imageRepository.findByIdAndProject(imageId,id).get();
        comment.setImage(image);
        LocalDateTime date = LocalDateTime.now();
        comment.setDate(date);
        commentRepository.save(comment);
    }

    @Transactional
    public List<Comment> getCommentsOfImage(Project project, Long id) {
        Image img = imageRepository.findByIdAndProject(id, project).get();
        return commentRepository.findByImage(img);
    }

}
