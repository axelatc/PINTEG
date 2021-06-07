package com.atc.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "permissions", schema = "shapp", catalog = "")
@NamedQueries(
        value = {
                @NamedQuery(name = "Permission.findAll",
                        query = "SELECT p from PermissionEntity p"),
                @NamedQuery(name = "Permission.findOneByLabel",
                        query = "SELECT p from PermissionEntity p where p.label= :label")
        }

)
public class PermissionEntity {
    private int id;
    private String label;
    private String description;
    private Collection<GroupPermissionEntity> groupsPermissionsById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Basic
    @Column(name = "label", nullable = false, length = 100)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Basic
    @Column(name = "description", nullable = false, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionEntity that = (PermissionEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(label, that.label) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, description);
    }

    @OneToMany(mappedBy = "permissionsByPermissionId")
    public Collection<GroupPermissionEntity> getGroupsPermissionsById() {
        return groupsPermissionsById;
    }

    public void setGroupsPermissionsById(Collection<GroupPermissionEntity> groupsPermissionsById) {
        this.groupsPermissionsById = groupsPermissionsById;
    }
}
