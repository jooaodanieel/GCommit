# GCommit - Group Commit

GCommit is a git-plugin that eases how to commit when you need to sign for
more than one person -- pair and mob programming reality.

Instead of having to set manually the other developers names, you can define
once and reference at any commit-time.

## Installation
For instalattion you have to run the next command:

```
$ sudo make install

GCommit has been installed successfully
```

## Uninstall
For Uninstallation you have to run the next command:

```
$ sudo make uninstall
```


## How to use

GCommit reads a file that defines your teammates signatures, so first create
a `.gitteam` file in your project's root directory, that follows the following
structure:

```plain
JD="João Daniel <jotaf.daniel@gmail.com>"
JOD="John Doe <jon.doe@example.com>"
JAD="Jane Doe <jane.doe@example.com>"
```

> note: there's no empty line at the end

Once you have `.gitteam` in your repository, you can commit something using:

```bash
git gcommit JAD JD
```

This will generate a initial commit message like this:

```plain


Signed-off-by: Jane Doe <jane.doe@example.com>
Signed-off-by: João Daniel <jotaf.daniel@gmail.com>
```


## Contributing

Please refer to [CONTRIBUTING.md][1]


## Contributors

Many thanks to all contributors!



|          Name  \| Photo           |                                                              |
| :-------------------------------: | ------------------------------------------------------------ |
|  [ Mairieli ][ mairieli ] Wessel  | <img src="https://avatars3.githubusercontent.com/u/5549736?s=400&v=4" alt = "Mairielli" width = "200"/> |
|    [ Emmanuel ][ eamanu ]Arias    | <img src = "https://avatars2.githubusercontent.com/u/7605307?s=400&v=4" alt = "Emmanuel Arias"/> |
|  [ Gurkirpal ][gpalsingh] Singh   | <img src ="https://avatars2.githubusercontent.com/u/12171804?s=400&v=4" alt = "Gurkirpal Singh" width = "200"/> |
|           [ Eloy ][ehx]           | <img src ="https://avatars2.githubusercontent.com/u/3865119?s=400&v=4" alt = "Eloy" width = "200"/> |
|    [ Thathiane ][thatiane]Rosa    | <img src = "https://avatars0.githubusercontent.com/u/3801092?s=400&v=4" alt = "Thathiane" width = "200"/> |
|       [Vinay][hegde5] Hegde       | <img src = "https://avatars3.githubusercontent.com/u/8609211?s=400&v=4" alt = "Vinay Hedge" width = "200"/> |
| [Andre][Detril] Ferrari Moukarzel | <img src = "https://avatars3.githubusercontent.com/u/17693231?s=400&v=4" alt ="Andre Ferrari Moukarzel" witdh = "200"/> |
|       [Caio][CaioA] Andrade       | <img src = "https://avatars3.githubusercontent.com/u/27254325?s=460&v=4" alt = "Caio Andrade" width = "200"/> |

## License



This project is licensed under the [MIT License][2]



[1]: https://github.com/jooaodanieel/GCommit/blob/master/CONTRIBUTING.md
[2]: https://opensource.org/licenses/MIT
[mairieli]: https://github.com/mairieli
[eamanu]: https://github.com/eamanu
[gpalsingh]: https://github.com/gpalsingh
[ehx]: https://github.com/ehx
[thatiane]: https://github.com/thatiane
[hegde5]: https://github.com/hegde5
[Detril]: https://github.com/Detril
[CaioA]: https://github.com/CanTulio

