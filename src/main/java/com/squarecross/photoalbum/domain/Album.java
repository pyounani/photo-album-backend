package com.squarecross.photoalbum.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate createdAt;

}

