package kotlinutil

private const val iterations = 20

// perform a random action repeatedly, checking to ensure that the result was randomized at least once
// action should contain any other assertions about result
internal fun <T> runRandomTest(randomAction: () -> T, randomCheck: (T) -> Boolean) {
    var checkPassed = false

    repeat {
        val result = randomAction()
        if (randomCheck(result)) {
            checkPassed = true
        }
    }

    assert(checkPassed)
}

// run a function several times
internal fun repeat(function: () -> Unit) {
    for (i in 0 until iterations) {
        function()
    }
}
