package com.atc.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "organizations", schema = "shapp", catalog = "")
public class OrganizationEntity {
    private int id;
    private String name;
    private String description;
    private Collection<EventEntity> eventsById;
    private AddressEntity addressesByAddressId;
    private Collection<UserOrganizationEntity> usersOrganizationsById;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationEntity that = (OrganizationEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @OneToMany(mappedBy = "organizationsByOrganizationId")
    public Collection<EventEntity> getEventsById() {
        return eventsById;
    }

    public void setEventsById(Collection<EventEntity> eventsById) {
        this.eventsById = eventsById;
    }

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    public AddressEntity getAddressesByAddressId() {
        return addressesByAddressId;
    }

    public void setAddressesByAddressId(AddressEntity addressesByAddressId) {
        this.addressesByAddressId = addressesByAddressId;
    }

    @OneToMany(mappedBy = "organizationsByOrganizationId")
    public Collection<UserOrganizationEntity> getUsersOrganizationsById() {
        return usersOrganizationsById;
    }

    public void setUsersOrganizationsById(Collection<UserOrganizationEntity> usersOrganizationsById) {
        this.usersOrganizationsById = usersOrganizationsById;
    }
}
