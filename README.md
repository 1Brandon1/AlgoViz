# 🧠 AlgoViz

### Interactive Array Algorithm Visualiser built with JavaFX

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![JavaFX](https://img.shields.io/badge/javafx-%23FF0000.svg?style=for-the-badge&logo=javafx&logoColor=white) ![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

---

## 🚀 Overview

AlgoViz is a JavaFX-based application designed to visualise and explore **array-based algorithms** through interactive animation.

The application uses an **event-driven architecture** where algorithms generate a deterministic sequence of animation events. These events are then replayed by a shared visualisation engine, allowing users to:

- ▶️ Play algorithm animations
- ⏸️ Pause execution
- ⏭️ Step forward through individual events
- ⏮️ Step backward through execution
- 🔄 Generate new arrays
- 🎯 Select search targets directly from the visualiser
- 📊 View live algorithm statistics

The project focuses exclusively on **array-based sorting and searching algorithms**.

---

## ✨ Algorithms

### 📊 Sorting

| Algorithm      | Supported |
| -------------- | --------- |
| Bubble Sort    | ✅        |
| Selection Sort | ✅        |
| Insertion Sort | ✅        |
| Merge Sort     | ✅        |
| Quick Sort     | ✅        |
| Heap Sort      | ✅        |

### 🔎 Searching

| Algorithm            | Supported |
| -------------------- | --------- |
| Linear Search        | ✅        |
| Binary Search        | ✅        |
| Jump Search          | ✅        |
| Interpolation Search | ✅        |

---

## 🎨 Visualisation

AlgoViz provides visual feedback for different stages of an algorithm's execution.

| Visual State  | Meaning                     |
| ------------- | --------------------------- |
| 🔵 Slate      | Normal array element        |
| 🟠 Amber      | Current comparison          |
| 🟣 Violet     | Pivot                       |
| 🟢 Green      | Sorted / found              |
| 🔴 Red        | Search target not found     |
| ⚫ Dark Slate | Outside active search range |

The visualiser also supports interactive bar selection, allowing users to click an array element and use its value as a search target.

---

## 🧠 Core Architecture

Algorithms do not directly control the visualisation.

Instead, they generate a list of events:

```java
CompareEvent(i, j)
SwapEvent(i, j)
OverwriteEvent(index, value)
PivotEvent(index)
SortedEvent(index)
SortCompleteEvent()
SearchCompareEvent(index)
FoundEvent(index)
NotFoundEvent()
```

These events are passed to the shared `EventPlayer`, which replays them against the `ArrayVisualiser`.

This separation keeps the project modular:

```text
Algorithm
    ↓
Animation Events
    ↓
EventPlayer
    ↓
ArrayVisualiser
    ↓
JavaFX UI
```

The same playback system can therefore be used by multiple algorithms without each algorithm needing to know anything about JavaFX.

---

## 🎮 Controls

| Control  | Function                                   |
| -------- | ------------------------------------------ |
| Generate | Creates a new random array                 |
| Run      | Generates and loads the selected algorithm |
| Play     | Automatically plays the animation          |
| Pause    | Pauses playback                            |
| Step →   | Advances one event                         |
| ← Step   | Moves one event backwards                  |
| Target   | Sets the value to search for               |
| Mode     | Switches between Sorting and Searching     |

---

## 🎨 UI

AlgoViz uses a clean, modern **dark-themed interface** designed to keep the focus on the algorithm visualisation.

The interface includes:

- Dark theme
- Responsive control layout
- Algorithm and mode selectors
- Live status information
- Playback controls
- Interactive array bars
- Hover feedback
- Search target selection
- Algorithm statistics

---

## 🛠️ Technologies

- **Java 21+**
- **JavaFX**
- **Apache Maven**
- Event-driven architecture
- Object-oriented design

---

## ▶️ Running the Project

### Requirements

- Java 21 or later
- Maven

### Run

```bash
mvn clean javafx:run
```

---

## 📁 Project Structure

```text
src/
└── main/
    ├── java/
    │   └── com/brandon/
    │       ├── algorithms/
    │       ├── events/
    │       ├── models/
    │       ├── ui/
    │       └── visualisation/
    │
    └── resources/
        └── styles/
```

The main components are separated by responsibility:

- `algorithms` — Sorting and searching implementations
- `events` — Animation event definitions
- `models` — Array data models
- `visualisation` — Event playback and array rendering
- `ui` — JavaFX interface
- `styles` — Application styling

---

## 🎯 Project Goals

AlgoViz was built to demonstrate how algorithms can be separated from their visual representation using an **event-driven architecture**.

The project focuses on:

- Algorithm visualisation
- Event-driven design
- Clean separation of concerns
- Interactive JavaFX development
- Understanding sorting and searching algorithms

---

## 📌 Project Status

**v1.0.0 — Complete**

AlgoViz currently focuses on array-based algorithms. Graph algorithms and graph visualisation are intentionally outside the scope of this project.
