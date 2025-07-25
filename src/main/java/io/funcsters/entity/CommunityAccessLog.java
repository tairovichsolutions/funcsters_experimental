package io.funcsters.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "community_access_log", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "challenge_id", "language"})
})
public class CommunityAccessLog {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private LocalDateTime viewedAt;

    @PrePersist
    public void onCreate() {
        this.viewedAt = LocalDateTime.now();
    }
}