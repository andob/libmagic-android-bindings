#!/bin/bash
set -e
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
mkdir ./magic/bin
cp ./magic/magic.mgc ./magic/bin
cd ..
