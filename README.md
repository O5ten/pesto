# PESTO

A Pastebin/Gist clone that you can run as your own service. 
Currently under development. 

## How to get running
```
gradle serve 
```

## Implemented features
- Syntax highlighting
- Persistence Support, CRUD exists everywhere but UI
- List pastes
- Modify pastes
- Remove pastes
- Update pastes
- Categorize pastes

## Roadmap
- Make voting-system update entry
- Make voting only possible once per entry and origin
- Make latest view
- Make top-voted pastes view
- Make MongoDB credentials.cfg instead of hardcoded
- Make paste view microlight div contenteditable and
  custom angular directive to pick up keyups as an ng-change similar 

## Requires
An accessable MongoDB instance with usr/pw mongodb/mongodb


## Powered by 
- MongoDB 2.4.9
- Angular JS 1.5.6
- Spark Java 2.3
- GMongo 1.3
- Microlight 0.0.1
- Groovy 2.4.4
- Gradle 2.11

Most likely a few more buzzwords until it's done.
