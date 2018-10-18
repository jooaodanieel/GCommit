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

Name | Photo
---: | ---
[Mairieli Wessel][mairieli] | ![Mairieli Wessel](https://avatars3.githubusercontent.com/u/5549736?s=120&v=4)
[Emmanuel Arias][eamanu] | ![Emmanuel Arias](https://avatars2.githubusercontent.com/u/7605307?s=120&v=4)
[Gurkirpal Singh][gpalsingh] | <img alt="" width="120" height="120" class="avatar width-full rounded-2" src="https://avatars3.githubusercontent.com/u/12171804?s=120&amp;v=4">
[Eloy][ehx] | ![Eloy](https://avatars2.githubusercontent.com/u/3865119?s=120&v=4)
[Thatiane de Oliveira Rosa][thatiane] | ![Thatiane de Oliveira Rosa](https://avatars0.githubusercontent.com/u/3801092?s=120&v=4)
[Vinay Hegde][hegde5] | ![Vinay Hegde](https://avatars3.githubusercontent.com/u/8609211?s=120&v=4)
[Andre Ferrari Moukarzel][Detril] | ![Andre Ferrari Moukarzel](https://avatars3.githubusercontent.com/u/17693231?s=120&v=4)


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
