# International Draughts Bot

# Features
* MiniMax
* Alpha-Beta pruning
* Iterative deepening
* Board state heuristic
* Material heuristic

# How to run project
First cd into the project directory, then:
## Build
1. Build DraughtsPlugin
```
ant -f DraughtsPlugin -Dnb.internal.action.name=build jar
```
2. Build AICompetition
```
ant -f AICompetition -Dnb.internal.action.name=build jar
```

## Run
```
ant -f AICompetition -Dnb.internal.action.name=run run
```

# All at once
```
ant -f DraughtsPlugin -Dnb.internal.action.name=build jar && ant -f AICompetition -Dnb.internal.action.name=build jar && ant -f AICompetition -Dnb.internal.action.name=run run
```
