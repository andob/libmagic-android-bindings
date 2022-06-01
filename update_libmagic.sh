#!/bin/bash
set -e
git reset --hard
rm -rf ./file
git submodule update --init --recursive
cd ./file
autoheader
aclocal
libtoolize --ltdl --copy --force
automake --add-missing --copy
autoconf
./configure
make
cd ..

