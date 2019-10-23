package com.netflix.demo.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name ="suggestions")
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User owner;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    public Suggestion(){}

    public interface Create{}

    public interface Update{}
}
