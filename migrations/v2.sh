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
declare -A deprecations
deprecations["classes.labelled.Labelled"]="utils.Labelled"

for key in "${!deprecations[@]}"; do
  value=${deprecations[$key]}
  replacePaths $key $value
done


# others

# bool array/collection come at end


# import xyz.lbres.kotlinutils.classes.labelled.Labelled
