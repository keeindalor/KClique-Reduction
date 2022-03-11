IN=tests/in/$(CTG)/test$(TEST).in
OUT=tests/out/$(CTG)/test$(TEST).out
AUX=tests/aux/$(CTG)/test$(TEST).aux

build:
	javac *.java

run_backtracking:
	java Bkt $(IN) > $(OUT)

run_reduction:
	java Reduction $(IN) > $(AUX)

clean:
	rm -rf *.class
