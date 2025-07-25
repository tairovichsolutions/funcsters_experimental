package io.funcsters.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "oauth_provider", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "provider"})
})
public class OAuthProvider {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProviderType provider; // GOOGLE, GITHUB, LINKEDIN

    @Column(nullable = false, unique = true)
    private String providerUserId; // The unique user ID from the provider
}