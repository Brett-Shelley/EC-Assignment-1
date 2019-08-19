all: compile run

compile:
	javac -Xlint Main.java

run:
	java Main

clean:
	rm -f *.class
