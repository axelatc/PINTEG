package com.atc.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users_organizations", schema = "shapp", catalog = "")
public class UserOrganizationEntity {
    private int id;
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
    private boolean obsoleteFlag;
    private UserEntity usersByUserId;
    private OrganizationEntity organizationsByOrganizationId;

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
    @Column(name = "valid_from", nullable = false)
    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    @Basic
    @Column(name = "valid_until", nullable = false)
    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }

    @Basic
    @Column(name = "obsolete_flag", nullable = false)
    public boolean isObsoleteFlag() {
        return obsoleteFlag;
    }

    public void setObsoleteFlag(boolean obsoleteFlag) {
        this.obsoleteFlag = obsoleteFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserOrganizationEntity that = (UserOrganizationEntity) o;
        return obsoleteFlag == that.obsoleteFlag && Objects.equals(id, that.id) && Objects.equals(validFrom, that.validFrom) && Objects.equals(validUntil, that.validUntil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, validFrom, validUntil, obsoleteFlag);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public UserEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UserEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    public OrganizationEntity getOrganizationsByOrganizationId() {
        return organizationsByOrganizationId;
    }

    public void setOrganizationsByOrganizationId(OrganizationEntity organizationsByOrganizationId) {
        this.organizationsByOrganizationId = organizationsByOrganizationId;
    }
}
