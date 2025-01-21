package com.lag.todoapp.todoapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "history")
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "changed_date")
    private LocalDateTime changedDate;

    @Column(name = "changed_field")
    private String field;

    @Column(name = "prev_value")
    private String prevValue;

    @Column(name = "current_value")
    private String currentValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    public HistoryEntity() {
    }

    public HistoryEntity(Long id, LocalDateTime changedDate, String field, String prevValue, String currentValue, UserEntity user, TaskEntity task) {
        this.id = id;
        this.changedDate = changedDate;
        this.field = field;
        this.prevValue = prevValue;
        this.currentValue = currentValue;
        this.user = user;
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getChangedDate() {
        return changedDate;
    }

    public void setChangedDate(LocalDateTime changedDate) {
        this.changedDate = changedDate;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getPrevValue() {
        return prevValue;
    }

    public void setPrevValue(String prevValue) {
        this.prevValue = prevValue;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "HistoryEntity[" +
                "id=" + id +
                ", changedDate=" + changedDate +
                ", field='" + field + '\'' +
                ", prevValue='" + prevValue + '\'' +
                ", currentValue='" + currentValue + '\'' +
                ", user=" + user +
                ", task=" + task +
                ']';
    }
}
