```markdown
# Simulation N-Corps avec Quarkus

Ce projet implémente une **simulation d'anticorps** basée sur une simulation N-corps, en utilisant **Quarkus**, un framework Java rapide et léger. L'application est développée en **Java** pour le backend et communique avec un frontend.

## 🛠 Technologies Utilisées
- **Quarkus** – Framework backend ultrarapide
- **Gradle** – Outil de gestion de construction
- **JUnit** – Tests unitaires et d'intégration
- **RESTEasy** – API REST en Jakarta REST

## 🚀 Lancer l'application en mode développement

Lancez l'application en mode développement avec :

```shell
./gradlew quarkusDev
```

## 📦 Packaging et Exécution

L'application peut être packagée avec :

```shell
./gradlew build
```

## 📡 API REST
L'application expose une API REST pour interagir avec la simulation. Voici les principaux endpoints :

### Ajouter une particule
- **POST** `/simulation/add`
- **Corps de la requête** :
  ```json
  {
    "x": 1.0,
    "y": 2.0,
    "vx": 0.5,
    "vy": -0.3,
    "mass": 10.0
  }
  ```

### Récupérer les particules
- **GET** `/simulation/particles`

### Mettre à jour la simulation
- **POST** `/simulation/update`

### Mettre à jour la position du soleil (masse centrale)
- **POST** `/simulation/update-sun`
    - Corps de la requête :
  ```json
  {
    "x": 0.0,
    "y": 0.0,
    "mass": 5e15
  }
  ```

## Tests et Qualité
- Les **tests unitaires** sont définis dans `src/test/java/unit/`.
- Les **tests API** sont définis dans `src/test/java/api/`.
- Les **tests d'intégration** sont définis dans `src/test/java/integration/`.
- Exécutez tous les tests avec :
  ```shell
  ./gradlew test
  ```

## 📂 Structure du Projet
```
Project_Test_Trial/
├── src/
│   ├── main/java/org/acme/
│   │   ├── Particle.java   # Modélisation d'une particule
│   │   ├── SimulationService.java  # Gestion de la simulation
│   │   ├── SimulationResource.java  # API REST
│   ├── test/java/ # Dossiers de test
│   ├── native-test/java/ # Tests natifs
├── build.gradle  # Configuration Gradle
├── README.md  # Ce fichier
```

## 📚 Guides 
- **Quarkus et RESTEasy Classic JSON-B** : [Guide](https://quarkus.io/guides/rest-json)
- **Introduction à RESTEasy** : [Guide](https://quarkus.io/guides/resteasy)
- **Documentation officielle Quarkus** : [Site Web](https://quarkus.io/guides/)
```

