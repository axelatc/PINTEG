package com.atc.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles_permissions", schema = "shapp", catalog = "")
public class RolePermissionEntity {
    private int id;
    private RoleEntity rolesByRoleId;
    private PermissionEntity permissionsByPermissionId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        RolePermissionEntity that = (RolePermissionEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    public RoleEntity getRolesByRoleId() {
        return rolesByRoleId;
    }

    public void setRolesByRoleId(RoleEntity rolesByRoleId) {
        this.rolesByRoleId = rolesByRoleId;
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
