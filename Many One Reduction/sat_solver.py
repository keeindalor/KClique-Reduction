from typing import List, Set
import time
from z3 import *
import sys


class Literal:
    _value: str
    is_negate: bool

    def __init__(self, value: str, negate: bool) -> None:
        self._value = value
        self.is_negate = negate

    def __str__(self) -> str:
        if self.is_negate:
            return "~" + self._value
        else:
            return self._value

class Clause:
    _literals: List[Literal]

    def __init__(self, literals: List[Literal]) -> None:
        self._literals = literals

    def add_literal(self, literal: Literal):
        self._literals.append(literal)
        return literal._value

    def __str__(self) -> str:
        result = "("
        for literal in self._literals:
            result += str(literal) + "V"
        
        return result[:-1] + ")"

class Formula:
    _clauses: List[Clause]
    _variables: Set[str]

    def __init__(self, clauses: List[Clause], variables: Set[str]) -> None:
        self._clauses = clauses
        self._variables = variables

    def add_clause(self, clause: Clause):
        for literal in clause._literals:
            self._variables.add(literal._value)
        self._clauses.append(clause)

    def __str__(self) -> str:
        result = ""
        for clause in self._clauses:
            result += str(clause) + "^"
        return result[:-1]

def build_formula(s_formula: str) -> Formula:
    formula = Formula([], set())

    for s_clause in s_formula.split('^'):  # for each string clause
        s_clause = s_clause[1:-1]  # get rid of the parathesis

        clause = Clause([])
        for s_literal in s_clause.split('V'):  # for each literal in a clause
            if s_literal[0] == '~':
                clause.add_literal(Literal(s_literal[1:], True))
            else:
                clause.add_literal(Literal(s_literal, False))
        
        formula.add_clause(clause)

    return formula
            

def check_formula(formula: Formula):
    solver = Solver()

    variables = {}

    for variable in formula._variables:
        variables[variable] = Bool(variable)

    for clause in formula._clauses:
        literal_array = []
        for literal in clause._literals:
            if literal.is_negate:
                literal_array.append(Not(variables[literal._value]))
            else:
                literal_array.append(variables[literal._value])
        
        solver.add(Or(literal_array))

    return solver.check()

if __name__ == '__main__':
    
    with open(str(sys.argv[1]), 'r') as file:
        s_formula = str(file.read())

    s_formula = "".join(s_formula.split())

    formula = build_formula(s_formula)

    print(True if str(check_formula(formula)) == "sat" else False)
