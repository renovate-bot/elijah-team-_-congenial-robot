Elijah almost-septagon
=======================

The elijah-lang compiler.

https://git.sr.ht/~tripleo/el-almost

This project is licensed under LGPL, read [LICENSE.LGPL](LICENSE.LGPL)

For instructions on how to build and run the compiler, read the HACKING
document. Please don't use this project in production, it's still under
development.

ABOUT
------

Elijjah is a high-level language suitable for piecemeal replacement in Java
and C/C++ programs, as well as new projects. It is meant to integrate into
your project without much hassle.

It is free software intended for use on all systems, including GNU/Linux.

`almost-septagon` is a silly name for the current incarnation, implemented in
Java.

You will need JVM 17 and Maven or Gradle.

Much work is needed.

See [Language Overview](docs/language-overview.md) for more details on the language.

All of this is a work in progress and your support would be appreciated.


DOCUMENTATION
--------------

[wiki -- https://man.sr.ht/~tripleo/el-technical/Home.md](https://man.sr.ht/~tripleo/el-technical/Home.md)

INTERACT
---------

### DISCUSSIONS

If you want to contribute to the source code, send plain text patch to:

    ~tripleo/el-fluffy-umbrella-devel@lists.sr.ht

Please use git-sendmail to send the patches. You might want to configure your
git to prevent silly mistakes:

    $ git config sendmail.to "~tripleo/el-fluffy-umbrella-devel@lists.sr.ht"
    $ git config format.subjectPrefix "PATCH el-fluffy-umbrella"

----

## TODO

- (libera) chat channel. (meh)
- nix/guix
- learn sr.ht flow
