package com.atc.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "groups_permissions", schema = "shapp", catalog = "")
public class GroupPermissionEntity {
    private int id;
    private GroupEntity groupsByGroupId;
    private PermissionEntity permissionsByPermissionId;

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
        GroupPermissionEntity that = (GroupPermissionEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    public GroupEntity getGroupsByGroupId() {
        return groupsByGroupId;
    }

    public void setGroupsByGroupId(GroupEntity groupsByGroupId) {
        this.groupsByGroupId = groupsByGroupId;
    }

    @ManyToOne
    @JoinColumn(name = "permission_id", referencedColumnName = "id")
    public PermissionEntity getPermissionsByPermissionId() {
        return permissionsByPermissionId;
    }

    public void setPermissionsByPermissionId(PermissionEntity permissionsByPermissionId) {
        this.permissionsByPermissionId = permissionsByPermissionId;
    }
}
