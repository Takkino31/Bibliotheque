# Système de Gestion de Bibliothèque

Ce projet implémente un système de gestion de bibliothèque en Java, permettant de gérer les livres, les utilisateurs et les emprunts.

## Fonctionnalités

- Gestion des livres : ajout, mise à jour et suppression de livres
- Gestion des utilisateurs : ajout, modification de l'éligibilité et suppression d'utilisateurs
- Gestion des emprunts : emprunt et retour de livres
- Recherche de livres par titre, auteur ou ISBN
- Affichage de la liste des utilisateurs et des livres empruntés par un utilisateur
- Affichage des statistiques de la bibliothèque (nombre total de livres, de livres empruntés et de livres disponibles)

## Classes principales

- `Book` : Représente un livre avec son titre, auteur, année de publication et ISBN.
- `User` : Représente un utilisateur avec son nom, son ID et son statut d'éligibilité pour emprunter des livres.
- `Library` : Gère la collection de livres, les emprunts, les utilisateurs et les statistiques de la bibliothèque.
- `LibraryException` et ses sous-classes : Gèrent les exceptions liées à la bibliothèque, comme la non-disponibilité d'un livre ou l'inéligibilité d'un utilisateur.
- `LibraryTerminalUI` : Fournit une interface en ligne de commande pour interagir avec la bibliothèque.
- `UserManager` : Gère les utilisateurs de la bibliothèque.

## Utilisation

1. Clonez le dépôt du projet.
2. Ouvrez le projet dans votre environnement de développement Java.
3. Exécutez la classe `Main` pour lancer l'interface utilisateur en ligne de commande.
4. Suivez les instructions affichées dans le terminal pour gérer les livres, les utilisateurs et les emprunts.

## Contribuer

Les contributions sont les bienvenues ! Si vous souhaitez améliorer le projet, n'hésitez pas à soumettre une pull request.