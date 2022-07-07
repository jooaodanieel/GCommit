![GCommit](GCommitLogo.png)


# GCommit - Group Commit

GCommit is a git-plugin that eases how to commit when you need to sign for
more than one person -- pair and mob programming reality.

Instead of having to set manually the other developers names, you can define
once in a configuration file, and simply reference it at any commit-time.

## Installation

> This is a temporary approach, the intention is to bind installation to GitHub releases

First, ensure you have `libcrypt.so.1` available in your system.

```bash
# build the GCommit executable
./gradlew build

# copy to a directory within your PATH and name it "git-commit"
sudo cp build/bin/native/releaseExecutable/g-commit.kexe /usr/local/bin/git-commit
```

## How to use

GCommit relies on a configuration file `gcommit.conf.json`. In it, there are two settings you define:

1. the team

Each member of the team is defined by:

- the **name** to be displayed
- the **email** to be displayed
- a **tag** to act as a reference

So, for instance, a team member can be defined in `gcommit.conf.json` as follows:

```json
{
  "team": [
    {
      "name": "John Doe",
      "email": "john.doe@example.com",
      "tag": "JOD"
    },
    {
      "name": "Jane Doe",
      "email": "jane.doe@example.com",
      "tag": "JAD"
    }
  ],
  
  // ... other aspects ...
  
}
```

GCommit makes it easier to sign commits by providing a quick reference to a stored team member.
The **tag** is the "quick reference".
So make sure to inform unique tags ;)

2. the signature format

GCommit works for multiple Code Repository platforms, such as [GitHub][github] and [GitLab][gitlab] 
-- so far, that's all GCommit supports.

Each platform has its own signature template to account activities for users,
so you can configure which format to adopt.

Out-of-the-box, GCommit supports GitHub's `Co-authored-by: Name <email>` (default)
and GitLab's `Signed-off-by: Name <email>`.

In the config file, you can choose which format to use:

```json
{
  // ... other aspects ...

  "format": "GCommit/GitLab"
}
```

> in case you wish to follow GitHub's format instead, use `"GCommit/GitHub"` or even don't define
> the format since it's the default

Place the `gcommit.conf.json` at the root of the project, and we recommend to add it to Git Ignore file.

Then, after adding changes to git staging area, simply run

```bash
git gcommit JOD JAD
```

> in the case you want to sign with the entire team, you can simply run `git gcommit` with no arguments


## Contributing

Please refer to [CONTRIBUTING.md][1]


## Contributors

Many thanks to all contributors!

| | | | | |
|-|-|-|-|-|
|<p align="center"><img style="border-radius: 100px" width="100px" src="https://avatars3.githubusercontent.com/u/5549736?s=200&v=4"  alt="Mairielli" /><br />[Mairieli][mairieli] Wessel</p>|<p align="center"><img style="border-radius: 100px" width="100px" src="https://avatars2.githubusercontent.com/u/7605307?s=200&v=4"  alt="Emmanuel Arias"/><br />[Emmanuel][eamanu] Arias</p>|<p align="center"><img style="border-radius: 100px" width="100px" src="https://avatars2.githubusercontent.com/u/12171804?s=100&v=4" alt="Gurkirpal Singh" width="100px"/><br />[Gurkirpal][gpalsingh] Singh </p>|<p align="center"><img style="border-radius: 100px" width="100px" src="https://avatars2.githubusercontent.com/u/3865119?s=200&v=4"  alt="Eloy"/><br />[Eloy][ehx]</p>|<p align="center"><img style="border-radius: 100px" width="100px" src="https://avatars0.githubusercontent.com/u/3801092?s=200&v=4"  alt="Thathiane"/><br />[Thathiane][thatiane] Rosa</p>|
|<p align="center"><img style="border-radius: 100px" width="100px" src="https://avatars3.githubusercontent.com/u/8609211?s=200&v=4"  alt="Vinay Hedge"/><br />[Vinay][hegde5] Hegde</p>|<p align="center"><img style="border-radius: 100px" width="100px" src="https://avatars3.githubusercontent.com/u/17693231?s=200&v=4" alt="Andre Moukarzel"/><br />[Andre][Detril] Moukarzel</p>|<p align="center"><img style="border-radius: 100px" width="100px" src="https://avatars3.githubusercontent.com/u/27254325?s=200&v=4" alt="Caio Andrade"/><br />[Caio][CaioA] Andrade</p>|<p align="center"><img style="border-radius: 100px" width="100px" src="https://avatars2.githubusercontent.com/u/7110169?s=200&v=4"  alt="Pedro Pereira"/><br />[Pedro][pedro823] Pereira </p>|<p align="center"><img style="border-radius: 100px" width="100px" src="https://avatars1.githubusercontent.com/u/20888363?s=200&v=4" alt="Jay Welborn"/><br />[Jay][JayWelborn] Welborn </p>|
|<p align="center"><img style="border-radius: 100px" width="100px" src="https://avatars1.githubusercontent.com/u/39068024?s=460&v=4" alt="Leandro Rodrigues" width="100px"/><br />[Leandro][Leandrigues] Rodrigues</p>|<p align="center"><img style="border-radius: 100px" width="100px" src="https://avatars1.githubusercontent.com/u/30770796?s=460&v=4"  alt="ParthPratim" width="100px"/><br />[Parth][ParthPratim] Pratim </p>|<p align="center"><img style="border-radius: 100px" width="100px" src="https://avatars1.githubusercontent.com/u/5351077?s=460&v=4"  alt="JorossBarredo" width="100px"/><br />[Joross][iamjoross] Joross </p>| |



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
[pedro823]: https://github.com/pedro823
[JayWelborn]:https://github.com/JayWelborn
[Leandrigues]:https://github.com/Leandrigues
[ParthPratim]:https://github.com/ParthPratim
[iamjoross]:https://github.com/iamjoross

[github]: https://github.com
[gitlab]: https://gitlab.com

