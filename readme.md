# Conway's Game of Life

## Project decription

Game of Life is a project made for Novomatic as a recruitment task showcasing designing, programming and documenting a project in Java. 
It implements basic main rules of the game which can be red [here](https://en.wikipedia.org/wiki/Conway's_Game_of_Life)

## Usage

Choose type of pattern: either random or set coordinates

### Set coordinates

Create JSON array file with position coordinates at which the cells will be alive



#### example

<style>
div.sourceCode::before {
    content: attr(data-filename);
    display: block;
    background-color: #cfeadd;
    font-family: monospace;
}

</style>
```{.json filename="resources/Example.json"}
[
  {"x": 10, "y": 5},
  {"x": 11, "y": 5},
  {"x": 12, "y": 6},
  {"x": 13, "y": 7},
  {"x": 13, "y": 8},
  {"x": 13, "y": 9},
  {"x": 12, "y": 10},
  {"x": 11, "y": 11},
  {"x": 10, "y": 11}
]
```