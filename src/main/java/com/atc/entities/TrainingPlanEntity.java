package com.atc.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * @author william
 * temporaire: fonctionnalités à redéfinir
 */
@Entity
@Table(name = "training_plans", schema = "shapp", catalog = "")
public class TrainingPlanEntity {
    private int id;
    private String name;
    private String description;
    private String accessRange;
    private double durationEstimation;
    private double caloriesEstimation;
    private Collection<ScheduleLinkEntity> schedulesLinksById;
    private LevelEntity levelsByLevelId;
    private GoalEntity goalsByGoalId;
    private TrainingTypeEntity trainingTypesByTrainingTypeId;
    private SubscriptionEntity subscriptionsBySubscriptionIdMinimumRank;
    private UserEntity usersByUserIdCreator;
    private Collection<TrainingCategoryLinkEntity> trainingsCategoriesLinksById;
    private Collection<TrainingExerciseLinkEntity> trainingsExercisesLinksById;

    @Id
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

    @Basic
    @Column(name = "access_range", nullable = false)
    public String getAccessRange() {
        return accessRange;
    }

    public void setAccessRange(String accessRange) {
        this.accessRange = accessRange;
    }

    @Basic
    @Column(name = "duration_estimation", nullable = false, precision = 0)
    public double getDurationEstimation() {
        return durationEstimation;
    }

    public void setDurationEstimation(double durationEstimation) {
        this.durationEstimation = durationEstimation;
    }

    @Basic
    @Column(name = "calories_estimation", nullable = false, precision = 0)
    public double getCaloriesEstimation() {
        return caloriesEstimation;
    }

    public void setCaloriesEstimation(double caloriesEstimation) {
        this.caloriesEstimation = caloriesEstimation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingPlanEntity that = (TrainingPlanEntity) o;
        return id == that.id && Double.compare(that.durationEstimation, durationEstimation) == 0 && Double.compare(that.caloriesEstimation, caloriesEstimation) == 0 && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(accessRange, that.accessRange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, accessRange, durationEstimation, caloriesEstimation);
    }

    @OneToMany(mappedBy = "trainingPlansByTrainingPlanId")
    public Collection<ScheduleLinkEntity> getSchedulesLinksById() {
        return schedulesLinksById;
    }

    public void setSchedulesLinksById(Collection<ScheduleLinkEntity> schedulesLinksById) {
        this.schedulesLinksById = schedulesLinksById;
    }

    @ManyToOne
    @JoinColumn(name = "level_id", referencedColumnName = "id")
    public LevelEntity getLevelsByLevelId() {
        return levelsByLevelId;
    }

    public void setLevelsByLevelId(LevelEntity levelsByLevelId) {
        this.levelsByLevelId = levelsByLevelId;
    }

    @ManyToOne
    @JoinColumn(name = "goal_id", referencedColumnName = "id")
    public GoalEntity getGoalsByGoalId() {
        return goalsByGoalId;
    }

    public void setGoalsByGoalId(GoalEntity goalsByGoalId) {
        this.goalsByGoalId = goalsByGoalId;
    }

    @ManyToOne
    @JoinColumn(name = "training_type_id", referencedColumnName = "id")
    public TrainingTypeEntity getTrainingTypesByTrainingTypeId() {
        return trainingTypesByTrainingTypeId;
    }

    public void setTrainingTypesByTrainingTypeId(TrainingTypeEntity trainingTypesByTrainingTypeId) {
        this.trainingTypesByTrainingTypeId = trainingTypesByTrainingTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "subscription_id_minimum_rank", referencedColumnName = "id")
    public SubscriptionEntity getSubscriptionsBySubscriptionIdMinimumRank() {
        return subscriptionsBySubscriptionIdMinimumRank;
    }

    public void setSubscriptionsBySubscriptionIdMinimumRank(SubscriptionEntity subscriptionsBySubscriptionIdMinimumRank) {
        this.subscriptionsBySubscriptionIdMinimumRank = subscriptionsBySubscriptionIdMinimumRank;
    }

    @ManyToOne
    @JoinColumn(name = "user_id_creator", referencedColumnName = "id")
    public UserEntity getUsersByUserIdCreator() {
        return usersByUserIdCreator;
    }

    public void setUsersByUserIdCreator(UserEntity usersByUserIdCreator) {
        this.usersByUserIdCreator = usersByUserIdCreator;
    }

    @OneToMany(mappedBy = "trainingPlansByTrainingPlanId")
    public Collection<TrainingCategoryLinkEntity> getTrainingsCategoriesLinksById() {
        return trainingsCategoriesLinksById;
    }

    public void setTrainingsCategoriesLinksById(Collection<TrainingCategoryLinkEntity> trainingsCategoriesLinksById) {
        this.trainingsCategoriesLinksById = trainingsCategoriesLinksById;
    }

    @OneToMany(mappedBy = "trainingPlansByTrainingPlanId")
    public Collection<TrainingExerciseLinkEntity> getTrainingsExercisesLinksById() {
        return trainingsExercisesLinksById;
    }

    public void setTrainingsExercisesLinksById(Collection<TrainingExerciseLinkEntity> trainingsExercisesLinksById) {
        this.trainingsExercisesLinksById = trainingsExercisesLinksById;
    }
}
