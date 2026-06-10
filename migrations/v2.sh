#!/bin/bash
# TODO snake case?
# TODO non git version

rootPath="${1:-.}"
basePackage="xyz.lbres.kotlinutils"

replacePaths() {
  oldPath="$basePackage.$1"
  newPath="$basePackage.$2"
  oldPathE="$(sed -e 's/\./\\./g' <<< "$oldPath")"
  newPathE="$(sed -e 's/\./\\./g' <<< "$newPath")"
  output=$(git grep -rl $oldPathE $rootPath)
  if [[ -z $output ]]; then
    echo "No occurrences of '$oldPath' found skipping"
  else
    echo "Replacing '$oldPath' with '$newPath'"
    echo $output | xargs sed -i "s/$oldPathE/$newPathE/"
  fi
}

basePackageE="$(sed -e 's/\./\\./g' <<< "$basePackage")"
git grep -rl "$basePackageE.*\.ext" $rootPath

exit

# deprecated
declare -A deprecations
deprecations["classes.labelled.Labelled"]="utils.Labelled"
deprecations["classes.multiset."]="collections.multiset."
deprecations["set.mutableset.popRandom"]="collections.popRandom"
deprecations["list.mutablelist.popRandom"]="collections.popRandom"
deprecations["general.ternaryIf"]="utils.simpleIf"

for key in "${!deprecations[@]}"; do
  value=${deprecations[$key]}
  replacePaths $key $value
done

# ext


# utils
replacePaths "general." "utils."

# numbers
bigdecimal=("isWholeNumber" "roundToBigInteger")
for fn in "${bigdecimal[@]}"; do
  replacePaths "bigdecimal.$fn" "number.bigdecimal.$fn"
done

numbers=("bigdecimal" "biginteger" "char" "int" "long")
for p in "${numbers[@]}"; do
  replacePaths "$p." "number."
done

# closedranges
closedranges=("charrange" "intrange" "longrange")
for p in "${closedranges[@]}"; do
  replacePaths "closedrange.$p." "closedrange."
done

# arrays
arrays=("booleanarray" "chararray" "bytearray" "doublearray" "floatarray" "intarray" "longarray" "shortarray")
for p in "${arrays[@]}"; do
  replacePaths "$p." "array."
done

booleanarray=("all" "none" "any")
for fn in "${booleanarray[@]}"; do
  replacePaths "array.$fn" "array.booleanarray.$fn"
done

# collections
declare -A collections
collections["collection.boolean"]="collection.bool"
collections["collection.char"]="collection.number"
collections["collection.int"]="collection.number"
collections["collection.long"]="collection.number"
collections["collection.mutable"]="collection"
collections["list"]="collection.list"
collections["map.mutablemap"]="collection.map"
collections["set.multiset.const"]="collection.multiset" # must be before set.multiset
collections["set.multiset"]="collection.multiset"

for key in "${!collections[@]}"; do
  value=${collections[$key]}
  replacePaths "$key." "$value."
done

# import xyz.lbres.kotlinutils.classes.labelled.Labelled
