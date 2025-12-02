#!/bin/bash
set -e
rm -rf ./file
git submodule update --init --recursive
cd ./file
case `uname` in Darwin*) glibtoolize --ltdl --copy --force ;;
  *) libtoolize --ltdl --copy --force ;; esac
aclocal
autoheader
autoconf
automake --add-missing --copy
./configure
make
mkdir ./magic/bin
cp ./magic/magic.mgc ./magic/bin
cd ..
echo "libmagic was updated!"
