#!/bin/bash
set -e
rm -rf ./file
git submodule update --init --recursive
cd ./file
autoheader
aclocal
case `uname` in Darwin*) glibtoolize --ltdl --copy --force ;;
  *) libtoolize --ltdl --copy --force ;; esac
automake --add-missing --copy
autoconf
./configure
make
mkdir ./magic/bin
cp ./magic/magic.mgc ./magic/bin
cd ..
echo "libmagic was updated!"
