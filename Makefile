all: compile run

compile:
	javac Main.java

run:
	java Main

clean:
	rm -f *.class
