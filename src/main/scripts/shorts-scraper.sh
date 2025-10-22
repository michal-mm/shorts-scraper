#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

if [[ -e app.properties ]]
then
	java -jar "$DIR/ShortsScraper.jar" "$@"
else
	echo "File app.properties not found"
	echo "Consider running:"
	echo "cp example.app.properties app.properties"
	echo "and edit it before running Shorts Scraper again"
fi
