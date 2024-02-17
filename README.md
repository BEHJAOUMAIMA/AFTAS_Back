Backend du Projet Club Aftas - Gestion de Compétitions
Ce document décrit la partie backend du projet Club Aftas - Gestion de Compétitions, développée en utilisant Spring Boot et Spring Security.

Technologies Utilisées
Spring Boot: Framework Java pour le développement d'applications web et d'API RESTful. Spring Boot facilite la configuration et la mise en place rapide de projets Spring.

Spring Security: Module de sécurité de Spring qui fournit des fonctionnalités d'authentification, d'autorisation et de protection contre les vulnérabilités de sécurité courantes.

Structure du Projet
La structure du projet backend est organisée de manière à séparer les différentes responsabilités et fonctionnalités. Voici un aperçu de la structure typique du projet :

css
Copy code
aftas-backend/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com.example.clubaftas/
│   │   │   │   ├── web.rest/
│   │   │   │   │   ├── AuthRest.java
│   │   │   │   │   ├── CompetitionRest.java
│   │   │   │   │   ├── UserRest.java
│   │   │   │   │   └── ...
│   │   │   │   ├── domain/
│   │   │   │   │   ├── Competition.java
│   │   │   │   │   ├── User.java
│   │   │   │   │   └── ...
│   │   │   │   ├── repository/
│   │   │   │   │   ├── CompetitionRepository.java
│   │   │   │   │   ├── UserRepository.java
│   │   │   │   │   └── ...
│   │   │   │   ├── security/
│   │   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   │   ├── JwtAuthorizationFilter.java
│   │   │   │   │   └── SecurityConfig.java
│   │   │   │   ├── service/
│   │   │   │   │   ├── impl/
│   │   │   │   │   │     ├── AuthServiceImpl.java
│   │   │   │   │   │     ├── CompetitionServiceImpl.java
│   │   │   │   │   │     └── ...
│   │   │   │   │   ├── AuthService.java
│   │   │   │   │   ├── CompetitionService.java
│   │   │   │   │   ├── UserService.java
│   │   │   │   │   └── ...
│   │   │   │   │
│   │   │   └── Application.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── ...
│   └── test/
│       └── ...
└── pom.xml
Sécurité
Stateless Session Management
L'authentification est gérée de manière stateless en utilisant JSON Web Tokens (JWT).
CORS (Cross-Origin Resource Sharing)
La configuration de Spring Security permet de gérer les requêtes CORS pour autoriser les requêtes provenant de domaines spécifiques.
CSRF (Cross-Site Request Forgery)
Des mesures de protection contre les attaques CSRF sont mises en place, y compris l'utilisation de jetons anti-CSRF et des filtres pour valider les en-têtes de requête.
JWT (JSON Web Tokens)
Un filtre Spring Security est implémenté pour gérer l'authentification basée sur les jetons JWT, assurant la validation, l'intégrité et l'extraction des informations d'identification des utilisateurs à partir des jetons.
Déploiement
Pour déployer l'application backend, assurez-vous d'avoir configuré correctement votre environnement Java et Maven. Exécutez ensuite la commande Maven suivante à la racine du projet :

bash
Copy code
mvn spring-boot:run
Cela démarrera le serveur backend et rendra l'API accessible via les endpoints configurés.

Pour plus d'informations sur les endpoints et l'utilisation de l'API, veuillez consulter la documentation appropriée.