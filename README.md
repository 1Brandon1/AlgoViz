# 🧠 Algorithm Sim

### Interactive Algorithm Visualizer built with JavaFX

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![JavaFX](https://img.shields.io/badge/javafx-%23FF0000.svg?style=for-the-badge&logo=javafx&logoColor=white) ![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

---

## 🚀 Overview

Algorithm Sim is a JavaFX-based visualizer designed to demonstrate how algorithms work through real-time animation and step-by-step execution.

The project uses an event-driven architecture where algorithms generate animation events that are rendered by a shared visualization engine.

---

## ✨ Algorithms

| Category     | Algorithms                                          |
| ------------ | --------------------------------------------------- |
| 📊 Sorting   | Bubble Sort, Selection Sort, Merge Sort, Quick Sort |
| 🔎 Searching | Linear Search, Binary Search                        |
| 🌐 Graphs    | BFS, DFS, Dijkstra’s, Prim’s, Topological Sort      |

---

## 🧠 Core Concept

Algorithms generate events such as:

```java
Compare(i, j)
Swap(i, j)
Visit(node)
```

These events are replayed by the JavaFX animation engine to create smooth visual simulations.

---

## ▶️ Running the Project

Requires Java 21+ and Maven.

```bash
mvn clean javafx:run
```
