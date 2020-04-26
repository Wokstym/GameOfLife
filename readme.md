# Conway's Game of Life

[![](https://img.shields.io/badge/Maven-3.6.1-yellow)](https://maven.apache.org)
[![](https://img.shields.io/badge/Project_Lombok-1.18.12-blue)](https://mvnrepository.com/artifact/org.projectlombok/lombok)
[![](https://img.shields.io/badge/Gson-2.8.6-orange)](https://mvnrepository.com/artifact/com.google.code.gson/gson)
[![](https://img.shields.io/badge/JavaFX_Graphics-14.0.1-green)](https://mvnrepository.com/artifact/org.openjfx/javafx-graphics)
[![](https://img.shields.io/badge/JSON.simple-1.1.1-red)](https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple)

## Project description

Game of Life is a project made for Novomatic as a recruitment task showcasing designing, programming and documenting a project in Java. 
It implements basic main rules of the game which can be red [here](https://en.wikipedia.org/wiki/Conway's_Game_of_Life)

## Showcase 

![](res/GameOfLife.gif)

## Usage

Press Enter to switch pattern

### Adding new patterns

Choose type of pattern: either random or set coordinates

### Set coordinates

Create JSON array file with position coordinates at which the cells will be alive

#### **`resources/Example1.json`**
```json
[
  {"x": 10, "y": 5},
  {"x": 11, "y": 5},
  {"x": 12, "y": 6}
]
```

### Random

Create JSON file with attribute defining ratio of alive cells to all cells

#### **`resources/Example2.json`**
```json
{
  "aliveToAllRatio": 0.25
}
```

Define this file in json settings file 

#### **`resources/settings.json`**
```json
{
  "width": 50,
  "height": 50,
  "frequency": 30,
  "patterns": [
    "RandomPattern.json",
    "QueenBeePattern.json",
    "TumblerPattern.json",
    "Example1.json",
    "Example2.json"
  ]
}
```

Run Simulation ;)

## Contributors ✨

<table>
    <tr>
        <td align="center">
            <a href="https://github.com/Wokstym">
                <img src="https://avatars2.githubusercontent.com/u/44115112?s=460&u=2fea6d808fb949060aa499dad3e3365608bb5c40&v=4" width="100px;" alt=""/><br />
                <sub><b>Grzegorz Poręba</b></sub>
            </a><br />
        </td>
    </tr>
</table>


