package com.mariama.easylibrary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOKS")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID", nullable = false)
    private Long id;

    @Column (name = "TITLE", nullable = false, length = 250)
    private String title;

    @Column (name = "ISBN", nullable = false, length = 250)
    private String isbn;

    @Column (name = "PUBLISHED_YEAR", nullable = false)
    private Integer publishedYear;

    @Column (name = "IMAGE_NAME", nullable = false, length = 500)
    private String imageName;

    @ManyToOne
    @JoinColumn (name = "AUTHOR_ID")
    private Author author;

}

