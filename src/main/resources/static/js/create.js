const modifiable = document.getElementById("Modifiable");
const number = document.getElementById("number_2_5");
const text = document.getElementById("text_2_5");
const digitSelectionCells = document.getElementsByClassName("digit-selection-cell");
const sudokuGridCells = document.getElementsByClassName("sudoku-cell");

var selected = null;
var given = true;

modifiable.innerHTML = "Modified"

function setSelectedValue(value) {
    if (selected != null) {
        selected.innerHTML =
            "<p>" + value + "</p>\n" +
            "<input type='hidden' name='number[]' value='" + value + "'/>\n" +
            "<input type='hidden' name='given[]' value='" + given + "'/>"
    }
}

for (let i = 0; i < digitSelectionCells.length; i++) {
    let cell = digitSelectionCells[i];
    cell.addEventListener("click", function () {
        console.log(this.textContent);
        setSelectedValue(this.textContent);
    })
}

for (let i = 0; i < sudokuGridCells.length; i++) {
    let cell = sudokuGridCells[i];
    cell.addEventListener("click", function () {
        selected = this;
        console.log(selected.childNodes);
    })
}
