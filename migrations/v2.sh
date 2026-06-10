#!/bin/bash
# TODO snake case?

rootPath="."
basePackage="xyz.lbres.kotlinutils"
grepCmd="git grep"

while test $# -gt 0; do
  case "$1" in
    --no-git)
      grepCmd="grep"
      shift
      ;;
    --src-path*)
      rootPath=`echo $1 | sed -e 's/^[^=]*=//g'`
      shift
      ;;
    --log-skipped*)
      logSkipped=true
      shift
      ;;
    *)
      break
      ;;
  esac
done

escape() {
  echo "$(sed -e 's/\./\\./g' <<< "$1")"
}

get_suffix() {
  paths=$1
  if [[ $paths == "true" ]]; then
    echo "."
  else
    echo ""
  fi
}

replacePaths() {
  oldPath="$basePackage.$1"
  newPath="$basePackage.$2"
  oldPathE=$(escape $oldPath)
  newPathE=$(escape $newPath)
  files=$($grepCmd -rl $oldPathE $rootPath)
  if [[ -z $files ]]; then
    if [[ $logSkipped == "true" ]]; then
      echo "No occurrences of '$oldPath' found, skipping"
    fi
  else
    echo "Replacing '$oldPath' with '$newPath'"
    echo $files | xargs sed -i "s/$oldPathE/$newPathE/"
  fi
}

replaceArray() {
  name=$1[@]
  arr=("${!name}")
  arrayPrefix=$2
  replacement=$3
  paths="${4:-true}"
  suffix=$(get_suffix $paths)
  for p in "${arr[@]}"; do
    replacePaths "$arrayPrefix$p$suffix" "$replacement$suffix"
  done
}

replaceMap() {
  name=$(declare -p "$1")
  declare -A map=${name#*=}
  paths="${2:-true}"
  suffix=$(get_suffix $paths)
  for key in "${!map[@]}"; do
    replacePaths "$key$suffix" "${map[$key]}$suffix"
  done
}

# deprecated
declare -A deprecations
deprecations["classes.labelled.Labelled"]="utils.Labelled"
deprecations["classes.multiset."]="collections.multiset."
deprecations["set.mutableset.popRandom"]="collections.popRandom"
deprecations["list.mutablelist.popRandom"]="collections.popRandom"
deprecations["general.ternaryIf"]="utils.simpleIf"

replaceMap "deprecations" false

# ext
extPattern="$(escape $basePackage).*\.ext"
extFiles=$($grepCmd -rl $extPattern $rootPath)

if [[ -z $extFiles ]]; then
  if [[ $logSkipped == "true" ]]; then
    echo "No occurrences of '.ext' paths, skipping"
  fi
else
  echo "Replacing '.ext' paths"
  for f in "${extFiles[@]}"; do
    sed -i "/$extPattern/s/\.ext//g" $f
  done
fi

# utils
replacePaths "general." "utils."

# numbers
bigdecimal=("isWholeNumber" "roundToBigInteger")
for fn in "${bigdecimal[@]}"; do
  replacePaths "bigdecimal.$fn" "number.bigdecimal.$fn"
done

numbers=("bigdecimal" "biginteger" "char" "int" "long")
replaceArray "numbers" "" "number"

# closedranges
closedranges=("charrange" "intrange" "longrange")
replaceArray "closedranges" "closedrange." "closedrange"

# arrays
arrays=("booleanarray" "chararray" "bytearray" "doublearray" "floatarray" "intarray" "longarray" "shortarray")
replaceArray "arrays" "" "array"

booleanarray=("all" "none" "any")
replaceArray "booleanarray" "array." "array.booleanarray"

# collections
replacePaths "set.multiset.const." "set.multiset."

declare -A collections
collections["collection.boolean"]="collection.bool"
collections["collection.char"]="collection.number"
collections["collection.int"]="collection.number"
collections["collection.long"]="collection.number"
collections["collection.mutable"]="collection"
collections["list"]="collection.list"
collections["map.mutablemap"]="collection.map"
collections["set.multiset"]="collection.multiset"

replaceMap "collections"

declare -A lists
lists["copyWithReplacement"]="withReplacementAt"
lists["copyWithLastReplaced"]="withLastReplaced"
lists["copyWithFirstReplaced"]="withFirstReplaced"
lists["copyWithoutLast"]="withoutLast"

for key in "${!lists[@]}"; do
  replacePaths "collection.list.$key" "collection.list.${lists[$key]}"
done

# invocations
declare -A invocations
invocations["ternaryIf"]="simpleIf"
invocations["copyWithReplacement"]="withReplacementAt"
invocations["copyWithLastReplaced"]="withLastReplaced"
invocations["copyWithFirstReplaced"]="withFirstReplaced"
invocations["copyWithoutLast"]="withoutLast"

for key in "${!invocations[@]}"; do
  value="${invocations[$key]}"
  files=$($grepCmd -rl $key $rootPath)

  if [[ -z $files ]]; then
    if [[ $logSkipped == "true" ]]; then
      echo "No occurrences of '$key' found, skipping"
    fi
  else
    echo "Replacing '$key' with '$value'"
    echo $files | xargs sed -i "s/$key/$value/"
  fi
done
