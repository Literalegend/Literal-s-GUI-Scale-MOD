# HUD Scale Editor — Fabric 1.21.11

## Implemented in v0.2
- `I` opens the editor only during normal gameplay; it does not steal `i` while chat/text screens are open.
- HUD element registry for Hotbar, Scoreboard, Chat, Boss Bar, Status Effects, and a generic Mod HUD/Overlay slot.
- Click + drag editor boxes.
- Select an element and press `-` / `=` to scale it from 25% to 400%.
- `R` resets the selected element.
- Positions/scales persist in `.minecraft/config/hudscale.json`.

## Next phase
The editor/config system is now present. The next phase is render hooks/mixins that apply these transforms to the *real* vanilla HUD and compatible third-party overlays. The Mod HUD box is currently an editor target, not universal interception yet.

## Build
Java 21. Run `./gradlew build` in the project after adding/generating a Gradle wrapper, or import it into an IDE/Gradle environment. Output is under `build/libs/`.
