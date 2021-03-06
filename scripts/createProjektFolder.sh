#!/bin/bash

# creates the needed directories to setup a new project

scriptPos=`dirname $0`
scriptName=${0##*/}


if [ "$#" -lt 1 ]; then
    	echo "illegal number of parameters, usage: $scriptName PROJECT_DIR"
	exit 1
fi

PROJECT_DIR=$1

function createDirOrExit {
	if ! [ -d "$1" ]; then
		echo "create dir '$1'"
		if ! mkdir -p "$1"; then
			echo "error while create dir '$1'"
			exit 1
		fi
	fi
}

function copyFileOrExit {
	fileName=${1##*/}
        if ! [ -f "$2/$fileName" ]; then
		echo "copy '$1' to '$2'"
                if ! cp "$1" "$2"; then
                        echo "error while copy file '$1' to '$2'"
                        exit 1
                fi
        fi
}


createDirOrExit "$PROJECT_DIR"

createDirOrExit "$PROJECT_DIR/src/main/java"
createDirOrExit "$PROJECT_DIR/src/main/resources"

createDirOrExit "$PROJECT_DIR/src/test/java"
createDirOrExit "$PROJECT_DIR/src/test/resources"


# copy of extra files ... license, readme, gitignore and stuff
copyFileOrExit "$scriptPos/res/gitignore" "$PROJECT_DIR/.gitignore"
copyFileOrExit "$scriptPos/res/LICENSE" "$PROJECT_DIR"
copyFileOrExit "$scriptPos/res/logback.xml" "$PROJECT_DIR/src/main/resources"

if ! [ -f "$PROJECT_DIR/README.md" ]; then
	touch "$PROJECT_DIR/README.md"
fi

if ! [ -f "$PROJECT_DIR/build.gradle" ]; then
	baseFile="$scriptPos/res/build_gradle_base.groovy"
	echo "copy '$baseFile' to '$PROJECT_DIR/build.gradle'"
	if ! cp "$baseFile" "$PROJECT_DIR/build.gradle"; then
		echo "error while copy '$baseFile' to '$PROJECT_DIR/build.gradle'"
		exit 1
	fi
fi
