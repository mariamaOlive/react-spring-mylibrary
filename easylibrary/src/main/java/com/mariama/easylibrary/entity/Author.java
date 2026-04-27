package com.mariama.easylibrary.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "AUTHORS")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", length = 250)
    private String name;

    @Column(name = "BIO", length = 1000)
    private String bio;

    @Column(name = "IMAGE_NAME", length = 250)
    private String imageName;
}
