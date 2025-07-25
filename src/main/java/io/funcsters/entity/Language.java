package io.funcsters.entity;

import jakarta.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name; // e.g., "Java", "Python", "C++"

    @Column(nullable = false)
    private String judge0Identifier; // The language ID used by Judge0 (e.g., "java", "python3")

    @ManyToMany(mappedBy = "supportedLanguages")
    private Set<Challenge> challenges;
}