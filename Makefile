.SILENT:

MVN := mvn

.DEFAULT_GOAL := help

PROJECT_DIR := $(PWD)/src/main/java/com/exercise/project
RESOURCES_DIR := $(PWD)/src/main/resources

##-----------------------------------
## Application
##-----------------------------------
.PHONY: install
install: ## Install all dependencies of the project
	$(MVN) clean install

.PHONY: init
init: # Init the project
	$(MAKE) properties
	$(MAKE) install
	$(MAKE) keypair

.PHONY: dev
dev: ## Run the project
	$(MVN) clean spring-boot:run

.PHONY: run
run: dev ## Run the project (alias: dev)

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
build: ## Alias of package
	$(MAKE) package

.PHONY: package
package: ## Pack to project from packaging configuration
	$(MVN) clean package

.PHONY: keypair
keypair: ## Generate keypair for JWT Authentication
	mkdir -p "$(RESOURCES_DIR)/jwt/"
	openssl genpkey -algorithm RSA -out "$(RESOURCES_DIR)/jwt/private.pem" -pkeyopt rsa_keygen_bits:2048
	openssl rsa -pubout -in "$(RESOURCES_DIR)/jwt/private.pem" -out "$(RESOURCES_DIR)/jwt/public.pem"

##
##-----------------------------------
## Other
##-----------------------------------
.PHONY: help
help: ## List commands
	grep -E '(^[a-zA-Z0-9_-]+:.*?##.*$$)|(^##)' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}{printf "\033[32m%-30s\033[0m %s\n", $$1, $$2}' | sed -e 's/\[32m##/[33m/'

#-----------------------------------
# Dependencies
#-----------------------------------

#-----------------------------------
