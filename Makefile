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
	$(TEST_DIR)/ProcessorTests.java \
        $(TEST_DIR)/TicketHandlerTests.java \
        $(TEST_DIR)/TicketTests.java \
	$(TEST_DIR)/UserHandlerTests.java \
        $(TEST_DIR)/UserTests.java

default: classes

classes: $(CLASSES:.java=.class)

build_tests:
	$(JC) -d $(BUILD_DIR) -classpath junit.jar -sourcepath $(SRC_DIR) $(TEST_CLASSES)

# Main class. Can be anything, but must contain public static void main(String[] args)
MAIN = Processor
TESTMAIN = \
	ProcessorTests \
	TicketHandlerTests \
	TicketTests \
	UserHandlerTests \
	UserTests

run: classes
	$(JVM) -cp $(BUILD_DIR) $(MAIN)

test: build_tests
	$(JVM) -cp junit.jar:$(BUILD_DIR) org.junit.runner.JUnitCore $(TESTMAIN)

clean:
	$(RM) -r $(BUILD_DIR)/*.class