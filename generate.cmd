cd src/main/java/
call java -jar ../../../jars/javacpp.jar org/helixd2s/yavulkanmod/wrapper/*.java -nocompile -d ../../../src/main/cpp -o ../../../src/main/cpp/jniAlter -classpath ../../../build/classes -clear
call robocopy "./" "../../../build/classes/java/main/" "*.class" /mov /e /xd 
call cmake ../../../ -B../../../build
pause
