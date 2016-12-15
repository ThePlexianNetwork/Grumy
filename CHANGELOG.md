# Changes By Commit/Release

Latest:
1. Added sounds for digging and placing blocks.

96cd49c377759638a2fe4a94c1930798f40af144 (12/14/16):
1. Added Alpha variable to text rendering
2. Removed Keys from world configuration
3. Re-implemented world generator
4. Added checkboxes to the launcher for "Load world" and "Level editor mode"
5. Fixed shaders by setting the parameter ```u_texture``` to 0 before binding shader and by removing ```* color``` part in ```gl_FragColor = texColor * color``` in the fragment shaders.
6. Fixed warnings in ```Location``` and ```World``` by creating getters/setters and casting ```HashMap```s correctly.
7. Removed all alpha settings from code and changed completely to shaders.
8. Fixed the world so that the generated floor doesn't only extend to the initial ```Game.WORLD_SIZE``` in chunks.
9. Fixed RGBA parsing from string if a value is missing.
10. Fixed the world loader/saver to rely on user input. The game was somewhere between a single-world and multiworld setup, but not its multiworld
11. Fixed world generator so now "bottom" chunks are reall cool looking.
12. Added basic Maven support
13. Working on "paulscode" sound system implemenation. Should be done in next commit.
14. Added licenses to LWJGL and paulscode libraries. Adding Snakeyaml and NBT later
15. Created a bug where dialog isn't rendered if there aren't any non-transparent tiles on the screen.
