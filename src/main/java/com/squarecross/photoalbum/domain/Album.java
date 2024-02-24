package com.squarecross.photoalbum.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="album", schema="photo_album", uniqueConstraints = {@UniqueConstraint(columnNames = "album_id")})
@Getter
public class Album {
    @Id
    @GeneratedValue
    @Column(name = "album_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "album_name", unique = false, nullable = false)
    private String name;

    @Column(name = "create_at", unique = false, nullable = false)
    @CreationTimestamp
    private LocalDate createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "album", cascade = CascadeType.ALL)
    private List<Photo> photos;

    public void setName(String name) {
        this.name = name;
    }
}

