# 🧠 Algorithm Sim

### Interactive Array Algorithm Visualiser built with JavaFX

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![JavaFX](https://img.shields.io/badge/javafx-%23FF0000.svg?style=for-the-badge&logo=javafx&logoColor=white) ![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

---

## 🚀 Overview

Algorithm Sim is a JavaFX-based application designed to visualise and analyse array-based algorithms through interactive execution.

The project uses an event-driven architecture where algorithms generate a deterministic sequence of events. These events are then replayed by a shared visualisation engine, allowing users to step forward, step backward, or play through an algorithm execution.

---

## ✨ Algorithms

| Category            | Algorithms                                                          |
| ------------------- | ------------------------------------------------------------------- |
| 📊 Sorting          | Bubble Sort, Selection Sort, Insertion Sort, Merge Sort, Quick Sort |
| 🔎 Searching        | Planned                                                             |
| 🔄 Array Operations | Planned                                                             |

---

## 🧠 Core Concept

Algorithms do not directly control the visualisation.

Instead, they generate a list of events:

```java
CompareEvent(i, j)
SwapEvent(i, j)
OverwriteEvent(index, value)
PivotEvent(index)
SortedEvent(index)
```

These events are replayed by the JavaFX animation engine to create smooth visual simulations.

---

## ▶️ Running the Project

Requires Java 21+ and Maven.

```bash
mvn clean javafx:run
```
