package com.lag.todoapp.todoapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tasks_labels",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private Set<LabelEntity> labels = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "priority_id")
    private PriorityEntity priority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private StatusEntity status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tasks_comments",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id")
    )
    private Set<CommentEntity> comments = new HashSet<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public TaskEntity() {
    }

    public TaskEntity(Long id, String title, String description, Set<LabelEntity> labels, PriorityEntity priority, StatusEntity status, UserEntity user, Set<CommentEntity> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.labels = labels;
        this.priority = priority;
        this.status = status;
        this.user = user;
        this.comments = comments;
    }

    public TaskEntity(Long id, String title, String description, Set<LabelEntity> labels, PriorityEntity priority, StatusEntity status, UserEntity user, Set<CommentEntity> comments, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.labels = labels;
        this.priority = priority;
        this.status = status;
        this.user = user;
        this.comments = comments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<LabelEntity> getLabels() {
        return labels;
    }

    public void setLabels(Set<LabelEntity> labels) {
        this.labels = labels;
    }

    public PriorityEntity getPriority() {
        return priority;
    }

    public void setPriority(PriorityEntity priority) {
        this.priority = priority;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(Set<CommentEntity> comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "TaskEntity[" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", labels=" + labels +
                ", priority=" + priority +
                ", status=" + status +
                ", user=" + user +
                ", comments=" + comments +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ']';
    }
}
