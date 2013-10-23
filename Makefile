# Makefile
# Compiles the Java project.

all: reco

reco:
	javac *.java

clean:
	rm -f *.class

