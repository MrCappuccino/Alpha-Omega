# MiniMax Alpha-Beta Pruning for International Draughts

## Build
1. Build DraughtsPlugin
```
ant -f /home/ongo/Documents/uni/2ID90/assignment1/DraughtsPlugin -Dnb.internal.action.name=rebuild clean jar
```
2. Build AICompetition
```
ant -f /home/ongo/Documents/uni/2ID90/assignment1/AICompetition -Dnb.internal.action.name=rebuild clean jar
```

## Run
```
ant -f /home/ongo/Documents/uni/2ID90/assignment1/AICompetition -Dnb.internal.action.name=run run
```

### All at once
```
ant -f /home/ongo/Documents/uni/2ID90/assignment1/DraughtsPlugin -Dnb.internal.action.name=rebuild clean jar && ant -f /home/ongo/Documents/uni/2ID90/assignment1/AICompetition -Dnb.internal.action.name=rebuild clean jar && ant -f /home/ongo/Documents/uni/2ID90/assignment1/AICompetition -Dnb.internal.action.name=run run
```

