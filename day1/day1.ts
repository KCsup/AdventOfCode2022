import {readFileSync} from 'fs';

const input = readFileSync("day1input.txt", 'utf-8');
const lines = input.split('\n');

let currentElf = 0;
let totals = [];
for(const line of lines) {
    if(!totals[currentElf]) totals[currentElf] = 0;

    if(!line) currentElf++;

    totals[currentElf] += parseInt(line);
}

totals.sort((a, b) => { return b - a });

let top3 = 0;
for(let i = 0; i < 3; i++) {
    console.log(`${i + 1}. ${totals[i]}`);
    top3 += totals[i];
}

console.log(top3)

