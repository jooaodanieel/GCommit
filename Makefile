BIN_DIR   = /usr/local/bin
LOADER    = git-gcommit
COMMANDS  = git-gcommit


all:
	@echo "usage: make [install|uninstall]"

install:
	@install -d -m 755 $(BIN_DIR)
	@install -m 755 $(LOADER) $(BIN_DIR)
	@echo "GCommit has been installed successfully"

uninstall:
	@test -d $(BIN_DIR) && \
		cd $(BIN_DIR) && \
		rm -f git-$(LOADER)


