.SUFFIXES: .java .class
.java.class:
	javac $*.java

CLASSES = \
        Ticket.java \
        TicketHandler.java \
        Processor.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class