# redacted
Redacted is a lightweight Java game made with [LWJGL](https://www.lwjgl.org/) 

## Purpose
This game serves as a purpose to create a FPS game where you shoot in arenas and combat with other players. This game should be easy, fun, and quick, and serves as a lightweight game.

## Components
This game uses [LWJGL](https://www.lwjgl.org/) and is coded in Java (obviously). This library contains OpenGL, JOML, and other parent components. [Netty](https://netty.io/) is used(could be) for client / server communication utility, as this game is a multiplayer game.

## Design
There are no specific rules of this game. You play, you join a server, and you compete with other players by shooting others. This game does not rely on graphics, we will be using simple shapes and geometry for the background, model, and any form of shaping. 

### Mechanics
Player shoot each other. Every player only has one health (basically one shot then you are dead). Each game lasts 10 minutes, and the person who kills the most wins. (Basic FFA mode).

### Currency
`Qubit` is the currency name for this game. There is a "" in the center of the map which players can stay there and collect `Qubit`. This serves as an alternative gaming routine other than simply combating with other players (like KOTH gamemodes)
The players uses this currency to purchase weapons, and they can be converted to XP.

### Weapons
all and items are shown in todo list. 

### Shooting
Shooting in this game is very similar to other games. You have to stand still to shoot, but compared to other games, the weapon accuracy is more accurate.

### Movement
Every player can be seen as a hitbox, and the bullet collision detects with the hitbox. The player can walk, sprint, jump, and crouch (basic movements of every game)

### Communication
Netty is used for basic server / client communication. The client sends position packets and the server responds. Actual collisions are being checked in the server. this could change later due.

### Anticheat
two words: idk, idc
