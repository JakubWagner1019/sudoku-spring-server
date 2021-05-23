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
        setCellNumber(selected, value, given)
    }
}

function toggleGiven() {
    given = !given;
}

function clearSelected() {
    if (selected != null) {
        clearCell(selected)
    }
}

function getChildByIdSuffix(cell, idSuffix) {
    for (let i = 0; i < cell.children.length; i++) {
        let current = cell.children[i];
        let id = current.id;
        if (id.includes(idSuffix)) {
            return current;
        }
    }
}

function getCellText(cell) {
    return getChildByIdSuffix(cell, "text");
}

function getCellValue(cell) {
    return getChildByIdSuffix(cell, "value");
}

function getCellGiven(cell) {
    return getChildByIdSuffix(cell, "given")
}


function chooseNewSelected(cell) {
    //clear previous selected
    if (selected != null) {
        selected.classList.toggle(selectedClassName);
    }
    //make new selected
    selected = cell;
    selected.classList.toggle(selectedClassName);
    //console.log(selected.children)
    console.log(getCellState(selected))
}

function getCellState(cell) {
    return {
        text: getCellText(cell).innerHTML,
        value: getCellValue(cell).value,
        given: getCellGiven(cell).value
    }
}

function setCellNumber(cell, number, isGiven) {
    setCellState(cell, {
        text: number,
        value: number,
        given: isGiven
    })
}

function clearCell(cell) {
    setCellState(cell, {
        text: '',
        value: 0,
        given: false
    })
}

function setCellState(cell, state) {
    getCellText(cell).innerHTML = state.text
    getCellValue(cell).value = state.value
    getCellGiven(cell).value = state.given
}

function main() {
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

    removeButton.addEventListener("click", function () {
        clearSelected()
    })
}

main();
