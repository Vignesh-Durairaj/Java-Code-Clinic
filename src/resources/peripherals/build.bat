cd ../../../
set "PWD=%cd%"
set JAVA_LIB_PATH=%PWD%/lib
set JAVA_FILE_PATH=%PWD%/src/main/java/com/clinic/challenges/peripherals


echo %JAVA_FILE_PATH%


javac -cp %JAVA_LIB_PATH%/jfugue-4.0.3.jar %JAVA_FILE_PATH%/MouseMusic.java %JAVA_FILE_PATH%/MouseChecker.java %JAVA_FILE_PATH%/Main.java