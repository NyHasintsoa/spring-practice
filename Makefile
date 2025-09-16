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

GREEN = @echo -e "\x1b[32m\#\# $1\x1b[0m"
RED = @echo -e "\x1b[31m\#\# $1\x1b[0m"

##-----------------------------------
## Application
##-----------------------------------
.PHONY: install
install: ## Install all dependencies of the project
	$(MVN) clean install

.PHONY: init
init: # Init the project
	@make properties
	@make install
	@make keypair
	@$(call GREEN,"-- Project initiate successfully --")

.PHONY: dev
dev: ## Run the project (Local server)
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
build: ## Alias of package
	@make package

.PHONY: package
package: ## Pack to project from packaging configuration (war, jar)
	$(MVN) clean package

.PHONY: keypair
keypair: ## Generate keypair for JWT Authentication
	@mkdir -p "$(RESOURCES_DIR)/jwt/"
	@openssl genpkey -algorithm RSA -out "$(RESOURCES_DIR)/jwt/private.pem" -pkeyopt rsa_keygen_bits:2048
	@openssl rsa -pubout -in "$(RESOURCES_DIR)/jwt/private.pem" -out "$(RESOURCES_DIR)/jwt/public.pem"

.PHONY: properties
properties: $(RESOURCES_DIR)/application.properties.example ## Create properties file to resources dir
	@if [ -f "$(RESOURCES_DIR)/application.properties" ]; then; else cp "$(RESOURCES_DIR)/application.properties.example" "$(RESOURCES_DIR)/application.properties" ; fi

##
##-----------------------------------
## Other
##-----------------------------------
.PHONY: deploy
deploy: ## Deploy to local apache server
	@if [ -d "$(DEPLOY_DIR)/$(PROJECT_ARTIFACT_ID)" ]; then rm -r "$(DEPLOY_DIR)/$(PROJECT_ARTIFACT_ID)"; fi
	@if [ -f "$(DEPLOY_DIR)/$(PROJECT_ARTIFACT_ID).war" ]; then rm "$(DEPLOY_DIR)/$(PROJECT_ARTIFACT_ID).war"; fi
	@make install
	@cp ./target/$(PROJECT_ARTIFACT_ID).war $(DEPLOY_DIR)/

.PHONY: help
help: ## List commands
	@grep -E '(^[a-zA-Z0-9_-]+:.*?##.*$$)|(^##)' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}{printf "\033[32m%-30s\033[0m %s\n", $$1, $$2}' | sed -e 's/\[32m##/[33m/'

.PHONY: mailpit
mailpit: $(RESOURCES_DIR)/mail/mailpit-auth-file ## Run Mailpit Server
	@mailpit --listen 127.0.0.1:8025 --smtp 127.0.0.1:1143 \
		--ui-tls-cert $(RESOURCES_DIR)/certs/mailpit.pem \
		--ui-tls-key $(RESOURCES_DIR)/certs/mailpit-key.pem \
		--smtp-tls-cert $(RESOURCES_DIR)/certs/mailpit.crt \
		--smtp-tls-key $(RESOURCES_DIR)/certs/mailpit.key \
		--send-api-auth-file $(RESOURCES_DIR)/mail/mailpit-auth-file \
		--ui-auth-file $(RESOURCES_DIR)/mail/mailpit-auth-file \
		--database $(RESOURCES_DIR)/mail/database.db \
		--verbose --hide-delete-all-button \
		--smtp-require-tls
##

#-----------------------------------
# Dependencies
#-----------------------------------
target/$(PROJECT_ARTIFACT_ID).war:
	$(MVN) clean install

"$(RESOURCES_DIR)/application.properties.example":
	@touch "$(RESOURCES_DIR)/application.properties.example"

"$(RESOURCES_DIR)/mail/mailpit-auth-file":
	@touch $(RESOURCES_DIR)/mailpit-auth-file
#-----------------------------------