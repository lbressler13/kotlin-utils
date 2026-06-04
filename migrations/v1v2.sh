ROOT_PATH={$1-"."}

# TODO non git option
GIT={$2-true}

base_package="xyz.lbres.kotlinutils"

# update ext first
# then update current subpackages
# then update top level packages
# then update exceptions



oldtext="$base_package.list"
newtext="$base_package.collection.list"
git grep -rl oldtext "$ROOT_PATH" | xargs sed -i 's/oldtext/newtext/g'
