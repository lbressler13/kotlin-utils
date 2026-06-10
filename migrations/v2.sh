#!/bin/bash
# TODO snake case?

rootPath="${1:-.}"

replacePaths() {
  oldPath=$1
  newPath=$2
  oldPathE="$(sed -e 's/\./\\./g' <<< "$1")"
  newPathE="$(sed -e 's/\./\\./g' <<< "$2")"
  git grep -l $oldPathE $rootPath | xargs sed -i "s/$oldPathE/$newPathE/"
}

# ext

# deprecated

# others

# bool array/collection come at end

oldName="xyz.lbres.kotlinutils.classes.labelled.Labelled"
newName="xyz.lbres.kotlinutils.utils.Labelled"
replacePaths $oldName $newName
