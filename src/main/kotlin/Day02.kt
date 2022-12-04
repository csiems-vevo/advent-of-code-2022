fun playRockPaperScissors(input: List<Pair<Char,Char>>, calculator: (Pair<Char,Char>) -> Int) = input.sumOf { calculator(it) }

fun round1Calculator(roundResult: Pair<Char,Char>): Int {
    return when(roundResult) {
        Pair('A','X') -> 1 + 3
        Pair('A','Y') -> 2 + 6
        Pair('A','Z') -> 3 + 0
        Pair('B','X') -> 1 + 0
        Pair('B','Y') -> 2 + 3
        Pair('B','Z') -> 3 + 6
        Pair('C','X') -> 1 + 6
        Pair('C','Y') -> 2 + 0
        Pair('C','Z') -> 3 + 3
        else -> throw Exception("malformed input")
    }
}

fun round2Calculator(roundResult: Pair<Char,Char>): Int {
    return when(roundResult) {
        Pair('A','X') -> 3 + 0
        Pair('A','Y') -> 1 + 3
        Pair('A','Z') -> 2 + 6
        Pair('B','X') -> 1 + 0
        Pair('B','Y') -> 2 + 3
        Pair('B','Z') -> 3 + 6
        Pair('C','X') -> 2 + 0
        Pair('C','Y') -> 3 + 3
        Pair('C','Z') -> 1 + 6
        else -> throw Exception("malformed input")
    }
}
