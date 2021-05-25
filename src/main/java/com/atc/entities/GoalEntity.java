package com.atc.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "goals", schema = "shapp", catalog = "")
public class GoalEntity {
    private int id;
    private String code;
    private String name;
    private String description;
    private String keywordEn;
    private String keywordFr;
    private Collection<TrainingPlanEntity> trainingPlansById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code", nullable = false, length = 10)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "keyword_en", nullable = false, length = 100)
    public String getKeywordEn() {
        return keywordEn;
    }

    public void setKeywordEn(String keywordEn) {
        this.keywordEn = keywordEn;
    }

    @Basic
    @Column(name = "keyword_fr", nullable = false, length = 100)
    public String getKeywordFr() {
        return keywordFr;
    }

    public void setKeywordFr(String keywordFr) {
        this.keywordFr = keywordFr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoalEntity that = (GoalEntity) o;
        return id == that.id && Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(keywordEn, that.keywordEn) && Objects.equals(keywordFr, that.keywordFr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, description, keywordEn, keywordFr);
    }

    @OneToMany(mappedBy = "goalsByGoalId")
    public Collection<TrainingPlanEntity> getTrainingPlansById() {
        return trainingPlansById;
    }

    public void setTrainingPlansById(Collection<TrainingPlanEntity> trainingPlansById) {
        this.trainingPlansById = trainingPlansById;
    }
}
