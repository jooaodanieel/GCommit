import setuptools
import shutil
import subprocess
import platform
import sys

class bcolors:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'

def tool_on_sys(name):
    return shutil.which(name) is not None

def execute_cmd(command):
    process = subprocess.Popen(command,
                     stdout=subprocess.PIPE, 
                     stderr=subprocess.PIPE)
    return process

def install_to_git():
    process = execute_cmd(['make','install'])
    stdout, stderr = process.communicate()
    if stderr == b'':
        print(bcolors.OKGREEN + "Success : {}".format(stdout.decode("utf-8") ) + bcolors.ENDC)
    else:
        print(bcolors.FAIL + "Failure : {}".format(stderr.decode("utf-8") ) + bcolors.ENDC)

with open("README.md", "r") as fd:
    long_description = fd.read()

setuptools.setup(
    name="GCommit", # Replace with your own username
    version="1.0",
    author="jooaodanieel",
    description="GCommit is a git-plugin that eases how to commit when you need to sign \
        for more than one person -- pair and mob programming reality.",
    long_description=long_description,
    long_description_content_type="text/markdown",
    url="https://github.com/jooaodanieel/GCommit",
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: OSI Approved :: MIT License",
    ],
    py_modules=['subprocess'],
    python_requires='>=3.6',
    )

if tool_on_sys("git"):
    make_exe = None
    if (tool_on_sys("make")):
        install_to_git()
    else:
        print(bcolors.FAIL + "Failure : make was not found on this system. Please install Git" + bcolors.ENDC)
else:
    print(bcolors.FAIL + "Failure : Git was not found on this system. Please install Git" + bcolors.ENDC)
    
    


