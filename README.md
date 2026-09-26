# randobotjava

[![CI](https://github.com/hippi345/randobotjava/actions/workflows/ci.yml/badge.svg)](https://github.com/hippi345/randobotjava/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

A JavaFX desktop game where a robot hunts for treasure on a grid. You can play manually, step move-by-move, or let autoplay run until the bot finds the loot.

## Features

- Configurable grid size (1–15, default 5)
- Manual play: direction buttons or “Next move” (bot chooses intelligently)
- Autoplay until the treasure is found
- Reset and return to the main menu

## Requirements

- **JDK 21** (LTS)
- **Maven 3.9+** (for builds)
- A display for the JavaFX UI (desktop environment)

No API keys, environment variables, or network access are required.

## Setup

```bash
git clone https://github.com/hippi345/randobotjava.git
cd randobotjava
mvn -B package
```

## Run

```bash
mvn javafx:run
```

Or after packaging:

```bash
mvn -B package
java --module-path "$HOME/.m2/repository/org/openjfx/javafx-controls/21.0.6/javafx-controls-21.0.6.jar:$(dirname $(find ~/.m2/repository/org/openjfx -name 'javafx-graphics*.jar' | head -1))" --add-modules javafx.controls,javafx.graphics -cp target/randobotjava-1.0.0-SNAPSHOT.jar sample.Main
```

Using the Maven JavaFX plugin (`mvn javafx:run`) is the simplest option.

## Tests and linting

```bash
# Unit tests (offline, no credentials)
mvn test

# Google Java Format via Spotless
mvn spotless:check
mvn spotless:apply   # auto-format
```

## Project structure

```
├── pom.xml                          # Maven build (Java 21, JavaFX, JUnit 5)
├── src/main/java/sample/
│   ├── Main.java                    # JavaFX application entry
│   ├── Game.java                    # Game rules and turns
│   ├── View.java                    # UI and grid rendering
│   ├── Constants.java               # Grid defaults
│   ├── interfaces/                  # Game and point contracts
│   └── models/                      # Bot, treasure, grid types
└── src/test/java/                   # JUnit 5 tests
```

## License

MIT License — see [LICENSE](LICENSE) (Copyright © 2026 Joel Shearon).
