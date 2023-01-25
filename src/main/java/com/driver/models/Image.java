package com.driver.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "image")
@Data
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private String dimensions;


    @ManyToOne
    @JoinColumn
    private Blog blog;
}