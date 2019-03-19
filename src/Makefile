.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

JFLAGS = -g -d $(BUILD_DIR)
JC = javac
JVM =java

BUILD_DIR = dist
CLASSES = \
        Ticket.java \
        TicketHandler.java \
        User.java \
        UserHandler.java \
        Processor.java

default: classes

classes: $(CLASSES:.java=.class)

# Main class. Can be anything, but must contain public static void main(String[] args)
MAIN = Processor

run: classes
	$(JVM) -cp $(BUILD_DIR) $(MAIN)

# TODO: Add test target
# test: 

clean:
	$(RM) -r $(BUILD_DIR)/*.class