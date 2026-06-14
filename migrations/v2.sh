#!/bin/bash

rootPath="."
basePackage="xyz.lbres.kotlinutils"
grepCmd="git grep"

# track modified files
modified=()

# colors for logging
red="\u001b[31m"
nc="\u001b[0m" # no color

# print help text
printHelpOptions() {
  prefixText=$1
  if [[ "$prefixText" != "" ]]; then
    echo -e $prefixText
    echo
  fi
  tab="  "
  echo "Valid options:"
  echo "$tab--help|-h: print help text"
  echo "$tab--src-path: root path for update, defaults to current directory"
  echo "$tab--no-git: run script in a non-git repo, using the grep command instead of git grep. This can cause damage in git repositories."
  echo "$tab--log-skipped: print the names of checks which are being skipped due to no matching files"
  echo "$tab--log-modified: print the names of files changed after script runs"

  echo
  echo "Sample usage: ./v2.sh --src-path=\"src\" --log-skipped --log-modified"
}

# read input flags
while test $# -gt 0; do
  case "$1" in
    --help|-h)
      printHelpOptions "This script modifies the code in a repository to replace deprecated, moved, or renamed functions and classes that were changed between major versions 1 and 2."
      exit 0
      ;;
    --no-git)
      grepCmd="grep"
      shift
      ;;
    --src-path*)
      rootPath=`echo $1 | sed -e 's/^[^=]*=//g'`
      shift
      ;;
    --log-skipped)
      logSkipped=true
      shift
      ;;
    --log-modified)
      logModified=true
      shift
      ;;
    *)
      printHelpOptions "${red}Invalid option: $1${nc}"
      exit 1
      ;;
  esac
done

# escape a string to use in grep/sed
escape() {
  echo "$(sed -e 's/\./\\./g' <<< "$1")"
}

# get suffix for path
getSuffix() {
  local dirs=$1
  if [[ $dirs == "true" ]]; then
    echo "."
  else
    echo ""
  fi
}

######################################################################
# Replace old package path with new one in all files
# Globals:
#   basePackage
#   rootPath
# Arguments:
#   1 - old package path, starting after base package
#   2 - new package path, starting after base package
#   3 - if base package should be included in path, defaults to true
# Outputs:
#   Updates files
######################################################################
replacePaths() {
  local useBasePackage="${3:-true}"
  if [[ $useBasePackage == "true" ]]; then
    local oldPath="$basePackage.$1"
    local newPath="$basePackage.$2"
  else
    local oldPath=$1
    local newPath=$2
  fi
  local oldPathE=$(escape $oldPath)
  local newPathE=$(escape $newPath)
  local files=$($grepCmd -rl $oldPathE $rootPath)
  if [[ -z $files ]]; then
    if [[ $logSkipped == "true" ]]; then
      echo "No occurrences of '$oldPath' found, skipping"
    fi
  else
    echo "Replacing '$oldPath' with '$newPath'"
    echo $files | xargs sed -i "s/$oldPathE/$newPathE/"
    modified+=" $files"
  fi
}

######################################################################
# Replace all paths from an array with the same new path
# Arguments:
#   1 - name of array of old paths
#   2 - common prefix to add to paths in the array
#   3 - new path
#   4 - if the values in the array are dir paths, defaults to true
# Outputs:
#   Updates files
######################################################################
replaceArray() {
  local name=$1[@]
  local arr=("${!name}")
  local arrayPrefix=$2
  local replacement=$3
  local dirs="${4:-true}"
  local suffix=$(getSuffix $dirs)
  for p in "${arr[@]}"; do
    replacePaths "$arrayPrefix$p$suffix" "$replacement$suffix"
  done
}

######################################################################
# Replace all old paths from a map with the corresponding new values
# Arguments:
#   1 - name of map mapping old paths to new paths
#   2 - if the values in the array are dir paths, defaults to true
# Outputs:
#   Updates files
######################################################################
replaceMap() {
  local name=$(declare -p "$1")
  declare -A map=${name#*=}
  local dirs="${2:-true}"
  local suffix=$(getSuffix $dirs)
  for key in "${!map[@]}"; do
    replacePaths "$key$suffix" "${map[$key]}$suffix"
  done
}

echo "Starting v1 to v2 migration..."

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
modified+="$extFiles"

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
  replacePaths $key "${invocations[$key]}" false
done

# get unique files changed
IFS=" " read -r -a modified <<< "$(tr ' ' '\n' <<< "${modified[@]}" | sort -u | tr '\n' ' ')"

echo "v1 to v2 migration complete! ${#modified[@]} files modified"

if [[ $logModified ]]; then
  echo
  echo "Modified files:"
  for f in "${modified[@]}"; do
    echo "  $f"
  done
fi
