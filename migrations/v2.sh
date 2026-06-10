#!/bin/bash
# TODO snake case?

rootPath="${1:-.}"
basePackage="xyz.lbres.kotlinutils"

replacePaths() {
  oldPath="$basePackage.$1"
  newPath="$basePackage.$n"
  oldPathE="$(sed -e 's/\./\\./g' <<< "$1")"
  newPathE="$(sed -e 's/\./\\./g' <<< "$2")"
  git grep -l $oldPathE $rootPath | xargs sed -i "s/$oldPathE/$newPathE/"
}

# ext

# deprecated

# others

# bool array/collection come at end

oldName="classes.labelled.Labelled"
newName="utils.Labelled"
replacePaths $oldName $newName

# import xyz.lbres.kotlinutils.classes.labelled.Labelled
