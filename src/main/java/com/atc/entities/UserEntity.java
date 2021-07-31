package com.atc.entities;

import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "shapp", catalog = "")
@NamedQueries(
        value = {
                @NamedQuery(name = "User.findAll",
                        query = "SELECT u from UserEntity u"),
                @NamedQuery(name = "User.findOneByUsername",
                        query = "SELECT u from UserEntity u where u.username= :username")
        }

)
public class UserEntity {
    @Positive
    private int id;

    @NotBlank
    @NotNull
    @Size(min=1, max=100)
    private String firstName;

    @NotBlank
    @NotNull
    @Size(min=1, max=100)
    private String lastName;

    @NotBlank
    @NotNull
    @Size(min=1, max=100)
    private String username;

    @NotBlank
    @NotNull
    @Size(min=1, max=255)
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,255})")
    private String password;

    @NotNull
    @PastOrPresent
    private LocalDate birthdate;

    @NotNull
    private Gender gender;

    @NotNull
    @NotBlank
    @Size(min=3, max=254)
    @Email(regexp = "[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]")
    private String emailAddress;

    @NotNull
    @NotBlank
    @Size(min=8, max=16)
    @Pattern(regexp = "^([+]?[\\d]+)?$")
    private String phoneNumber;

    @NotNull
    @PastOrPresent
    private LocalDateTime creationDateTime;

    @NotNull
    private boolean isActive;

    @Nullable
    private String coachDegreeInfo;

    @PastOrPresent
    private LocalDate coachCareerStartDate;
    private Collection<MeasureEntity> measuresById;
    private Collection<ScheduleLinkEntity> schedulesLinksById;
    private Collection<TeamCommentEntity> teamCommentsById;
    private Collection<TeamPostEntity> teamPostsById;
    private Collection<TeamEntity> teamsById;
    private Collection<TrainingPlanEntity> trainingPlansById;
    private RoleEntity rolesByRoleId;
    private Collection<UserSubscriptionEntity> usersSubscriptionsById;
    private Collection<UsersTeamEntity> usersTeamsById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 100)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 100)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 100)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "birthdate", nullable = false)
    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "email_address", nullable = false, length = 255)
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Basic
    @Column(name = "phone_number", nullable = false, length = 100)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "creation_date_time", nullable = false)
    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }


    @Basic
    @Column(name = "active", nullable = false)
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Basic
    @Column(name = "coach_degree_info", nullable = true, length = -1)
    public String getCoachDegreeInfo() {
        return coachDegreeInfo;
    }

    public void setCoachDegreeInfo(String coachDegreeInfo) {
        this.coachDegreeInfo = coachDegreeInfo;
    }

    @Basic
    @Column(name = "coach_career_start_date", nullable = true)
    public LocalDate getCoachCareerStartDate() {
        return coachCareerStartDate;
    }

    public void setCoachCareerStartDate(LocalDate coachCareerStartDate) {
        this.coachCareerStartDate = coachCareerStartDate;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<MeasureEntity> getMeasuresById() {
        return measuresById;
    }

    public void setMeasuresById(Collection<MeasureEntity> measuresById) {
        this.measuresById = measuresById;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<ScheduleLinkEntity> getSchedulesLinksById() {
        return schedulesLinksById;
    }

    public void setSchedulesLinksById(Collection<ScheduleLinkEntity> schedulesLinksById) {
        this.schedulesLinksById = schedulesLinksById;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<TeamCommentEntity> getTeamCommentsById() {
        return teamCommentsById;
    }

    public void setTeamCommentsById(Collection<TeamCommentEntity> teamCommentsById) {
        this.teamCommentsById = teamCommentsById;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<TeamPostEntity> getTeamPostsById() {
        return teamPostsById;
    }

    public void setTeamPostsById(Collection<TeamPostEntity> teamPostsById) {
        this.teamPostsById = teamPostsById;
    }

    @OneToMany(mappedBy = "usersByUserCreatorId")
    public Collection<TeamEntity> getTeamsById() {
        return teamsById;
    }

    public void setTeamsById(Collection<TeamEntity> teamsById) {
        this.teamsById = teamsById;
    }

    @OneToMany(mappedBy = "usersByUserIdCreator")
    public Collection<TrainingPlanEntity> getTrainingPlansById() {
        return trainingPlansById;
    }

    public void setTrainingPlansById(Collection<TrainingPlanEntity> trainingPlansById) {
        this.trainingPlansById = trainingPlansById;
    }

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    public RoleEntity getRolesByRoleId() {
        return rolesByRoleId;
    }

    public void setRolesByRoleId(RoleEntity rolesByRoleId) {
        this.rolesByRoleId = rolesByRoleId;
    }


    @OneToMany(mappedBy = "usersByUserId")
    public Collection<UserSubscriptionEntity> getUsersSubscriptionsById() {
        return usersSubscriptionsById;
    }

    public void setUsersSubscriptionsById(Collection<UserSubscriptionEntity> usersSubscriptionsById) {
        this.usersSubscriptionsById = usersSubscriptionsById;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<UsersTeamEntity> getUsersTeamsById() {
        return usersTeamsById;
    }

    public void setUsersTeamsById(Collection<UsersTeamEntity> usersTeamsById) {
        this.usersTeamsById = usersTeamsById;
    }
}
