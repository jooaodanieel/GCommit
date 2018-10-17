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

* [@mairieli][mairieli]
* [@eamanu][eamanu]
* [@gpalsingh][gpalsingh]
* [@ehx][ehx]





[1]: https://github.com/jooaodanieel/GCommit/blob/master/CONTRIBUTING.md
[mairieli]: https://github.com/mairieli
[eamanu]: https://github.com/eamanu
[gpalsingh]: https://github.com/gpalsingh
[ehx]: https://github.com/ehx


## License

This project is licensed under the [MIT License] (https://opensource.org/licenses/MIT)
