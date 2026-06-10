#!/bin/bash

rootPath="${1:-.}"

previousName="xyz.lbres.kotlinutils.classes.labelled.Labelled"
newName="xyz.lbres.kotlinutils.utils.Labelled"
previousNameE="$(sed -e 's/\./\\./g' <<< "$previousName")"
newNameE="$(sed -e 's/\./\\./g' <<< "$newName")"
git grep -l $previousNameE $rootPath | xargs sed -i "s/$previousNameE/$newNameE/"
