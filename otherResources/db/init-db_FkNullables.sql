
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--				MEMENTO DIFFICULTES RENCONTREES
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Remarque : les tables doivent être crées au préalable avant de faire les CONSTRAINT NomContrainte FOREIGN KEY
-- On ne peut pas dire que id_rol dans la table users pointe vers la table roles si la table roles n'existe pas encore
-- Donc on crée bien les champs qui sont des foreign keys dans les tables, mais on attend d'avoir créer toutes les tables avant de dire que ce sont des foreign keys
-- Du coup, vu que j'avais écrit toutes les CONSTRAINT FOREIGN KEY au sein de chaque table concernée,
-- il a fallu sortir toutes les CONSTRAINT FOREIGN KEY de ces tables, et les remettre en fin de fichier via des ALTER TABLE ADD
-- Ceci a été réalisé et mis dans le fichier .txt nommé CONSTRAINTS_FK.txt
-- On a ensuite delete les CONSTRAINT FOREIGN KEY dans chaque table, et copié le contenu du CONSTRAINTS_FK.txt à la fin de ce fichier-ci aka CodeSQL_SGBD.txt
-- avant d'en changer le format de .txt vers du .sql



-- Pq rendre les foreigns keys nullables ?
-- https://stackoverflow.com/questions/7573590/can-a-foreign-key-be-null-and-or-duplicate#:~:text=Short answer%3A Yes%2C it can,table (the parent table).&text=Null by definition is not a value.
-- Ca bloque trop les inserts de raw datas pour faire des tests, faut tjrs que la table-parent d'une foreign key soit peuplée avant de pouvoir indiquer un id pour cette foreign key dans la table-enfant

-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------




-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--				PARAMETRAGE DB + DROP DATABASE AVANT DE LA RECREER + CREATION DES TABLES AVEC LEURS PK - ATTRIBUTS - FOREIGN KEYS - CONTRAINTES SUR PK - CONTRAINTES SUR ATTRIBUTS
-- 				PAS DE CONTRAINTES SUR LES FOREIGN KEYS ICI, CF "MEMENTO DIFFICULTES RENCONTREES"
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
-- ATTENTION : START TRANSACTION nécessite un COMMIT; en fin de fichier
SET time_zone = "+00:00";


--
-- Nom de notre Base de données :  `shapp`
--

DROP DATABASE IF EXISTS shapp;
CREATE DATABASE IF NOT EXISTS shapp;
USE shapp;


-- DROP TABLE IF EXISTS countries,
--					   addresses,
--                     cities,
--                     etc,
--	 				   etc;
-- On fait pas tous les DROP de tables ici, mais via le DROP TABLE IF EXISTS NomTable avant chaque CREATE TABLE IF NOT EXISTS NomTable qui arrivent dans la section suivante




-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--				DROP DE CHAQUE TABLE AVANT DE LA RECREER - CREATION DES TABLES AVEC LEURS PK - ATTRIBUTS - FOREIGN KEYS - CONTRAINTES SUR PK - CONTRAINTES SUR ATTRIBUTS
-- 				PAS DE CONTRAINTES SUR LES FOREIGN KEYS ICI, CF "MEMENTO DIFFICULTES RENCONTREES"
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
















--  -----------------------------------------------------------
--  -----------------------------------------------------------
--  AXEL
--  -----------------------------------------------------------
--  -----------------------------------------------------------




