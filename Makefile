BIN_DIR   = /usr/local/bin
LOADER    = gcommit
COMMANDS  = gcommit


all:
	@echo "usage: make [install|uninstall]"

install:
	@install -d -m 755 $(BIN_DIR)
	@install -m 755 $(LOADER) $(BIN_DIR)
	@mv $(BIN_DIR)/$(LOADER) $(BIN_DIR)/git-$(LOADER)
	@echo "GCommit has been installed successfully"

uninstall:
	@test -d $(BIN_DIR) && \
		cd $(BIN_DIR) && \
		rm -f git-$(LOADER)


































