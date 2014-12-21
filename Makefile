run:
	java -cp bin hr.fer.oop.lab3.topic1.shell.MyShell

compile:
	javac -d bin \
	src/hr/fer/oop/lab3/topic1/*.java \
	src/hr/fer/oop/lab3/topic1/shell/*.java \
	src/hr/fer/oop/lab3/topic1/shell/commands/*.java \
	src/hr/fer/oop/lab3/topic1/shell/Visitors/*.java \
	src/hr/fer/oop/lab3/topic1/shell/Exceptions/*.java
