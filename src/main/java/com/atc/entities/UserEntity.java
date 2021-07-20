package com.atc.entities;

import javax.persistence.*;
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
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDateTime birthdate;
    private String gender;
    private String emailAddress;
    private String phoneNumber;
    private LocalDateTime creationDateTime;
    private String pictureUri;
    private boolean isActive;
    private String coachDegreeInfo;
    private LocalDateTime coachCareerStartDate;
    private Collection<EventCommentEntity> eventCommentsById;
    private Collection<EventParticipationEntity> eventParticipationsById;
    private Collection<EventParticipationEntity> eventParticipationsById_0;
    private Collection<EventPostEntity> eventPostsById;
    private Collection<EventEntity> eventsById;
    private Collection<MeasureEntity> measuresById;
    private Collection<ScheduleLinkEntity> schedulesLinksById;
    private Collection<TeamCommentEntity> teamCommentsById;
    private Collection<TeamPostEntity> teamPostsById;
    private Collection<TeamEntity> teamsById;
    private Collection<TrainingPlanEntity> trainingPlansById;
    private Collection<UserAddressEntity> usersAddressesById;
    private RoleEntity rolesByRoleId;
    private Collection<UserOrganizationEntity> usersOrganizationsById;
    private Collection<UserSubscriptionEntity> usersSubscriptionsById;
    private Collection<UsersTeamEntity> usersTeamsById;
    private Collection<WorkoutPlaceEntity> workoutPlacesById;

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
    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    @Basic
    @Column(name = "gender", nullable = false)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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
    @Column(name = "picture_URI", nullable = true, length = 2083)
    public String getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(String pictureUri) {
        this.pictureUri = pictureUri;
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
    public LocalDateTime getCoachCareerStartDate() {
        return coachCareerStartDate;
    }

    public void setCoachCareerStartDate(LocalDateTime coachCareerStartDate) {
        this.coachCareerStartDate = coachCareerStartDate;
    }

    @OneToMany(mappedBy = "usersByCreatorUserId")
    public Collection<EventCommentEntity> getEventCommentsById() {
        return eventCommentsById;
    }

    public void setEventCommentsById(Collection<EventCommentEntity> eventCommentsById) {
        this.eventCommentsById = eventCommentsById;
    }

    @OneToMany(mappedBy = "usersByInviterUserId")
    public Collection<EventParticipationEntity> getEventParticipationsById() {
        return eventParticipationsById;
    }

    public void setEventParticipationsById(Collection<EventParticipationEntity> eventParticipationsById) {
        this.eventParticipationsById = eventParticipationsById;
    }

    @OneToMany(mappedBy = "usersByInviteeUserId")
    public Collection<EventParticipationEntity> getEventParticipationsById_0() {
        return eventParticipationsById_0;
    }

    public void setEventParticipationsById_0(Collection<EventParticipationEntity> eventParticipationsById_0) {
        this.eventParticipationsById_0 = eventParticipationsById_0;
    }

    @OneToMany(mappedBy = "usersByCreatorUserId")
    public Collection<EventPostEntity> getEventPostsById() {
        return eventPostsById;
    }

    public void setEventPostsById(Collection<EventPostEntity> eventPostsById) {
        this.eventPostsById = eventPostsById;
    }

    @OneToMany(mappedBy = "usersByCreatorUserId")
    public Collection<EventEntity> getEventsById() {
        return eventsById;
    }

    public void setEventsById(Collection<EventEntity> eventsById) {
        this.eventsById = eventsById;
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

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<UserAddressEntity> getUsersAddressesById() {
        return usersAddressesById;
    }

    public void setUsersAddressesById(Collection<UserAddressEntity> usersAddressesById) {
        this.usersAddressesById = usersAddressesById;
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
    public Collection<UserOrganizationEntity> getUsersOrganizationsById() {
        return usersOrganizationsById;
    }

    public void setUsersOrganizationsById(Collection<UserOrganizationEntity> usersOrganizationsById) {
        this.usersOrganizationsById = usersOrganizationsById;
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

    @OneToMany(mappedBy = "usersByCreatorUserId")
    public Collection<WorkoutPlaceEntity> getWorkoutPlacesById() {
        return workoutPlacesById;
    }

    public void setWorkoutPlacesById(Collection<WorkoutPlaceEntity> workoutPlacesById) {
        this.workoutPlacesById = workoutPlacesById;
    }
}
