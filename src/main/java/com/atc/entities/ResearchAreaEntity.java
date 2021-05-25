package com.atc.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "research_areas", schema = "shapp", catalog = "")
public class ResearchAreaEntity {
    private int id;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResearchAreaEntity that = (ResearchAreaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
