# Projet d'intégration ATC 2020-2021

- Mot de passe pour tous les utilisateurs: `Password1@` 
- *username* d'utilisateurs pré-enregistrés: `utilisateur01`, `coach01`, `admin01`, `admin02`

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


  
  
  
  
