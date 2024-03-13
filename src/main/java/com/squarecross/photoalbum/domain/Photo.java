package com.squarecross.photoalbum.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "photo", schema = "photo_album", uniqueConstraints = {@UniqueConstraint(columnNames = "photo_id")})
@Getter
@Setter
public class Photo {

    @Id
    @GeneratedValue
    @Column(name = "photo_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "file_name", unique = false, nullable = true)
    private String fileName;

    @Column(name = "thumb_url", unique = false, nullable = true)
    private String thumbUrl;

    @Column(name = "original_url", unique = false, nullable = true)
    private String originalUrl;

    @Column(name = "file_size", unique = false, nullable = true)
    private int fileSize;

    @Column(name = "uploaded_at", unique = false, nullable = true)
    @CreatedDate
    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

}