-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `first_name` varchar(100) NOT NULL,
    `last_name` varchar(100) NOT NULL,
    `username` varchar(100) NOT NULL,
    `password` varchar(255) NOT NULL,
    `birthdate` date NOT NULL,
    `gender` ENUM('FEMININ', 'MASCULIN', 'AUTRE') NOT NULL DEFAULT 'AUTRE',
    `email_address` varchar(255) NOT NULL,
    `phone_number` varchar(16) NOT NULL,
    `creation_date_time` datetime NOT NULL DEFAULT NOW(),
    `active` tinyint(1) NOT NULL DEFAULT 1,
    `coach_degree_info` varchar(2000) NULL,
    `coach_career_start_date` date NULL,
    `role_id` int(11) NULL DEFAULT NULL,

    CONSTRAINT PK_USERS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_USERS_username UNIQUE (`username`),
    CONSTRAINT UNIQUE_CONSTRAINT_USERS_email_address UNIQUE (`email_address`),
    CONSTRAINT UNIQUE_CONSTRAINT_USERS_phone_number UNIQUE (`phone_number`)




    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Structure de la table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `label` varchar(100) NOT NULL,
    `description` varchar(2000) NULL,


    CONSTRAINT PK_ROLES PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_ROLES_label UNIQUE (`label`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Structure de la table `roles_permissions`
--

DROP TABLE IF EXISTS `roles_permissions`;
CREATE TABLE IF NOT EXISTS `roles_permissions` (

-- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
    `id` int(11) NOT NULL AUTO_INCREMENT,


    `role_id` int(11) NULL DEFAULT NULL,
    `permission_id` int(11) NULL DEFAULT NULL,


    CONSTRAINT PK_ROLES_PERMISSIONS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_ROLES_PERMISSIONS_fkcomposite UNIQUE (`role_id`,`permission_id`)



    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
CREATE TABLE IF NOT EXISTS `permissions` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `label` varchar(100) NOT NULL,
    `description` varchar(2000) NOT NULL,


    CONSTRAINT PK_PERMISSIONS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_PERMISSIONS_label UNIQUE (`label`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `measures`
--

DROP TABLE IF EXISTS `measures`;
CREATE TABLE IF NOT EXISTS `measures` (

-- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `creation_date_time` datetime NOT NULL DEFAULT NOW(),
    `measured_value` double NOT NULL,
    `note` varchar(2000) NOT NULL,

    `user_id` int(11) NULL DEFAULT NULL,
    `measurand_id` int(11) NULL DEFAULT NULL,


    CONSTRAINT PK_MEASURES PRIMARY KEY (`id`)



    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `measurands`
--

DROP TABLE IF EXISTS `measurands`;
CREATE TABLE IF NOT EXISTS `measurands` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `description` varchar(2000) NOT NULL,

    `unit_id` int(11) NULL DEFAULT NULL,


    CONSTRAINT PK_MEASURANDS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_MEASURANDS_name UNIQUE (`name`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `units`
--

DROP TABLE IF EXISTS `units`;
CREATE TABLE IF NOT EXISTS `units` (

-- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `symbol` varchar(100) NOT NULL DEFAULT 'NONE',
    `description` varchar(2000) NOT NULL,



    CONSTRAINT PK_UNITS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_UNITS_name UNIQUE (`name`)



    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;





-- --------------------------------------------------------

--
-- Structure de la table `users_subscriptions`
--

DROP TABLE IF EXISTS `users_subscriptions`;
CREATE TABLE IF NOT EXISTS `users_subscriptions` (

-- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `begin_date_time` datetime NOT NULL,
    `end_date_time` datetime NOT NULL,


    `user_id` int(11) NULL DEFAULT NULL,
    `subscription_id` int(11) NULL DEFAULT NULL,


    CONSTRAINT PK_USERS_SUBSCRIPTIONS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_USERS_SUBSCRIPTIONS_fkcomposite UNIQUE (`user_id`,`subscription_id`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `subscriptions`
--

DROP TABLE IF EXISTS `subscriptions`;
CREATE TABLE IF NOT EXISTS `subscriptions` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `price_per_month` decimal(10,2) NOT NULL,
    `description` varchar(2000) NOT NULL,
    `rank` int(11) NOT NULL,


    CONSTRAINT PK_SUBSCRIPTIONS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_SUBSCRIPTIONS_name UNIQUE (`name`),
    CONSTRAINT UNIQUE_CONSTRAINT_SUBSCRIPTIONS_rank UNIQUE (`rank`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `users_teams`
--

DROP TABLE IF EXISTS `users_teams`;
CREATE TABLE IF NOT EXISTS `users_teams` (

-- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `join_date_time` datetime NOT NULL DEFAULT NOW(),
    `leave_date_time` datetime NULL,


    `user_id` int(11) NULL DEFAULT NULL,
    `team_id` int(11) NULL DEFAULT NULL,


    CONSTRAINT PK_USERS_TEAMS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_USERS_TEAMS_fkcomposite UNIQUE (`user_id`,`team_id`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `teams`
--

DROP TABLE IF EXISTS `teams`;
CREATE TABLE IF NOT EXISTS `teams` (

-- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `description` varchar(2000) NOT NULL,
    `creation_date_time` datetime NOT NULL DEFAULT NOW(),


    `user_creator_id` int(11) NULL DEFAULT NULL,


    CONSTRAINT PK_TEAMS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_SUBSCRIPTIONS_name UNIQUE (`name`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=`latin1_general_ci`;


-- --------------------------------------------------------

--
-- Structure de la table `team_posts`
--

DROP TABLE IF EXISTS `team_posts`;
CREATE TABLE IF NOT EXISTS `team_posts` (

-- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `creation_date_time` datetime NOT NULL DEFAULT NOW(),
    `message` varchar(2000) NOT NULL,


    `team_id` int(11) NULL DEFAULT NULL,
    `user_id` int(11) NULL DEFAULT NULL,



    CONSTRAINT PK_TEAM_POSTS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_TEAM_POSTS_fkcomposite UNIQUE (`team_id`,`user_id`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `team_comments`
--

DROP TABLE IF EXISTS `team_comments`;
CREATE TABLE IF NOT EXISTS `team_comments` (

-- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `creation_date_time` datetime NOT NULL DEFAULT NOW(),
    `message` varchar(2000) NOT NULL,


    `team_id` int(11) NULL DEFAULT NULL,
    `user_id` int(11) NULL DEFAULT NULL,


    CONSTRAINT PK_TEAM_COMMENTS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_TEAM_USERS_fkcomposite UNIQUE (`team_id`,`user_id`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;











--  -----------------------------------------------------------
--  -----------------------------------------------------------
--  WILLIAM temporaire, fonctionnalités à redéfinir
--  -----------------------------------------------------------
--  -----------------------------------------------------------




-- --------------------------------------------------------

--
-- Structure de la table `levels`
--

DROP TABLE IF EXISTS `levels`;
CREATE TABLE IF NOT EXISTS `levels` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `code` varchar(10) NOT NULL,
    `name` varchar(100) NOT NULL,
    `description` varchar(2000)  NULL,
    `keyword_en` varchar(100) NOT NULL,
    `keyword_fr` varchar(100) NOT NULL,




    CONSTRAINT PK_LEVELS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_LEVELS_code UNIQUE (`code`),
    CONSTRAINT UNIQUE_CONSTRAINT_LEVELS_name UNIQUE (`name`)




    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `goals`
--

DROP TABLE IF EXISTS `goals`;
CREATE TABLE IF NOT EXISTS `goals` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `code` varchar(10) NOT NULL,
    `name` varchar(100) NOT NULL,
    `description` varchar(2000) NULL  ,
    `keyword_en` varchar(100) NOT NULL,
    `keyword_fr` varchar(100) NOT NULL,




    CONSTRAINT PK_GOALS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_GOALS_code UNIQUE (`code`),
    CONSTRAINT UNIQUE_CONSTRAINT_GOALS_name UNIQUE (`name`)




    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `training_types`
--

DROP TABLE IF EXISTS `training_types`;
CREATE TABLE IF NOT EXISTS `training_types` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `code` varchar(10) NOT NULL,
    `name` varchar(100) NOT NULL,
    `description` varchar(2000) NULL ,
    `keyword_en` varchar(100) NOT NULL,
    `keyword_fr` varchar(100) NOT NULL,



    CONSTRAINT PK_TRAINING_TYPES PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_TRAINING_TYPES_code UNIQUE (`code`),
    CONSTRAINT UNIQUE_CONSTRAINT_TRAINING_TYPES_name UNIQUE (`name`)




    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `categories`
--

DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `code` varchar(10) NOT NULL,
    `name` varchar(100) NOT NULL,
    `description` varchar(2000) NULL,
    `keyword_en` varchar(100) NOT NULL,
    `keyword_fr` varchar(100) NOT NULL,




    CONSTRAINT PK_CATEGORIES PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_CATEGORIES_code UNIQUE (`code`),
    CONSTRAINT UNIQUE_CONSTRAINT_CATEGORIES_name UNIQUE (`name`)




    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `training_plans`
--
DROP TABLE IF EXISTS `training_plans`;
CREATE TABLE IF NOT EXISTS `training_plans` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `description` varchar(2000) NULL,
    `access_range` ENUM('PRIVATE', 'PUBLIC', 'SHAREDLINK', 'LIMITED') NOT NULL DEFAULT 'PRIVATE',
    `duration_estimation` double NOT NULL DEFAULT -1,
    `calories_estimation` double NOT NULL DEFAULT -1,




    `level_id` int(11) DEFAULT NULL,
    `goal_id` int(11) DEFAULT NULL,
    `training_type_id` int(11) DEFAULT NULL,
    `subscription_id_minimum_rank` int(11) DEFAULT NULL,
    `user_id_creator` int(11) DEFAULT NULL,




    CONSTRAINT PK_TRAINING_PLANS PRIMARY KEY (`id`)




    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `training_categories_link`
--
DROP TABLE IF EXISTS `trainings_categories_link`;
CREATE TABLE IF NOT EXISTS `trainings_categories_link` (

    -- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
    `id` int(11) NOT NULL AUTO_INCREMENT,




    `training_plan_id` int(11) NOT NULL,
    `category_id` int(11) NOT NULL,




    CONSTRAINT PK_TRAININGS_CATEGORIES_LINK PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_TRAININGS_CATEGORIES_LINK_fkcomposite UNIQUE (`training_plan_id`,`category_id`)




    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `schedules_link`
--
DROP TABLE IF EXISTS `schedules_link`;
CREATE TABLE IF NOT EXISTS `schedules_link` (

    -- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
    `id` int(11) NOT NULL AUTO_INCREMENT,
    -- Notice that CURRENT_TIMESTAMP and CURRENT_TIMESTAMP() are synonyms for NOW() so you can use them interchangeably.
    `execution_date_time` DATETIME NOT NULL DEFAULT NOW(),




    `user_id` int(11) NOT NULL,
    `training_plan_id` int(11) NOT NULL,




    CONSTRAINT PK_SCHEDULES_LINK PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_SCHEDULES_LINK_fkcomposite UNIQUE (`user_id`,`training_plan_id`,`execution_date_time`)




    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `trainings_exercises_link`
--
DROP TABLE IF EXISTS `trainings_exercises_link`;
CREATE TABLE IF NOT EXISTS `trainings_exercises_link` (

    -- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `sequential_number_exercise` int(11) NOT NULL,




    `training_plan_id` int(11) NOT NULL,
    `exercise_id` int(11) NOT NULL,




    CONSTRAINT PK_TRAININGS_EXERCISES_LINK PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_TRAINING_EXERCISES_LINK_fkcomposite UNIQUE (`training_plan_id`,`exercise_id`,`sequential_number_exercise`)




    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `exercises`
--
DROP TABLE IF EXISTS `exercises`;
CREATE TABLE IF NOT EXISTS `exercises` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `description` varchar(2000) NULL,




    `level_id` int(11) DEFAULT NULL,




    CONSTRAINT PK_EXERCISES PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_EXERCISES_name UNIQUE (`name`)




    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `muscles`
--
DROP TABLE IF EXISTS `muscles`;
CREATE TABLE IF NOT EXISTS `muscles` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `description` varchar(2000) NULL,
    `picture_URI` varchar(2000) DEFAULT NULL,




    CONSTRAINT PK_MUSCLES PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_MUSCLES_name UNIQUE (`name`)




    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `exercises_muscles_link`
--
DROP TABLE IF EXISTS `exercises_muscles_link`;
CREATE TABLE IF NOT EXISTS `exercises_muscles_link` (

    -- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `details` varchar(2000) NULL,




    `exercise_id` int(11) NOT NULL,
    `muscle_id` int(11) NOT NULL,




    CONSTRAINT PK_EXERCISES_MUSCLES_LINK PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_EXERCISES_MUSCLES_LINK_fkcomposite UNIQUE (`exercise_id`,`muscle_id`)




    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `equipment_generics`
--
DROP TABLE IF EXISTS `equipment_generics`;
CREATE TABLE IF NOT EXISTS `equipment_generics` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `description` varchar(2000) NULL,




    CONSTRAINT PK_EQUIPEMENT_GENERICS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_EQUIPEMENT_GENERICS_name UNIQUE (`name`)




    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `requirements_link`
--
DROP TABLE IF EXISTS `requirements_link`;
CREATE TABLE IF NOT EXISTS `requirements_link` (

    -- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `quantity` int(11) DEFAULT -2,



    `exercise_id` int(11) NOT NULL,
    `generic_id` int(11) NOT NULL,




    CONSTRAINT PK_REQUIREMENTS_LINK PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_REQUIREMENTS_LINK_fkcomposite UNIQUE (`exercise_id`,`generic_id`)




    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `brands`
--
DROP TABLE IF EXISTS `brands`;
CREATE TABLE IF NOT EXISTS `brands` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `description` varchar(2000) NULL,




    CONSTRAINT PK_BRANDS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_BRANDS_name UNIQUE (`name`)




    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `equipments_items_link`
--
DROP TABLE IF EXISTS `equipment_items_link`;
CREATE TABLE IF NOT EXISTS `equipment_items_link` (

    -- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `description` varchar(2000) NULL,
    `price` decimal(10,2) DEFAULT NULL,
    `referal_buy_URL` varchar(2000) DEFAULT 'NONE',



    `generic_id` int(11) NOT NULL,
    `brand_id` int(11) NOT NULL,




    CONSTRAINT PK_EQUIPEMENT_ITEMS_LINK PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_EQUIPMENT_ITEMS_LINK_name UNIQUE (`name`)




    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------







































-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--				AJOUT DES CONTRAINTES DE FOREIGN KEYS POUR CHAQUE TABLE EN POSSEDANT
--
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



-- On réalise ceci via des ALTER TABLE comme suit
-- ALTER TABLE1 nomTableEnfant ADD CONSTRAINT ConstraintName (column ID dansTableEnfant) REFERENCES TableParent (columnId dans TableParent) ON DELETE NO ACTION ON UPDATE CASCADE


-- ALTER TABLE NomTable
-- pas de parenthèses
-- écrire ADD qu'une seule fois puis ouvrir parenthèse
-- séparer les CONSTRAINT par des virgules
-- fermer parenthèse
-- écrire un ; qui terminera l'instruction d'alter table




--  -----------------------------------------------------------
--  -----------------------------------------------------------
--  AXEL
--  -----------------------------------------------------------
--  -----------------------------------------------------------

ALTER TABLE users

    ADD(
        CONSTRAINT FK_USERS_ROLE FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
        );


ALTER TABLE roles_permissions

    ADD(
		CONSTRAINT FK_ROLES_PERMISSIONS_ROLES FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
		CONSTRAINT FK_ROLES_PERMISSIONS_PERMISSIONS FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE measures

    ADD(
		CONSTRAINT FK_MEASURES_USER FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_MEASURES_MEASURAND FOREIGN KEY (`measurand_id`) REFERENCES `measurands` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE measurands

    ADD(
		CONSTRAINT FK_MEASURANDS_UNIT FOREIGN KEY (`unit_id`) REFERENCES `units` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE users_subscriptions

    ADD(
		CONSTRAINT FK_USERS_SUBSCRIPTIONS_USERS FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_USERS_SUBSCRIPTIONS_SUBSCRIPTIONS FOREIGN KEY (`subscription_id`) REFERENCES `subscriptions` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE users_teams

    ADD(
		CONSTRAINT FK_USERS_TEAMS_USERS FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_USERS_TEAMS_TEAMS FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE teams

    ADD(
		CONSTRAINT FK_TEAMS_USER_CREATOR FOREIGN KEY (`user_creator_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE team_posts

    ADD(
		CONSTRAINT FK_TEAM_POSTS_TEAM FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_TEAM_POSTS_USER FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE team_comments

    ADD(
		CONSTRAINT FK_TEAM_COMMENTS_TEAM FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_TEAM_COMMENTS_USER FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);











--  -----------------------------------------------------------
--  -----------------------------------------------------------
--  WILLIAM temporaire, fonctionnalités à redéfinir
--  -----------------------------------------------------------
--  -----------------------------------------------------------

ALTER TABLE training_plans

    ADD(
	  CONSTRAINT FK_TRAINING_PLANS_LEVELS FOREIGN KEY (`level_id`) REFERENCES `levels` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
	  CONSTRAINT FK_TRAINING_PLANS_GOALS FOREIGN KEY (`goal_id`) REFERENCES `goals` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
	  CONSTRAINT FK_TRAINING_PLANS_TRAINING_TYPES FOREIGN KEY (`training_type_id`) REFERENCES `training_types` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
	  CONSTRAINT FK_TRAINING_PLANS_SUBSCRIPTIONS FOREIGN KEY (`subscription_id_minimum_rank`) REFERENCES `subscriptions` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
	  CONSTRAINT FK_TRAINING_PLANS_USERS FOREIGN KEY (`user_id_creator`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE trainings_categories_link

    ADD(
	  CONSTRAINT FK_TRAINING_CATEGORIES_LINK_TRAINING_PLANS FOREIGN KEY (`training_plan_id`) REFERENCES `training_plans` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
	  CONSTRAINT FK_TRAINING_CATEGORIES_LINK_CATEGORIES FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE schedules_link

    ADD(
    CONSTRAINT FK_SCHEDULES_LINK_USERS FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
	  CONSTRAINT FK_SCHEDULES_LINK_TRAINING_PLANS FOREIGN KEY (`training_plan_id`) REFERENCES `training_plans` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE trainings_exercises_link

    ADD(
	  CONSTRAINT FK_TRAININGS_EXERCISES_LINK_TRAINING_PLANS FOREIGN KEY (`training_plan_id`) REFERENCES `training_plans` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT FK_TRAININGS_EXERCISES_LINK_EXERCISES FOREIGN KEY (`exercise_id`) REFERENCES `exercises` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE exercises

    ADD(
	  CONSTRAINT FK_EXERCISES_LEVELS FOREIGN KEY (`level_id`) REFERENCES `levels` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE exercises_muscles_link

    ADD(
	  CONSTRAINT FK_EXERCISES_MUSCLES_LINK_EXERCISES FOREIGN KEY (`exercise_id`) REFERENCES `exercises` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT FK_EXERCISES_MUSCLES_LINK_MUSCLES FOREIGN KEY (`muscle_id`) REFERENCES `muscles` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE requirements_link

    ADD(
	  CONSTRAINT FK_REQUIREMENTS_LINK_EXERCISES FOREIGN KEY (`exercise_id`) REFERENCES `exercises` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT FK_REQUIREMENTS_LINK_EQUIPMENT_GENERICS FOREIGN KEY (`generic_id`) REFERENCES `equipment_generics` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE equipment_items_link

    ADD(
    CONSTRAINT FK_EQUIPMENT_ITEMS_LINK_EQUIPMENT_GENERICS FOREIGN KEY (`generic_id`) REFERENCES `equipment_generics` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
	  CONSTRAINT FK_EQUIPMENT_ITEMS_LINK_BRANDS FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE

	);



















-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--				AJOUT DE DATA RECORDS = POPULATING TABLES
--              ATTENTION NE PAS OUBLIER LE COMMIT; Á LA FIN
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------





--  -----------------------------------------------------------
--  -----------------------------------------------------------
--  AXEL
--  -----------------------------------------------------------
--  -----------------------------------------------------------

INSERT INTO `roles` (`id`, `label`, `description`) VALUES
(1, 'administrator', 'Ce rôle est attribué aux utilisateurs identifiés en tant qu\'administrateurs'),
(2, 'user_bronze', 'Ce rôle est attribué aux utilisateurs identifiés qui bénéficient de l\'abonnement gratuit `bronze`'),
(3, 'user_silver', 'Ce rôle est attribué aux utilisateurs identifiés qui bénéficient de l\'abonnement payant `argent`'),
(4, 'user_gold', 'Ce rôle est attribué aux utilisateurs identifiés qui bénéficient de l\'abonnement payant `or`'),
(5, 'user_platinum', 'Ce rôle est attribué aux utilisateurs identifiés qui bénéficient de l\'abonnement payant `platine`'),
(6, 'user_diamond', 'Ce rôle est attribué aux utilisateurs identifiés qui bénéficient de l\'abonnement payant `diamant`');

-- The unencrypted password for all users is "Password1@"
INSERT INTO `users` (`id`,`first_name`,`last_name`,`username`,`password`,`birthdate`,`gender`,`email_address`,`phone_number`,
					`creation_date_time`,`active`,`coach_degree_info`,`coach_career_start_date`, `role_id`)
VALUES
(1,'Arya','Secret','admin','$shiro1$SHA-256$500000$U6c6oOh4vW5QRvXotD8Ywg==$rQeHywWeulrE1cok0AcMLklqJ7stY0dtzWiJoA4Yp8M=','1970-03-03','Féminin','arya.secret@gmail.com','+32486444444',
	'2020-10-02 12:30:03',1,NULL,NULL, 1),
(2,'Jean','Seigne','ubronze','$shiro1$SHA-256$500000$U6c6oOh4vW5QRvXotD8Ywg==$rQeHywWeulrE1cok0AcMLklqJ7stY0dtzWiJoA4Yp8M=','1985-02-02','Autre','jean.seigne@gmail.com','+32486555555',
 '2020-10-05 10:46:03',1,NULL,NULL, 2),
(3,'Gaspard','Ent','usilver','$shiro1$SHA-256$500000$U6c6oOh4vW5QRvXotD8Ywg==$rQeHywWeulrE1cok0AcMLklqJ7stY0dtzWiJoA4Yp8M=','1980-05-05','Masculin','gaspard.ent@gmail.com','+32486325645',
	'2020-08-09 17:46:03',1,'Dîplomé de l\Ecole de Kinésithérapie de Wavre, Promotion 1999','2001-01-01', 3),
(4,'Viktor','Ganise','ugold','$shiro1$SHA-256$500000$U6c6oOh4vW5QRvXotD8Ywg==$rQeHywWeulrE1cok0AcMLklqJ7stY0dtzWiJoA4Yp8M=','1991-01-01','Masculin','viktor.ganise@gmail.com','+32486888888',
	'2020-08-09 17:46:03',1,NULL,NULL, 4);



INSERT INTO `permissions` (`id`, `label`, `description`) VALUES
(1, '*', 'Permet toutes les actions sur l\'ensemble de l\'application.'),
(2, 'administrator', 'Identifie un administrateur.'),
(3, 'own_account:*', 'Permet toutes les actions sur son propre compte utilisateur'),
(4, 'own_account:read', 'Permet de consulter les détails de son propre compte utilisateur.'),
(5, 'own_account:edit', 'Permet d\'éditer les détails de de son propre compte utilisateur'),
(6, 'own_account:deactivate', 'Permet de désactiver son propre compte utilisateur.'),
(7, 'user_accounts:*', 'Permet toutes les actions sur les comptes utilisateurs'),
(8, 'user_accounts:new', 'Permet de créer un nouveau compte utilisateur'),
(9, 'user_accounts:read', 'Permet de consulter les détails de n\'importe quel utilisateur.'),
(10, 'user_accounts:edit', 'Permet d\'éditer les détails de n\'importe quel utilisateur.'),
(11, 'user_accounts:deactivate', 'Permet de désactiver un compte utilisateur.'),
(12, 'roles:*', 'Permet toutes les actions sur les roles.'),
(13, 'roles:new', 'Permet de créer un nouveau rôle.'),
(14, 'roles:read', 'Permet de consulter les détails de n\'importe quel rôle.'),
(15, 'roles:edit', 'Permet d\'éditer les détails de n\'importe quel rôle.'),
(16, 'roles:delete', 'Permet de supprimer un rôle.'),
(17, 'roles:assign_permission', 'Permet d\'assigner une permission à un rôle.'),
(18, 'roles:remove_permission', 'Permet de retirer une permission d\'un rôle.'),
(19, 'roles:add_user', 'Permet d\'ajouter un utilisateur à un rôle.'),
(20, 'roles:remove_user', 'Permet de retirer un utilisateur d\'un rôle.'),
(21, 'permissions:*', 'Permet toutes les actions sur les permissions.'),
(22, 'permissions:new', 'Permet de créer une nouvelle permission'),
(23, 'permissions:read', 'Permet de consulter les détails de n\'importe quelle permission.'),
(24, 'permissions:edit', 'Permet d\'éditer les détails de n\'importe quelle permission.'),
(25, 'permissions:delete', 'Permet de supprimer une permission.'),
(26, 'own_measures:*', 'Permet toutes les actions sur ses mesures personnelles.'),
(27, 'own_measures:new', 'Permet de créer une nouvelle mesure personnelle'),
(28, 'own_measures:read', 'Permet de consulter les détails de n\'importe quelle mesure personnelle.'),
(29, 'own_measures:edit', 'Permet d\'éditer les détails de n\'importe quelle mesure personnelle.'),
(30, 'own_measures:delete', 'Permet de supprimer une mesure personnelle.'),
(31, 'own_measurands:*', 'Permet toutes les actions sur ses types de mesure personnels'),
(32, 'own_measurands:new', 'Permet de créer un nouveau type de mesure personnel'),
(33, 'own_measurands:read', 'Permet de consulter les détails de n\'importe quel type de mesure personnel.'),
(34, 'own_measurands:edit', 'Permet d\'éditer les détails de n\'importe quel type de mesure personnel.'),
(35, 'own_measurands:delete', 'Permet de supprimer un type de mesure personnel.'),
(36, 'own_measurands:assign_unit', 'Permet d\'assigner une unité à un type de mesure personnel.'),
(37, 'own_measurands:remove_unit', 'Permet de retirer une unité d\'un type de mesure personnel.'),
(38, 'own_subscription:*', 'Permet toutes les actions sur son abonnement personnel'),
(39, 'own_subscription:consult', 'Permet de consulter son abonnement personnel'),
(40, 'own_subscription:subscribe', 'Permet de souscrire à un abonnement personnel.'),
(41, 'own_subscription:upgrade', 'Permet de surclasser son rang d\'abonnement personnel'),
(42, 'own_subscription:downgrade', 'Permet de déclasser son rang d\'abonnement personnel'),
(43, 'own_subscription:unsubscribe', 'Permet de mettre fin à son abonnement personnel.');

INSERT INTO `roles_permissions` (`id`,`role_id`,`permission_id`) VALUES
-- role "administrator"
(1,1,1),
(2,1,2),
-- role "user_bronze", "user_silver",...,"user_diamond"
(3,2,3),
(4,2,26),
(5,2,31),
(6,2,38),
(7,3,3),
(8,3,26),
(9,3,31),
(10,3,38),
(11,4,3),
(12,4,26),
(13,4,31),
(14,4,38),
(15,5,3),
(16,5,26),
(17,5,31),
(18,5,38),
(19,6,3),
(20,6,26),
(21,6,31),
(22,6,38);




INSERT INTO `units` (`id`, `name`, `symbol`, `description`) VALUES
(1, 'kilogramme', 'Kg', 'Unité de base du système international de mesure de masse'),
(2, 'mètre', 'm', 'Unité de base du système international de mesure de longueur'),
(3, 'centimètre', 'cm', 'Centième du mètre');


INSERT INTO `measurands` (`id`,`name`,`description`,`unit_id`) VALUES
(1, 'poids corporel', 'le poids du corps mesuré sur une balance amateure', 1),
(2, 'tour de biceps', 'le tour du biceps', 3);

INSERT INTO `measures` (`id`, `creation_date_time`, `measured_value`, `note`, `user_id`, `measurand_id`) VALUES
(1,'2020-08-09 08:46:03',79.05,'poids mesuré à jeun le matin',1,1),
(2,'2020-08-10 08:46:03',80.05,'poids mesuré après snacking nocture',1,1),
(3,'2020-08-09 17:46:03',25.02,'biceps: après exercices des membres supérieurs',1,2),
(4,'2020-08-11 17:46:03',24.65,'biceps: au repos',1,2);



INSERT INTO `subscriptions` (`id`, `name`,`price_per_month`,`description`,`rank`) VALUES
(1, 'Bronze',5,'Blabla desc', 1),
(2, 'Silver',10,'Blabla desc',2),
(3, 'Gold',15,'Blabla desc',3),
(4, 'Platinum',20, 'Blabla desc',4),
(5, 'Diamond',25,'Blabla desc',5);

INSERT INTO `users_subscriptions` (`id`,`begin_date_time`,`end_date_time`,`user_id`,`subscription_id`) VALUES
(1,'2020-08-11 17:46:03','2020-09-11 17:46:03',1,1),
(2,'2020-08-11 17:46:03','2020-09-11 17:46:03',2,3);














--  -----------------------------------------------------------
--  -----------------------------------------------------------
--  WILLIAM : temporaire, fonctionnalités à redéfinir
--  -----------------------------------------------------------
--  -----------------------------------------------------------


INSERT INTO `levels` (`id`, `code`, `name`, `description`,`keyword_en`, `keyword_fr`) VALUES
(1, 'BEG-01', 'Beginner ', 'Ce niveau de difficulté s\'adresse aux sportifs débutants', 'Beginner', 'Débutant'),
(2, 'INT-01', 'Intermediary ', 'Ce niveau de difficulté s\'adresse aux sportifs intérmédiaires', 'Intermediary', 'Intermédiaire' ),
(3, 'CON-01', 'Confirmed ', 'Ce niveau de difficulté s\'adresse aux sportifs confirmés', 'Confirmed', 'Confirmé' );



INSERT INTO `goals` (`id`, `code`, `name`, `description`,`keyword_en`, `keyword_fr`) VALUES
(1, 'WL-01', 'Weightloss ', 'Cet objectif vise à perdre du poids', 'Weightloss', 'Perte de poids'),
(2, 'CARD-01', 'Cardio ', 'Cet objectif vise à améliorer le cardio', 'Cardio', 'Cardio' ),
(3, 'SOUP-01', 'Relaxation ', 'Cet objectif vise à améliorer la souplesse', 'Relaxation', 'Assouplissement' ),
(4, 'MUSC-01', 'Muscular reinforcement ', 'Cet objectif vise à se renforcer musculairement', 'Muscular reinforcement', 'Renforcement musculaire' );





INSERT INTO `training_types` (`id`, `code`, `name`, `description`,`keyword_en`, `keyword_fr`) VALUES
(1, 'SPLIT-01', 'Split ', 'Avec ce type d\'entraînement, chaque séance se focalise  sur un et un seul groupe musculaire', 'Split', 'Split'),
(2, 'FULL-01', 'Full body ', 'Avec ce type d\'entraînement, chaque séance entraîne l\entiéreté du corps', 'Full body', 'Full body'),
(3, 'HALF-01', 'Half body ', 'Avec ce type d\'entraînement, chaque séance entraîne soit la moitié supérieure, soit la moitié inférieure du corps', 'Half body', 'Half body');



INSERT INTO `categories` (`id`, `code`, `name`, `description`,`keyword_en`, `keyword_fr`) VALUES
(1, 'TIME-01', 'Around 30 minutes ', ' Cette catégorie recense les entraînements durant environ 30 minutes ', ' 30 mins', '30 mins'),
(2, 'TIME-02', 'Around 1 hour ', ' Cette catégorie recense les entraînements durant environ 1 heure ', ' 1 hour', '1 heure'),
(3, 'EQUIP-01', 'No equipment needed', ' Cette catégorie recense les entraînements ne nécessitant aucun matériel ', 'No equipment', 'Sans matériel');




INSERT INTO `training_plans` (`id`, `name`,`description`,`access_range`, `duration_estimation`, `calories_estimation`, `level_id`, `goal_id`, `training_type_id`, `subscription_id_minimum_rank`, `user_id_creator` ) VALUES
(1, 'Plan cardio', 'Plan d\'entraînement cardio ', 'PUBLIC', 3600, 1000, 1, 2, 2 , 1, NULL),
(2, 'Plan muscu', 'Plan d\'entraînement muscu ', 'PUBLIC', 3600, 800 , 1, 4, 1 , 1, NULL),
(3, 'Plan perte de poids', 'Plan d\'entraînement perte de poids', 'PUBLIC', 3600, 1300 , 1, 1, 2 , 1, NULL);




INSERT INTO `trainings_categories_link` (`id`, `training_plan_id`, `category_id`) VALUES
(1, 1, 2),
(2, 1, 3),
(3, 2, 2),
(4,3,2);

/*
INSERT INTO `schedules_link` (`id`, `user_id`,`training_plan_id`,`execution_date_time`) VALUES
(1, 1, 1, NOW()),
(2, 2, 3, NOW()),
(3, 3, 2, NOW());
*/




INSERT INTO `exercises` (`id`, `name`, `description`,`level_id`) VALUES
(1, 'Developpé-couché aux haltères', 'Description du benchpress aux haltères ', 1),
(2, 'Squat', 'Description du squat ', 2),
(3, 'Soulevé de terre', 'Description du deadlift', 2),
(4, 'Developpé-couché à la barre', 'Description du benchpress à la barre ', 1);



INSERT INTO `trainings_exercises_link` (`id`, `sequential_number_exercise`, `training_plan_id`,`exercise_id`) VALUES
(1, 1, 1, 1),
(2, 2, 1, 3),
(3, 3, 1, 1),

(4,1,2,4),
(5,2,2,1),

(6,1,3,4);





INSERT INTO `muscles` (`id`, `name`, `description`,`picture_URI`) VALUES
(1, 'Pectoral', 'Description d\'un pectoral', NULL),
(2, 'Quadriceps', 'Description d\'un quadriceps', NULL),
(3, 'Région lombaire', 'Description de la région lombaire', NULL);




INSERT INTO `exercises_muscles_link` (`id`, `details`, `exercise_id`,`muscle_id`) VALUES
(1, 'Détails d\'exécution à mettre', 1, 1),
(2, 'Détails d\'exécution à mettre', 2, 2),
(3, 'Détails d\'exécution à mettre', 3, 3);





INSERT INTO `equipment_generics` (`id`, `name`, `description`) VALUES
(1, 'Tapis', 'Description d\'un tapis'),
(2, 'Kettlebell', 'Description d\'un kettlebell'),
(3, 'Haltère', 'Description d\'un haltère'),
(4, 'Barre athlétique', 'Description d\'une barre athlétique'),
(5, 'Corde à sauter', 'Description d\'une corde à sauter'),
(6, 'Corde ondulatoire', 'Description de la corde ondulatoire'),
(7, 'Bande de résistance', 'Description d\'une bande de résistance'),
(8, 'Rouleau de massage', 'Description d\'un rouleau de massage');




INSERT INTO `requirements_link` (`id`, `quantity`, `exercise_id`,`generic_id`) VALUES
(1, 2, 1, 3),
(2, 1, 3, 2),
(3, 1, 4, 4),
(4, 2 , 4, 7);




INSERT INTO `brands` (`id`, `name`, `description`) VALUES
(1, 'Puma', 'Description de la marque Puma'),
(2, 'Rogue', 'Description de la marque Rogue '),
(3, 'Nike', 'Description de la marque Nike'),
(4, 'Domyos', 'Description de la marque Domyos');





INSERT INTO `equipment_items_link` (`id`, `name`, `description`, `price`, `referal_buy_URL`, `generic_id`,`brand_id`) VALUES
(1, 'Corde à sauter modèle Nike NS54', 'Description de la corde à sauter', 5, 'NONE', 5,3),
(2, 'Haltère modèle HA43', 'Description de l\'haltère HA43', 20, 'NONE', 3, 2),
(3, 'Bande de résistance modèle BR45', 'Description de la bande de résistance BR45', 10, 'NONE', 7, 4);












--  -----------------------------------------------------------
--  -----------------------------------------------------------
--  POUR TOUS
--  -----------------------------------------------------------
--  -----------------------------------------------------------

COMMIT;















