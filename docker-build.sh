#!/bin/bash

# Usage: ./docker-build.sh <repo name (local or remote)> <tag> [push]
# Example: ./docker-build.sh inatrace-be 2.4.0
# Needed Java 11+ and docker command.
#
# If "push" is provided, the image will be pushed to the specified repo with the prepended tag.

set -e

repoName=$1
tag=$2
push=$3

if [ "$repoName" == "" ] || [ "$tag" == "" ]; then
  echo "Usage: $0 <repo name (local or remote)> <tag>"
  exit 1
fi

docker build -t $repoName:$tag .

if [ "$push" == "push" ]; then
  docker push $repoName:$tag
fi
