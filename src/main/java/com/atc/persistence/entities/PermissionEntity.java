package com.atc.entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * @author axel
 */
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
@SessionScoped
@Named
public class PermissionEntity implements Serializable {
    @Positive
    private int id;

    @NotBlank
    @NotNull
    @Size(min=1, max=100)
    private String label;

    @NotBlank
    @NotNull
    @Size(min=1, max=2000)
    private String description;


    private Collection<RolePermissionEntity> rolesPermissionsById;

    public PermissionEntity(@Positive int id, @NotBlank @NotNull @Size(min = 1, max = 100) String label, @NotBlank @NotNull @Size(min = 1, max = 2000) String description) {
        this.id = id;
        this.label = label;
        this.description = description;
    }

    public PermissionEntity() {

    }

    public PermissionEntity(@NotBlank @NotNull @Size(min = 1, max = 100) String label, @NotBlank @NotNull @Size(min = 1, max = 2000) String description) {
        this.label = label;
        this.description = description;
    }

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

/*    @OneToMany(mappedBy = "permissionsByPermissionId")
    public Collection<RolePermissionEntity> getRolesPermissionsById() {
        return rolesPermissionsById;
    }

    public void setRolesPermissionsById(Collection<RolePermissionEntity> rolesPermissionsById) {
        this.rolesPermissionsById = rolesPermissionsById;
    }*/
}
