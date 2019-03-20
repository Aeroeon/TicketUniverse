.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

JFLAGS = -g -classpath $(SRC_DIR) -d $(BUILD_DIR)
JC = javac
JVM = java

SRC_DIR = src
BUILD_DIR = dist
TEST_DIR = tests
CLASSES = \
        $(SRC_DIR)/Ticket.java \
        $(SRC_DIR)/TicketHandler.java \
        $(SRC_DIR)/User.java \
        $(SRC_DIR)/UserHandler.java \
        $(SRC_DIR)/Processor.java

TEST_CLASSES = \
        $(TEST_DIR)/TicketHandlerTests.java \
        $(TEST_DIR)/TicketTests.java

default: classes

classes: $(CLASSES:.java=.class)

build_tests: $(TEST_CLASSES:.java=.class)

# Main class. Can be anything, but must contain public static void main(String[] args)
MAIN = Processor

run: classes
	$(JVM) -cp $(BUILD_DIR) $(MAIN)

# TODO: Add test target
test: build_tests
    $(JVM) -cp .:/junit-4.10.jar org.junit.runner.JUnitCore $(TEST_CLASSES)

clean:
	$(RM) -r $(BUILD_DIR)/*.class