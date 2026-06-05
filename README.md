# space-invaders-java

#### This project is as an activity of the Object Oriented Programming (SCC0504-101-2026) subject on the Instituto de Ciências Matemáticas e de Computação from the Universidade de São Paulo (USP). The main goal is to recriate the known "Alien invaders" using Java and LibGDX.

## Developers:
<div style="display:inline-block; padding:1rem;">
    <img src="https://github.com/4i6caetano.png" style="height: 60px; border-radius:40px">
    <ul style="display:inline-block">
        <li><span>João Pedro Correia Caetano</span>
    </ul>
</div>

<div style="display:inline-block; padding:1rem;">
    <img src="https://github.com/ItzCrynix.png" style="height: 60px; border-radius:40px">
    <ul style="display:inline-block">
        <li><span>Cainan Loyola Schiavolin</span>
    </ul>
</div>

---

The goal and requisites are the following:

### Objective
Apply OOP design patterns in a game context, use LibGDX for screen management and graphics, and implement multi-level progression and local cooperative gameplay.

### Required Features
- Game Setup
- Main menu with “New Game”, “Load Game”, and “Exit”.
- LibGDX screen manager to transition between menus and gameplay.
- Core Game Mechanics
- Players move their ships left/right and fire projectiles upward.
- Alien formations move toward the players and drop bombs periodically.
 -Collision detection: projectiles destroy aliens; alien bombs or bodies destroy player ships.
- Two-Player Co-op
- Two players share the screen using different keyboard keys.
- Both ships have individual lives; the game ends when all lives are lost.
- Levels and Difficulty
- At least 3 levels with progressively faster aliens and/or more complex movement patterns.
- Brief “Level Complete” screen between levels.
- Scoring and Lives
- Points for each alien destroyed; bonus points for level completion.
- Each player starts with 3 lives.
- Real-time score and lives are displayed on screen.
- Save and Load
- Save current game progress (level, scores, lives) to a file.
- Load a previously saved game from the main menu.
  - Optional Enhancements
    1. Power-ups: rapid fire, shield, bomb drop.
    2. Network multiplayer using LibGDX networking (players on separate computers).
    3. Boss enemy at the end of each level.

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