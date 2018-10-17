from distutils.core import setup

description = '''
GCommit is a git-plugin that eases how to commit when you need to sign for more than one person -- pair and mob programming reality.

Instead of having to set manually the other developers names, you can define once and reference at any commit-time.
'''

setup(
    name="GCommit",
    version="0.1",
    description='git commit plugin',
    url='https://github.com/jooaodanieel/GCommit',
    scripts=["git-gcommit",],
    long_description=description,
    license='MIT'
)
