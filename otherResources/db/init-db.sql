
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--				MEMENTO DIFFICULTES RENCONTREES
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Remarque : les tables doivent être crées au préalable avant de faire les CONSTRAINT NomContrainte FOREIGN KEY
-- On ne peut pas dire que id_role dans la table users pointe vers la table roles si la table roles n'existe pas encore
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
    `gender` ENUM('Féminin', 'Masculin', 'Autre') NOT NULL DEFAULT 'Autre',
    `email_address` varchar(255) NOT NULL,
    `phone_number` varchar(100) NOT NULL,
    `creation_date_time` datetime NOT NULL DEFAULT NOW(),
    `picture_URI` varchar(2083) NULL,
    `active` tinyint(1) NOT NULL DEFAULT 1,
    `coach_degree_info` text NULL,
    `coach_career_start_date` datetime NULL,


    CONSTRAINT PK_USERS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_USERS_username UNIQUE (`username`),
    CONSTRAINT UNIQUE_CONSTRAINT_USERS_email_address UNIQUE (`email_address`),
    CONSTRAINT UNIQUE_CONSTRAINT_USERS_phone_number UNIQUE (`phone_number`),
    CONSTRAINT UNIQUE_CONSTRAINT_USERS_picture_URI UNIQUE (`picture_URI`)




    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `users_groups`
