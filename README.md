# space-invaders-java

#### This project is as an activity of the Object Oriented Programming (SCC0504-101-2026) subject on the Instituto de Ciências Matemáticas e de Computação from the Universidade de São Paulo (USP). The main goal is to recriate the known "Space invaders" using Java and LibGDX.

## Developers:
<div style="display:inline-block; padding:1rem;">
    <a href="https://github.com/https://github.com/4i6caetano">
    <img src="https://github.com/4i6caetano.png" style="height: 60px; border-radius:40px">
    <ul style="display:inline-block">
        <li><span>João Pedro Correia Caetano</span>
        <li><span>16987067</span>
    </ul>
</div>

<div style="display:inline-block; padding:1rem;">
    <a href="https://github.com/https://github.com/ItzCrynix">
    <img src="https://github.com/ItzCrynix.png" style="height: 60px; border-radius:40px">
    <ul style="display:inline-block">
        <li><span>Cainan Loyola Schiavolin</span>
        <li><span>15444319</span>
    </ul>
</div>

<div style="display:inline-block; padding:1rem;">
    <a href="https://github.com/kawancosta1">
    <img src="https://github.com/kawancosta1.png" style="height: 60px; border-radius:40px"/>
    <ul style="display:inline-block">
        <li><span>Kawan da Silva Costa</span>
        <li><span>15510661</span>
    </ul>
</div>

---

The goal and requisites are the following:

### Objective
Apply OOP design and use LibGDX for screen management, graphics, and input. Implement game entity classes with clear inheritance and encapsulation.

### Required Features
1. Game Setup
- Main menu with 'New Game' and 'Exit'.
- LibGDX screen manager for transitions between menu, gameplay, and game over.
2. Core Game Mechanics
- Player moves left/right and fires projectiles upward.
- Alien formations advance toward the player and drop bombs.
- Collision detection: projectiles destroy aliens; aliens destroy the player ship.
3. Levels and Difficulty
- At least 2 levels with progressively faster aliens.
- Brief 'Level Complete' screen between levels.
4. Scoring and Lives
- Points per alien destroyed.
- Player starts with 3 lives; the game ends when all lives are lost.
- Score and lives always visible on screen.

  - Optional Enhancements
    1. Two-player co-op (shared screen, different keys).
    2. Power-ups: rapid fire or shield.
    3. Save/load game progress to a file.


## LibGDX

### Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

### Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
