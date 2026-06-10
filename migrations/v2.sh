#!/bin/bash
# TODO snake case?

rootPath="${1:-.}"
basePackage="xyz.lbres.kotlinutils"

replacePaths() {
  oldPath="$basePackage.$1"
  newPath="$basePackage.$2"
  oldPathE="$(sed -e 's/\./\\./g' <<< "$1")"
  newPathE="$(sed -e 's/\./\\./g' <<< "$2")"
  output=$(git grep -rl $oldPathE $rootPath)
  if [[ -z $output ]]; then
    echo "No occurrences of '$oldPath' found, skipping"
  else
    echo "Replacing '$oldPath' with '$newPath'"
    echo $output | xargs sed -i "s/$oldPathE/$newPathE/"
  fi
}

# ext

# deprecated
declare -A deprecations
deprecations["classes.labelled.Labelled"]="utils.Labelled"
deprecations["classes.multiset"]="collections.multiset"
deprecations["set.mutableset.popRandom"]="collections.popRandom"
deprecations["list.mutablelist.popRandom"]="collections.popRandom"
deprecations["general.ternaryIf"]="utils.simpleIf"

for key in "${!deprecations[@]}"; do
  value=${deprecations[$key]}
  replacePaths $key $value
done


# others

# bool array/collection come at end


# import xyz.lbres.kotlinutils.classes.labelled.Labelled
