const modifiable = document.getElementById("Modifiable");
const digitSelectionCells = document.getElementsByClassName("digit-selection-cell");
const sudokuGridCells = document.getElementsByClassName("sudoku-cell");
const removeButton = document.getElementById("remove-button");

const selectedClassName = "selected-sudoku-cell";
const unselectedClassName = "unselected-sudoku-cell";

let selected = null;
let given = true;

function setSelectedValue(value) {
    if (selected != null) {
        selected.innerHTML =
            "<p>" + value + "</p>" +
            "<input type='hidden' name='number[]' value='" + value + "'/>" +
            "<input type='hidden' name='given[]' value='" + given + "'/>"
    }
}

function toggleGiven(){
    given = !given;
}

function clearSelected() {
    selected.innerHTML =
        "<p></p>" +
        "<input type='hidden' name='number[]'/>" +
        "<input type='hidden' name='given[]'/>"
}

function chooseNewSelected(cell){
    //clear previous selected
    if (selected != null) {
        selected.classList.toggle(selectedClassName);
    }
    //make new selected
    selected = cell;
    selected.classList.toggle(selectedClassName);
}

function main(){
    modifiable.innerHTML = "Modified"

    for (let i = 0; i < digitSelectionCells.length; i++) {
        let cell = digitSelectionCells[i];
        cell.addEventListener("click", function () {
            setSelectedValue(this.textContent);
        })
    }

    for (let i = 0; i < sudokuGridCells.length; i++) {
        let cell = sudokuGridCells[i];
        cell.addEventListener("click", function () {
            chooseNewSelected(this);
        })
    }

    removeButton.addEventListener("click", function (){
        clearSelected()
    })
}

main();
