MVN := @mvn
MAKE := @make

.DEFAULT_GOAL := help

##-----------------------------------
## Application
##-----------------------------------
.PHONY: install
install: ## Install all dependencies of the project
	$(MVN) install

.PHONY: dev
dev: ## Run the project
	$(MVN) clean spring-boot:run

.PHONY: test
test: ## Test the project
	$(MVN) clean test

.PHONY: clean
clean: ## Clean the project
	$(MVN) clean

.PHONY: clear
clear: ## Alias of clean command
	$(MAKE) clean

.PHONY: build
build: ## Build the project
	$(MVN) clean build

##
##-----------------------------------
## Others
##-----------------------------------
.PHONY: help
help: ## List commands
	@grep -E '(^[a-zA-Z0-9_-]+:.*?##.*$$)|(^##)' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}{printf "\033[32m%-30s\033[0m %s\n", $$1, $$2}' | sed -e 's/\[32m##/[33m/'

##