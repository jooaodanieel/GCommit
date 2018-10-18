# GCommit - Group Commit

GCommit is a git-plugin that eases how to commit when you need to sign for
more than one person -- pair and mob programming reality.

Instead of having to set manually the other developers names, you can define
once and reference at any commit-time.

## Installation

**Note:** While installing make sure that the directory where the plugin script
is installed is in your `PATH`.

### Unix-like systems

#### Using make

For instalattion you have to run the next command:

```
$ sudo make install

GCommit has been installed successfully
```

#### Using setup.py

First you might need to get distutils:

```
$ sudo apt-get install python3-distutils
```

Then use the following command:

```
$ sudo python setup.py install
```

This will automatically take care of changing permissions on the script.

### Windows

The only method currently available is using `setup.py`:
```
$ python setup.py install
```

## Uninstallation

If you used `make`, use the following command:

```
$ sudo make uninstall
```

If you're on windows or used `setup.py` you'll have to manually remove
the file from the install directory. For example, The default on linux is
`/usr/local/bin` while on windows (using python 3.7) it is
`~\{user}\AppData\Local\Programs\Python37\Scripts`


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
* [@thatiane][thatiane]
* [@hegde5][hegde5]
* [@Detril][Detril]


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
