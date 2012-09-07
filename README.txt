/* TADB-R README */

TABLE OF CONTENTS

  1.0 PREREQUISITES
  2.0 COMPILING
  3.0 USAGE LINUX
  4.0 USAGE WINDOWS
  5.0 CONTACTS


1.0 PREREQUISITES:

  You have to install "xboard" to use TADB-R chess IA.
  You must have at least:
	- xboard
	- javac or ant

2.0 COMPILING (LINUX):

  To compile the source just use the "makeJar.sh" file. It will
  automatically use ant if installed.

2.1 UNISTALL:

  There is no need to unistall the IA. Just remove the class files 
  in the bin/ directiory and/or the jar file.

3.0 USAGE LINUX:

  LINUX:
  Just use the play.sh script to play against TADB. To see
  him play (and lose) against the pc, use playTwoMachines.sh.

  -- Or --

  To play against TADB-R, just go in ./bin and type:

	$ xboard -fcp 'java TADB'

  Or if you have download the "jar" file:

	$ xboard -fcp 'java -jar TADB.jar'

4.0 USAGE WINDOWS:

  It's not been tested for windows (yet).

5.0 CONTACT:

  To contact me, send an e-mail to jecnua@gmail.com.

END

...
