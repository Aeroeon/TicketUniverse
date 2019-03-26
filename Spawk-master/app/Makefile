# Tested with GCC 8.2.1

MKDIR_P = mkdir -p # Required for creating obj dir if it doesn't exist

EXE = Spawk

CC = g++

# Source code directory.
SRC_DIR = src
# Temporary object file directory (unlinked binaries)
OBJ_DIR = obj

OUT_DIR = out

SRC = $(wildcard $(SRC_DIR)/*.cpp)
OBJ = $(SRC:$(SRC_DIR)/%.cpp=$(OBJ_DIR)/%.o)

# Include everything in the include subdirectory.
# Enable C++17 mode. Needs C++17 due to the use of the standard filesystem library.
CPPFLAGS += -Iinclude -std=c++17
# Enable all warnings (-Werror would be good too.)
CFLAGS += -Wall
# Leave this here in case we ever add external libs. (would need to create a lib subdir)
LDFLAGS += -Llib
LDLIBS += -lm -lstdc++fs

.PHONY: all clean

all: $(OBJ_DIR) $(OUT_DIR) $ $(EXE)

${OUT_DIR}:
	${MKDIR_P} ${OUT_DIR}

${OBJ_DIR}:
	${MKDIR_P} ${OBJ_DIR}

$(EXE): $(OBJ)
	$(CC) $(LDFLAGS) $^ $(LDLIBS) -o ${OUT_DIR}/$@

$(OBJ_DIR)/%.o: $(SRC_DIR)/%.cpp
	$(CC) $(CPPFLAGS) $(CFLAGS) -c $< -o $@

clean:
	$(RM) $(OBJ)
	$(RM) -r $(OUT_DIR)