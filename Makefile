MVN := @mvn
MAKE := @make

.DEFAULT_GOAL := help

PROJECT_DIR := $(PWD)/src/main/java/com/exercise/project

GREEN = /bin/echo -e "\x1b[32m\#\# $1\x1b[0m"
RED = /bin/echo -e "\x1b[31m\#\# $1\x1b[0m"

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

.PHONY: echoPath
echoPath: ## Echo
	@echo $(PROJECT_DIR)

.PHONY: keypair
keypair: ## Generate keypair for JWT Authentication
	@mkdir -p "$(PROJECT_DIR)/config/jwt/"
	@openssl genpkey -algorithm RSA -out "$(PROJECT_DIR)/config/jwt/private.pem" -pkeyopt rsa_keygen_bits:2048
	@openssl rsa -pubout -in "$(PROJECT_DIR)/config/jwt/private.pem" -out "$(PROJECT_DIR)/config/jwt/public.pem"

##
##-----------------------------------
## Others
##-----------------------------------
.PHONY: help
help: ## List commands
	@grep -E '(^[a-zA-Z0-9_-]+:.*?##.*$$)|(^##)' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}{printf "\033[32m%-30s\033[0m %s\n", $$1, $$2}' | sed -e 's/\[32m##/[33m/'

##