--
DROP TABLE IF EXISTS `users_groups`;
CREATE TABLE IF NOT EXISTS `users_groups` (

    -- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
    `id` int(11) NOT NULL AUTO_INCREMENT,

    `user_id` int(11) NOT NULL,
    `group_id` int(11) NOT NULL,


    CONSTRAINT PK_USERS_GROUPS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_USERS_GROUPS_fkcomposite UNIQUE (`user_id`,`group_id`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `groups`
--

DROP TABLE IF EXISTS `groups`;
CREATE TABLE IF NOT EXISTS `groups` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `label` varchar(100) NOT NULL,
    `description` text NULL,


    CONSTRAINT PK_GROUPS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_GROUPS_label UNIQUE (`label`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Structure de la table `groups_permissions`
--

DROP TABLE IF EXISTS `groups_permissions`;
CREATE TABLE IF NOT EXISTS `groups_permissions` (

-- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
    `id` int(11) NOT NULL AUTO_INCREMENT,


    `group_id` int(11) NULL DEFAULT NULL,
    `permission_id` int(11) NULL DEFAULT NULL,


    CONSTRAINT PK_GROUPS_PERMISSIONS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_GROUPS_PERMISSIONS_fkcomposite UNIQUE (`group_id`,`permission_id`)



    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
CREATE TABLE IF NOT EXISTS `permissions` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `label` varchar(100) NOT NULL,
    `description` text NOT NULL,


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
    `note` text NOT NULL,

    `user_id` int(11) NULL DEFAULT NULL,
    `measurand_id` int(11) NULL DEFAULT NULL,


    CONSTRAINT PK_MEASURES PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_MEASURES_fkcomposite UNIQUE (`user_id`,`measurand_id`)



    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `measurands`
--

DROP TABLE IF EXISTS `measurands`;
CREATE TABLE IF NOT EXISTS `measurands` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `description` text NOT NULL,

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
    `description` text NOT NULL,



    CONSTRAINT PK_UNITS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_MEASURANDS_name UNIQUE (`name`)



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
    `description` text NOT NULL,
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
    `description` text NOT NULL,
    `creation_date_time` datetime NOT NULL DEFAULT NOW(),


    `user_creator_id` int(11) NULL DEFAULT NULL,


    CONSTRAINT PK_TEAMS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_SUBSCRIPTIONS_name UNIQUE (`name`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=`latin1_general_ci`;




-- --------------------------------------------------------

--
-- Structure de la table `measures`
--

DROP TABLE IF EXISTS `measures`;
CREATE TABLE IF NOT EXISTS `measures` (

-- artificial id dans les tables intermediaires pour simplifier java (permet que la table intermédiaire existe en tant que classe java, et non pas en tant qu'annotations dans les 2 autres classes java des tables générant la jointure).
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `creation_date_time` datetime NOT NULL DEFAULT NOW(),
    `measured_value` decimal(10,2) NOT NULL,
    `note` text NULL,

    `user_id` int(11) NULL DEFAULT NULL,
    `measurand_id` int(11) NULL DEFAULT NULL,


    CONSTRAINT PK_MEASURES PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_MEASURES_fkcomposite UNIQUE (`user_id`,`measurand_id`)


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
    `message` text NOT NULL,


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
    `message` text NOT NULL,


    `team_id` int(11) NULL DEFAULT NULL,
    `user_id` int(11) NULL DEFAULT NULL,


    CONSTRAINT PK_TEAM_COMMENTS PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_TEAM_USERS_fkcomposite UNIQUE (`team_id`,`user_id`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;











--  -----------------------------------------------------------
--  -----------------------------------------------------------
--  JEREMY
--  -----------------------------------------------------------
--  -----------------------------------------------------------




-- --------------------------------------------------------

--
-- Structure de la table `adresses`
--

DROP TABLE IF EXISTS `addresses`;
CREATE TABLE IF NOT EXISTS `addresses` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `street_name_fr` varchar(100) NOT NULL,
    `street_name_en` varchar(100) NOT NULL,
    `street_name_nl` varchar(100) NOT NULL,
    `street_number` varchar(10) NOT NULL,
    `street_number_box` varchar(10) NOT NULL DEFAULT '/',



    `city_id` int(11) NULL DEFAULT NULL,



    CONSTRAINT PK_ADDRESSES PRIMARY KEY (`id`)



    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `cities`
--

DROP TABLE IF EXISTS `cities`;
CREATE TABLE IF NOT EXISTS `cities` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name_fr` varchar(100) NOT NULL,
    `name_en` varchar(100) NOT NULL,
    `name_nl` varchar(100) NOT NULL,
    `zip_code` varchar(100)NOT NULL,



    `country_id` int(11) NULL DEFAULT NULL,



    CONSTRAINT PK_CITIES PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_CITIES_zipcode UNIQUE (`zip_code`)



    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `countries`
--

DROP TABLE IF EXISTS `countries`;
CREATE TABLE IF NOT EXISTS `countries` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name_fr` varchar(100) NOT NULL,
    `name_en` varchar(100) NOT NULL,
    `name_nl` varchar(100) NOT NULL,
    `iso_num` varchar(5) NOT NULL,
    `iso_alpha` varchar(5) NOT NULL,



    CONSTRAINT PK_COUNTRIES PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_CONSTRAINT_COUNTRIES_isonum UNIQUE (`iso_num`),
    CONSTRAINT UNIQUE_CONSTRAINT_COUNTRIES_isoalpha UNIQUE (`iso_alpha`)



    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `users_addresses`
--

DROP TABLE IF EXISTS `users_addresses`;
CREATE TABLE IF NOT EXISTS `users_addresses` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `alias` varchar(100) NOT NULL DEFAULT 'None',



    `user_id` int(11) NULL DEFAULT NULL,
    `address_id` int(11) NULL DEFAULT NULL,



    CONSTRAINT PK_COUNTRIES PRIMARY KEY (`id`)



    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `organizations`
--

DROP TABLE IF EXISTS `organizations`;
CREATE TABLE IF NOT EXISTS `organizations` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `description` text NULL ,



    `address_id` int(11) NULL DEFAULT NULL,



    CONSTRAINT PK_COUNTRIES PRIMARY KEY (`id`)



    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `users_organizations`
--

DROP TABLE IF EXISTS `users_organizations`;
CREATE TABLE IF NOT EXISTS `users_organizations` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `valid_from` datetime NOT NULL DEFAULT NOW(),
    `valid_until` datetime NOT NULL DEFAULT '9999-12-31',
    `obsolete_flag` boolean NOT NULL DEFAULT FALSE,



    `user_id` int(11) NULL DEFAULT NULL,
    `organization_id` int(11) NULL DEFAULT NULL,



    CONSTRAINT PK_COUNTRIES PRIMARY KEY (`id`)



    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------

--
-- Structure de la table `workout_places`
--

DROP TABLE IF EXISTS `workout_places`;
CREATE TABLE IF NOT EXISTS `workout_places` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `range` enum('Private','Public','SharedLink','Limted') NOT NULL DEFAULT 'Private',



    `address_id` int(11) NULL DEFAULT NULL,
    `creator_user_id` int(11) NULL DEFAULT NULL,



    CONSTRAINT PK_COUNTRIES PRIMARY KEY (`id`)



    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;




-- --------------------------------------------------------


--
-- Structure de la table `events`
--

DROP TABLE IF EXISTS `events`;
CREATE TABLE IF NOT EXISTS `events` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `begin_date_time` datetime NOT NULL,
    `end_date_time` datetime NOT NULL,
    `status` enum('Available','Full','Cancelled') NOT NULL DEFAULT 'Available',
    `range` enum('Private','Public','SharedLink','Limted') NOT NULL DEFAULT 'Private',
    `creation_date_time` datetime NOT NULL DEFAULT NOW(),
    `description` text NULL,
    `pratical_information` text NULL,
    `name` varchar(100) NOT NULL,



    `organization_id` int(11) NULL DEFAULT NULL,
    `workout_place_id` int(11) NULL DEFAULT NULL,
    `creator_user_id` int(11) NULL DEFAULT NULL,




    CONSTRAINT PK_EVENTS PRIMARY KEY (`id`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;






-- --------------------------------------------------------


--
-- Structure de la table `event_posts`
--

DROP TABLE IF EXISTS `event_posts`;
CREATE TABLE IF NOT EXISTS `event_posts` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `creation_date_time` datetime NOT NULL DEFAULT NOW(),



    `event_id` int(11) NULL DEFAULT NULL,
    `creator_user_id` int(11) NULL DEFAULT NULL,




    CONSTRAINT PK_EVENTS PRIMARY KEY (`id`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;






-- --------------------------------------------------------


--
-- Structure de la table `event_comments`
--

DROP TABLE IF EXISTS `event_comments`;
CREATE TABLE IF NOT EXISTS `event_comments` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `content` text NOT NULL,
    `creation_date_time` datetime NOT NULL DEFAULT NOW(),



    `event_post_id` int(11) NULL DEFAULT NULL,
    `creator_user_id` int(11) NULL DEFAULT NULL,




    CONSTRAINT PK_EVENTS PRIMARY KEY (`id`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;






-- --------------------------------------------------------


--
-- Structure de la table `event_participations`
--

DROP TABLE IF EXISTS `event_participations`;
CREATE TABLE IF NOT EXISTS `event_participations` (

    `id` int(11) NOT NULL AUTO_INCREMENT,
    `status` enum('Pending', 'Confirmed', 'Refused', 'Cancelled') NOT NULL DEFAULT 'Pending',
    `creation_date_time` datetime NOT NULL DEFAULT NOW(),



    `inviter_user_id` int(11) NULL DEFAULT NULL,
    `invitee_user_id` int(11) NULL DEFAULT NULL,
    `event_id` int(11) NULL DEFAULT NULL,




    CONSTRAINT PK_EVENTS PRIMARY KEY (`id`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;






-- --------------------------------------------------------


--
-- Structure de la table `research_areas`
--

DROP TABLE IF EXISTS `research_areas`;
CREATE TABLE IF NOT EXISTS `research_areas` (

    `id` int(11) NOT NULL AUTO_INCREMENT,



    `announcement_id` int(11) NULL DEFAULT NULL,
    `city_id` int(11) NULL DEFAULT NULL,




    CONSTRAINT PK_EVENTS PRIMARY KEY (`id`)


    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;



















































--  -----------------------------------------------------------
--  -----------------------------------------------------------
--  WILLIAM
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
    `description` text  NULL,
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
    `description` text NULL  ,
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
    `description` text NULL ,
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
    `description` text NULL,
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
    `description` text NULL,
    `access_range` ENUM('PRIVATE', 'PUBLIC', 'SHAREDLINK', 'LIMITED') NOT NULL DEFAULT 'PRIVATE',
    `duration_estimation` double NOT NULL DEFAULT -1,
    `calories_estimation` double NOT NULL DEFAULT -1,




    `level_id` int(11) NOT NULL,
    `goal_id` int(11) NOT NULL,
    `training_type_id` int(11) NOT NULL,
    `subscription_id_minimum_rank` int(11) NOT NULL,
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
    `description` text NULL,




    `level_id` int(11) NOT NULL,




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
    `description` text NULL,
    `picture_URI` varchar(2083) DEFAULT NULL,




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
    `details` text NULL,




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
    `description` text NULL,




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
    `description` text NULL,




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
    `description` text NULL,
    `price` decimal(10,2) DEFAULT NULL,
    `referal_buy_URL` varchar(2083) DEFAULT 'NONE',



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

ALTER TABLE users_groups

    ADD(
		CONSTRAINT FK_USERS_GROUPS_USERS FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_USERS_GROUPS_GROUPS FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE groups_permissions

    ADD(
		CONSTRAINT FK_GROUPS_PERMISSIONS_GROUPS FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_GROUPS_PERMISSIONS_PERMISSIONS FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
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
--  JEREMY
--  -----------------------------------------------------------
--  -----------------------------------------------------------


ALTER TABLE addresses

    ADD(
		CONSTRAINT FK_ADRESSES_CITIES FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);





ALTER TABLE cities

    ADD(
		CONSTRAINT FK_CITIES_COUNTRIES FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE users_addresses
    ADD(
		CONSTRAINT FK_USERSADRESSES_USERS FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_USERSADRESSES_ADDRESSES FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE organizations
    ADD(
		CONSTRAINT FK_ORGANIZATION_ADRESSES FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE users_organizations
    ADD(
		CONSTRAINT FK_USERSORGANIZATIONS_USERS FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_USERSORGANIZATIONS_ORGANIZATIONS FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE workout_places
    ADD(
		CONSTRAINT FK_WORKOUTPLACES_ADDRESSES FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_WORKOUTPLACES_CREATORUSERS FOREIGN KEY (`creator_user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE events
    ADD(
		CONSTRAINT FK_EVENTS_ORGANIZATIONS FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_EVENTS_WORKOUTPLACES FOREIGN KEY (`workout_place_id`) REFERENCES `workout_places` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_EVENTS_CREATORUSERS FOREIGN KEY (`creator_user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE event_posts
    ADD(
		CONSTRAINT FK_EVENTPOSTS_EVENTS FOREIGN KEY (`event_id`) REFERENCES `events` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_EVENTPOSTS_CREATORUSERS FOREIGN KEY (`creator_user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE event_comments
    ADD(
		CONSTRAINT FK_EVENTCOMMENTS_EVENTPOSTS FOREIGN KEY (`event_post_id`) REFERENCES `event_posts` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_EVENTCOMMENTS_CREATORUSERS FOREIGN KEY (`creator_user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE event_participations
    ADD(
		CONSTRAINT FK_EVENTPARTICIPATIONS_INVITERUSERS FOREIGN KEY (`inviter_user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_EVENTPARTICIPATIONS_INVITEEUSERS FOREIGN KEY (`invitee_user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_EVENTPARTICIPATIONS_EVENTS FOREIGN KEY (`event_id`) REFERENCES `events` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);




ALTER TABLE research_areas
    ADD(
		-- CONSTRAINT FK_RESEARCHAREA_ANNOUNCEMENTS FOREIGN KEY (`announcement_id`) REFERENCES `announcements` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
		CONSTRAINT FK_RESEARCHAREA_CITIES FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
	);













--  -----------------------------------------------------------
--  -----------------------------------------------------------
--  WILLIAM
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

/*

INSERT INTO `permissions` (`id`, `label`, `abbreviation`, `description`) VALUES
(1, 'DeleteAnyUser', 'D-USER-01', 'Cette permission permet de supprimer n\'importe lequel des comptes-utilisateurs.'),
(2, 'DeleteOwnUser', 'D-USER-02', 'Cette permission permet de supprimer son propre compte-utilisateur.'),
(3, 'DeleteAnyGroup', 'D-GROUP-01', 'Cette permission permet de supprimer un rôle.'),
(4, 'CreateNewGroup', 'C-GROUP-01', 'Cette permission permet de créer un nouveau rôle et de lui attribuer des permissions.'),
(5, 'ReadAnyGroup', 'R-GROUP-01', 'Cette permission permet de lire la liste des rôles.'),
(6, 'CreateNewUser', 'C-USER-01', 'Cette permission permet de créer un nouveau compte utilisateur'),
(7, 'ReadAnyPermission', 'R-PERM-01', 'Cette permission permet de lire la liste des permissions'),
(8, 'DeleteAnyPermission', 'D-PERM-01', 'Cette permission permet de supprimer une permission'),
(9, 'CreateNewPermission', 'C-PERM-01', 'Cette permission permet de créer une nouvelle permission'),
(10, 'UpdateAnyPermission', 'U-PERM-01', 'Cette permission permet de mettre à jour n\'importe laquelle des permissions.'),
(11, 'UpdateAnyGroup', 'U-GROUP-01', 'Cette permission permet de mettre à jour n\'importe lequel des rôles'),
(12, 'UpdateAnyUser', 'U-USER-01', 'Cette permission permet de mettre à jour n\'importe lequel des comptes utilisateurs'),
(13, 'ReadAnyUser', 'R-USER-01', 'Cette permission permet de lire la liste des utilisateurs');



INSERT INTO `groups` (`id`, `label`, `abbreviation`, `description`) VALUES
(1, 'Eleve 1', 'ELE-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant qu\'élèves de l\'école'),
(2, 'Parent 1', 'PAR-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant que parents d\'élève'),
(3, 'Professeur 1', 'PRO-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant que professeurs'),
(4, 'Secretaire 1', 'SEC-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant que secrétaires'),
(5, 'Directeur 1', 'DIR-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant que directeurs d\'école.'),
(6, 'Administrateur 1', 'ADM-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant qu\'administrateur.'),
(7, 'Stagiaire 1', 'STA-01', 'Ce rôle est attribué aux utilisateurs identifiés en tant que professeurs stagiaires.'),
(8, 'Stagiaire 2', 'STA-02', 'Ce rôle est attribué aux utilisateurs identifiés en tant que secrétaires stagiaires.');



INSERT INTO `groups_permissions` (`id`, `id_group`, `id_permission`) VALUES
(6, 6, 1),
(7, 6, 2),
(5, 6, 3),
(2, 6, 4),
(9, 6, 5),
(3, 6, 6),
(8, 6, 7),
(4, 6, 8),
(1, 6, 9),
(11, 6, 10),
(12, 6, 11),
(13, 6, 12),
(10, 6, 13);



INSERT INTO `users` (`id`, `first_name`, `last_name`, `username`, `password`, `phone_number`, `birthdate`, `gender`, `email_address`, `active`, `inscription_date`, `title`, `photo`, `id_address`, `id_group`, `id_school`, `id_parent`) VALUES
(1, 'Viktor', 'Ganise', 'ladministrateur01', 'mdp', NULL, '1991-01-01', 'Neutre', 'viktor.ganise@gmail.com', 1, '2020-08-09 17:46:03', 'Mr. l\'Administrateur', NULL, NULL, 6, NULL, NULL),
(2, 'Jean', 'Seigne', 'leprofesseur01', 'mdp', NULL, '1990-01-01', 'Masculin', 'jean.seigne@gmail.com', 1, '2020-08-09 17:46:03', '', NULL, NULL, 3, NULL, NULL),
(3, 'Arya', 'Secret', 'lasecretaire01', 'mdp', NULL, '1994-01-01', 'Féminin', 'arya.secret@gmail.com', 1, '2020-08-09 17:46:03', 'Mme la Secrétaire', NULL, NULL, 4, NULL, NULL),
(4, 'Moundir', 'Ecteur', 'ledirecteur01', 'mdp', NULL, '1985-02-02', 'Masculin', 'moundir.ecteur@gmail.com', 1, '2020-08-09 17:46:03', 'Mr. le Directeur', NULL, NULL, 5, NULL, NULL),
(5, 'Gaspard', 'Ent', 'leparent01', 'mdp', NULL, '1970-03-03', 'Masculin', 'gaspard.ent@gmail.com', 1, '2020-08-09 17:46:03', 'Mr. le Parent', NULL, NULL, 2, NULL, NULL),
(6, 'Michael', 'Eve', 'leleve01', 'mdp', NULL, '2005-05-05', 'Masculin', 'michael.eve@gmail.com', 1, '2020-08-09 17:46:03', 'L\'Élève', NULL, NULL, 1, NULL, NULL),
(7, 'Callista', 'Giaire', 'lastagiaire01', 'mdp', NULL, '2002-02-02', 'Féminin', 'callista.giaire@gmail.com', 1, '2020-08-09 17:46:03', 'Mme la Stagiaire-professeure', NULL, NULL, 7, NULL, NULL),
(8, 'Kosta', 'Giaire', 'lestagiaire01', 'mdp', NULL, '2001-01-01', 'Masculin', 'kosta.giaire@gmail.com', 1, '2020-08-09 17:46:03', 'Mr le Stagiaire-secrétaire', NULL, NULL, 8, NULL, NULL),
(9, 'Enf', 'Ent', 'lenfant01', 'mdp', NULL, '2004-04-04', 'Masculin', 'enf.ent@gmail.com', 1, '2020-08-09 17:49:31', 'Mr.', NULL, NULL, 1, NULL, 5);




*/
INSERT INTO `subscriptions` (`id`, `name`,`price_per_month`,`description`,`rank`) VALUES
(1, 'Bronze',5,'Blabla desc', 1),
(2, 'Silver',10,'Blabla desc',2),
(3, 'Gold',15,'Blabla desc',3),
(4, 'Platinum',20, 'Blabla desc',4),
(5, 'Diamond',25,'Blabla desc',5);







--  -----------------------------------------------------------
--  -----------------------------------------------------------
--  JEREMY
--  -----------------------------------------------------------
--  -----------------------------------------------------------
























--  -----------------------------------------------------------
--  -----------------------------------------------------------
--  WILLIAM
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
' ||
    '
INSERT INTO `exercises` (`id`, `name`, `description`,`level_id`) VALUES
(1, ''Developpé-couché aux haltères'', ''Description du benchpress aux haltères '', 1),
(2, ''Squat'', ''Description du squat '', 2),
(3, ''Soulevé de terre'', ''Description du deadlift'', 2),
(4, ''Developpé-couché à la barre'', ''Description du benchpress à la barre '', 1);




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















