Elijah congenial-robot
=======================

The elijah-lang compiler.

[https://github.com/elijah-team/congenial-robot](https://github.com/elijah-team/congenial-robot)

```shell
E=`mktemp -d`
git clone https://github.com/elijah-team/congenial-robot -b 2023-11-rosetta-w $E
mkdir $E/COMP
#cd congenial-robot
#mkdir COMP
(cd $E && nix-shell -p maven jdk17 --pure --command "mvn test")
#mvn test
```

This project is licensed under LGPL.

You will need JVM 17 and Maven.


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
