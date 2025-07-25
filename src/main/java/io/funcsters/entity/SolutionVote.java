package io.funcsters.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "solution_vote", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "community_solution_id"})
})
public class SolutionVote {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "community_solution_id", nullable = false)
    private CommunitySolution solution;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoteType voteType;
}