Elijah congenial-robot
=======================

The elijah-lang compiler.

https://github.com/elijah-team/congenial-robot

```shell
E=`mktemp -d`
git clone https://github.com/elijah-team/congenial-robot -b 2024-congenial-update $E
(cd $E && nix-shell -p maven jdk17_headless --pure --command "mvn test")
```

This project is licensed under LGPL.

You will need JVM 17 and Maven.

Oh yeah, and patience.


GOALS
------

- Less noise
- More verification
  - on the road to correctness
- Build confidence to reduce procrastination
- 


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
- Stop fiddling (act is broken too, i dont think its just me)
