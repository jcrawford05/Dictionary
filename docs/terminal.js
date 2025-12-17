const output = document.getElementById("output");
const input = document.getElementById("commandInput");
let dictionary = {};

let state = "MENU";
let temp = {};

function loadDictionary() {
    const raw = document.getElementById("dictionary-data").textContent;

    raw.split(/\r?\n/).forEach(line => {
        if (!line.trim()) return;

        const parts = line.split("::");
        if (parts.length < 3) return;

        const word = parts[0].trim().toLowerCase();
        const pos = parts[1];
        const definition = parts.slice(2).join("::");

        if (!dictionary[word]) {
            dictionary[word] = [];
        }

        dictionary[word].push(`${pos}. ${definition}`);
    });

    print(`Dictionary loaded (${Object.keys(dictionary).length} words)`);
    print("");
    menu();
}


function print(line = "") {
    output.textContent += line + "\n";
    output.scrollTop = output.scrollHeight;
}

function menu() {
    print("Main Menu");
    print("1.  Get metadata");
    print("2.  Get Words In Range");
    print("3.  Get word");
    print("4.  Get first word");
    print("5.  Get last word");
    print("6.  Get parts of speech");
    print("7.  Update definition");
    print("8.  Delete definition");
    print("9.  Add new definition");
    print("10. Save dictionary");
    print("11. Exit");
    print("");
    print("Select an option:");
}

function metadata() {
    const words = Object.keys(dictionary).length;
    const defs = Object.values(dictionary).flat().length;
    const parts = new Set(
        Object.values(dictionary)
            .flat()
            .map(d => d.split(".")[0])
    );
    const sorted = Object.keys(dictionary).sort();

    print(`words: ${words}`);
    print(`definitions: ${defs}`);
    print(`definitions per word: ${(defs / words).toFixed(3)}`);
    print(`parts of speech: ${parts.size}`);
    print(`first word: ${sorted[0]}`);
    print(`last word: ${sorted[sorted.length - 1]}`);
    print("");
    menu();
}

function handleMenu(choice) {
    switch (choice) {
        case "1":
            metadata();
            break;

        case "2":
            print("Enter starting word:");
            state = "RANGE_START";
            break;

        case "3":
            print("Select a word:");
            state = "GET_WORD";
            break;

        case "4":
            print(Object.keys(dictionary).sort()[0]);
            print("");
            menu();
            break;

        case "5":
            const keys = Object.keys(dictionary).sort();
            print(keys[keys.length - 1]);
            print("");
            menu();
            break;

        case "6":
            const parts = new Set(
                Object.values(dictionary)
                    .flat()
                    .map(d => d.split(".")[0])
            );
            parts.forEach(p => print(p));
            print("");
            menu();
            break;

        case "7":
            print("Select word to update:");
            state = "UPDATE_WORD";
            break;

        case "8":
            print("Select word to delete:");
            state = "DELETE_WORD";
            break;

        case "9":
            print("Enter new word:");
            state = "ADD_WORD";
            break;

        case "10":
            print("Dictionary saved successfully.");
            print("");
            menu();
            break;

        case "11":
            print("Process finished with exit code 0");
            input.disabled = true;
            break;

        default:
            print("Invalid Selection");
            print("");
            menu();
    }
}

function handleInput(value) {
    switch (state) {
        case "MENU":
            handleMenu(value);
            break;

        case "GET_WORD":
            if (!dictionary[value]) {
                print("Word not found.");
            } else {
                print(value);
                dictionary[value].forEach(d => print("    " + d));
            }
            print("");
            state = "MENU";
            menu();
            break;

        case "RANGE_START":
            temp.start = value;
            print("Enter ending word:");
            state = "RANGE_END";
            break;

        case "RANGE_END":
            Object.keys(dictionary)
                .sort()
                .filter(w => w >= temp.start && w <= value)
                .forEach(w => print(w));
            print("");
            temp = {};
            state = "MENU";
            menu();
            break;

        case "ADD_WORD":
            temp.word = value;
            print("Enter definition (e.g. noun. definition):");
            state = "ADD_DEF";
            break;

        case "ADD_DEF":
            dictionary[temp.word] = [value];
            print("Definition added.");
            print("");
            temp = {};
            state = "MENU";
            menu();
            break;

        case "UPDATE_WORD":
            if (!dictionary[value]) {
                print("Word not found.");
                state = "MENU";
                menu();
            } else {
                temp.word = value;
                print("Enter new definition:");
                state = "UPDATE_DEF";
            }
            break;

        case "UPDATE_DEF":
            dictionary[temp.word] = [value];
            print("Definition updated.");
            print("");
            temp = {};
            state = "MENU";
            menu();
            break;

        case "DELETE_WORD":
            if (!dictionary[value]) {
                print("Word not found.");
            } else {
                delete dictionary[value];
                print("Definition deleted.");
            }
            print("");
            state = "MENU";
            menu();
            break;
    }
}

input.addEventListener("keydown", e => {
    if (e.key !== "Enter") return;
    const value = input.value.trim().toLowerCase();
    input.value = "";
    print(value);
    handleInput(value);
});

// boot
print("Loading dictionary...");
loadDictionary();

