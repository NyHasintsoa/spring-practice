MVN := @mvn
MAKE := @make

.DEFAULT_GOAL := help

#------------------------------------
# Deployment Configuration
#------------------------------------
DEPLOY_DIR := /opt/apache-tomcat/webapps
PROJECT_ARTIFACT_ID := mcp-spring
#------------------------------------

PROJECT_DIR := $(PWD)/src/main/java/com/exercise/project
RESOURCES_DIR := $(PWD)/src/main/resources

GREEN = /bin/echo -e "\x1b[32m\#\# $1\x1b[0m"
RED = /bin/echo -e "\x1b[31m\#\# $1\x1b[0m"

##-----------------------------------
## Application
##-----------------------------------
.PHONY: install
install: ## Install all dependencies of the project
	$(MVN) clean install

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
	@mkdir -p "$(RESOURCES_DIR)/jwt/"
	@openssl genpkey -algorithm RSA -out "$(RESOURCES_DIR)/jwt/private.pem" -pkeyopt rsa_keygen_bits:2048
	@openssl rsa -pubout -in "$(RESOURCES_DIR)/jwt/private.pem" -out "$(RESOURCES_DIR)/jwt/public.pem"

##
##-----------------------------------
## Others
##-----------------------------------
.PHONY: deploy
deploy: ## Deploy to local apache server
	@if [ -d "$(DEPLOY_DIR)/$(PROJECT_ARTIFACT_ID)" ]; then rm -r "$(DEPLOY_DIR)/$(PROJECT_ARTIFACT_ID)"; fi
	@if [ -f "$(PROJECT_ARTIFACT_ID).war" ]; then rm "$(PROJECT_ARTIFACT_ID).war"; fi
	@make install
	@cp ./target/$(PROJECT_ARTIFACT_ID).war $(DEPLOY_DIR)/

.PHONY: help
help: ## List commands
	@grep -E '(^[a-zA-Z0-9_-]+:.*?##.*$$)|(^##)' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}{printf "\033[32m%-30s\033[0m %s\n", $$1, $$2}' | sed -e 's/\[32m##/[33m/'

##

#-----------------------------------
# Dependencies
#-----------------------------------
target/$(PROJECT_ARTIFACT_ID).war:
	$(MVN) clean install
#-----------------------------------