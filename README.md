Elijah congenial-robot
=======================

The elijah-lang compiler/package manager.

Elijah is:

- ... a high-level language built to explore \[insert goal here\].
- ... a historical curiosity.
- ... meant to be easy to use standalone or with existing projects.
- ... free software (LGPL) intended for use on all systems, aka Linux.
- ... philosophically opposed to semicolons

[`congenial-robot`][1] is:

- ... implemented in Java (17, for now; 8 is great and Graal is your paal)
- ... of the impression that build systems don't matter [2][2] [4][4] [5][5] (tldr maven for now)
- ... mortal enemies of Scala
- ... a friend of Eclipse and therefore not Lombok (but only when it's not trying that hard)
- ... a friend of Xtend
- ... is not really all that concerned by Kotlin (see [Verification][3])


Instructions
-------------

[https://github.com/elijah-team/elevated-potential](https://github.com/elijah-team/elevated-potential)

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
[2]: https://github.com/elijah-team/congenial-robot/tree/2023-99-robotics-sbt
[3]: https://nope.org
[4]: https://www.lihaoyi.com/post/SowhatswrongwithSBT.html
[5]: https://silverhammermba.github.io/blog/2023/01/06/gradle
