# Symbol-Balancer

The SymbolBalance class reads a Java file to check for syntactical errors, e.g check to make sure that all { }’s, ( )'s, [ ]'s, " "’s, single-quotes (' '), and /* */’s are properly balanced. Make sure to ignore characters within literal strings (" ") and comment blocks (/* */), as well as ignore single line comments (//). The symbol balancing is completed with the MyStack class, which implements a Java array that stores data and resizes as fit.

There are three types of errors that can be encountered: symbol mismatch after popping stack, empty stack popped, or non-empty stack after parsing the file entirely.
