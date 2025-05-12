package com.amit.devtracker.domain.entities;


import com.amit.devtracker.domain.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String password;

    // Automatically sets creation time when user is first saved
    @CreationTimestamp
    @Column (nullable = false)
    private LocalDateTime createdAt;

    // Store role enum as string (e.g. "USER", "ADMIN") instead of ordinal
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false) // for enabling users (in the future)
    private boolean enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Roadmap> roadmaps = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> userTasks = new ArrayList<>();

}
