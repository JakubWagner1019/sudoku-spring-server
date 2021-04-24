const modifiable = document.getElementById("Modifiable");
const digitSelectionCells = document.getElementsByClassName("digit-selection-cell");
const sudokuGridCells = document.getElementsByClassName("sudoku-cell");
const removeButton = document.getElementById("remove-button");

let selected = null;
let given = true;

function setSelectedValue(value) {
    if (selected != null) {
        selected.innerHTML =
            "<p>" + value + "</p>\n" +
            "<input type='hidden' name='number[]' value='" + value + "'/>\n" +
            "<input type='hidden' name='given[]' value='" + given + "'/>"
    }
}

function toggleGiven(){
    given = !given;
}

function clearSelected() {
    selected.innerHTML =
        "<p></p>\n" +
        "<input type='hidden' name='number[]'/>\n" +
        "<input type='hidden' name='given[]'/>"
}

function main(){
    modifiable.innerHTML = "Modified"

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

    removeButton.addEventListener("click", function (){
        clearSelected()
    })
}

main();
