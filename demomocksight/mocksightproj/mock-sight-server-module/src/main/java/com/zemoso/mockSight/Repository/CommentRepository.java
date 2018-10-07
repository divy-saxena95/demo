package com.zemoso.mockSight.Repository;


import com.zemoso.mockSight.Modal.Comment;
import com.zemoso.mockSight.Modal.Image;
import com.zemoso.mockSight.Modal.Comment;
import com.zemoso.mockSight.Modal.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select comment from Comment comment,Image img where img.project = :id and comment.image = img.id order by comment.date DESC")
    List<Comment> fetchComments(@Param("id") Project id);

    @Query("select comment from Comment comment where comment.image = :image order by comment.date DESC")
    List<Comment> findByImage(@Param("image") Image image);

}
