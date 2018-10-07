package com.zemoso.mockSight.Modal;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "hotspot")
public class Hotspot {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String linkto;

    public Long getX1() {
        return x1;
    }

    public void setX1(Long x1) {
        this.x1 = x1;
    }

    public Long getY1() {
        return y1;
    }

    public void setY1(Long y1) {
        this.y1 = y1;
    }

    public Long getX2() {
        return x2;
    }

    public void setX2(Long x2) {
        this.x2 = x2;
    }

    public Long getY2() {
        return y2;
    }

    public void setY2(Long y2) {
        this.y2 = y2;
    }

    private Long x1;
    private Long y1;
    private Long x2;
    private Long y2;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "image_id" ,nullable= false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Image image;


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getLinkto() {
        return linkto;
    }

    public void setLinkto(String linkto) {
        this.linkto = linkto;
    }
}