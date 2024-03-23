Elijah congenial-robot
=======================

The elijah-lang compiler/package manager.

Elijah is:

- ... a high-level language built to explore code generation and other interesting techniques.
- ... a historical curiosity.
- ... meant to be easy to use standalone or with existing projects.
- ... free software (LGPL) intended for use on all systems, aka Linux.
- ... philosophically opposed to semicolons.
- ... obsessed with curly braces and brackets.

[`congenial-robot`][1] is:

- ... implemented in Java (17, for now; 8 is great and Graal is your paal)
- ... of the impression that build systems don't matter [2][2] (tldr maven for now)
- ... mortal enemies of Scala
- ... a friend of Eclipse and therefore not Lombok (but only when it's not trying that hard)
- ... a friend of Xtend
- ... is not really all that concerned by Kotlin (see [Verification][3])


Instructions
-------------

https://github.com/elijah-team/congenial-robot

```shell
git clone https://github.com/elijah-team/congenial-robot -b 2024-congenial
cd congenial-robot
mkdir COMP
gradle test
# or 
nix-shell -p maven gradle jdk17 --pure --command "maven clean test"
```

GOALS
------

- Less noise
- More verification
  - on the road to correctness


LINEAGE
--------

`Septagon` - Starting over, again

`Rosetta` - Encapsulating state/environment. Pull model.

`Congenial` - Testablility/verification


TODO
-----

- Convert to ant
- Finish vision
- Stop fuddling


[1]: https://github.com/elijah-team/congenial-robot
[2]: https://gitlab.com/elijah-team/documentation/petal-to-the-medal/-/blob/main/ginitiatives/G8.md?ref_type=heads
[3]: https://gitlab.com/elijah-team/documentation/petal-to-the-medal/-/blob/main/ginitiatives/G1.md?ref_type=heads 
