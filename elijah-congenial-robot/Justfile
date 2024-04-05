watchdocs:
	#docker run --rm -it -p 8000:8000 -v ${PWD}:/docs squidfunk/mkdocs-material
	nix-shell -p python310Packages.mkdocs-material --command "mkdocs serve"
