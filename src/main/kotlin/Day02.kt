fun playRockPaperScissors(input: List<Pair<Char,Char>>, calculator: (Pair<Char,Char>) -> Int) = input.sumOf { calculator(it) }

fun round1Calculator(roundResult: Pair<Char,Char>): Int {
    return when(roundResult) {
        Pair('A','X') -> 4
        Pair('A','Y') -> 8
        Pair('A','Z') -> 3
        Pair('B','X') -> 1
        Pair('B','Y') -> 5
        Pair('B','Z') -> 9
        Pair('C','X') -> 7
        Pair('C','Y') -> 2
        Pair('C','Z') -> 6
        else -> throw Exception("malformed input")
    }
}

fun round2Calculator(roundResult: Pair<Char,Char>): Int {
    return when(roundResult) {
        Pair('A','X') -> 3
        Pair('A','Y') -> 4
        Pair('A','Z') -> 8
        Pair('B','X') -> 1
        Pair('B','Y') -> 5
        Pair('B','Z') -> 9
        Pair('C','X') -> 2
        Pair('C','Y') -> 6
        Pair('C','Z') -> 7
        else -> throw Exception("malformed input")
    }
}
