# MiniMax Alpha-Beta Pruning for International Draughts

# How to run project
First cd into the project directory, then:
## Build
1. Build DraughtsPlugin
```
ant -f DraughtsPlugin -Dnb.internal.action.name=rebuild clean jar
```
2. Build AICompetition
```
ant -f AICompetition -Dnb.internal.action.name=rebuild clean jar
```

## Run
```
ant -f AICompetition -Dnb.internal.action.name=run run
```

# All at once
```
ant -f DraughtsPlugin -Dnb.internal.action.name=rebuild clean jar && ant -f AICompetition -Dnb.internal.action.name=rebuild clean jar && ant -f AICompetition -Dnb.internal.action.name=run run
```
