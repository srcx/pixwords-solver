# PixWords Solver

Usage: `pattern letters [directory with word lists]`

For example `a?p??????? peetrinc` will generate all possibilities for word which has 10 letters,
first is A, third is P and rest might be composed of letters P, E, E, T, R, I, N, C.

All possibilities are compared to word lists if directory with word lists is specified.
Suggested word lists compilation is [SCOWL (And Friends)](http://wordlist.aspell.net/) - pass directory
`final` as the argument to the solver.