# Projet d'intégration ATC 2020-2021

mot de passe pour tous les utilisateurs: "mdp"

## Respecte les consignes données par Mr Diana
- utilisation des règles de navigation explicites `navigation-rule` dans `faces-config.xml`
- le mot de passe est crypté/décrypté lors du login: il n'est pas stocké en clair
- les transactions JPA sont gérées dans les controllers, 
  donc dans les beans du point de vue JSF
- l'UI doit être jolie: soit par du styling CSS personnel, soit par Bootstrap
- les menus et écrans doivent logiquement s'enchaîner
- l'UI doit être responsive pour un écran HD desktop (non mobile)  
- l'internationalization (i18n) des menus et des messages est gérée par 2 Locales, fr et be,
  dans 2 messages.properties différents
- les messages d'erreur et de validation doivent être bien gérés/affichés
- les exceptions ne sont pas gérées à la manière JSF mais de manière classique
- ne pas faire de composants JSF custom
- ne pas utiliser l'annotation @FlashScope sur les beans

## TODOs
### login
- Cacher la navbar si non login
- Que boostrap soit reconnu si pas login
- Page d’erreur si login échoué
- Login Validator + error messages
- Ajouter Bouton logout
- Cacher le menu permissions si l’utilisateur n’est pas admin
### other
- remplir le messages_fr.properties
- center le page content avec le container bootstrap


  
  
  
  
