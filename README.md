# IS24-AM52
Prova Finale Ingegneria del Sofware 2024 - Codex - Gruppo AM52

## Gruppo AM52
The development team is composed by:
- Andrea Aliberti (<andrea.aliberti@mail.polimi.it>)
- Livio Bordignon (<livio.bordgnon@mail.polimi.it>)
- Lorenzo Barcellini (<lorenzo.barcellini@mail.polimi.it>)
- William Arthur Cho Beni (<williamarthur.cho@mail.polimi.it>)

## Project Overview
The target set at the beginning of the project development is to reach the final exam score equal to 30, implementing the following requirements:
- complete game rules
- Textual User Interface (TUI)
- Graphical User Interface (GUI)
- RMI
- Socket
- Additional Feature: Multiple Matches, CHAT

Based on the progress of the project, the implementation of a further additional functionality will be evaluated.

## Current Progress of Works (Updated 27/05/24)
The following activities have been completed:
- UML of the game model
- First Peer review (group AM09)
- UML of the Network
- Complete testing on Model and Controller/Network
- Sequence diagram of sample calls
- Implementation of the Network for both RMI and TCP
- Second Peer review 
- UML of the client Architecture
- Implementation of Client
- Implementation of complete workflow for TUI
- Beautify TUI (99%)
- Artifact
- GUI
- Complete Testing with artifact on multiple devices
- CHAT

## Building and Assemblying the Artifact (from intellij ide)
To build the project and create the final artifact, run the follwing Maven commands
- mvn clean
- mvn package assembly:single

## Arfifact Usage
In this paragrapth, the arfifact is assumed to be name Codex.jar
A single artifact can be used both for Server application and Client application. The startup mode
is defined depending on the command line arguments when the jar file is run from the console.
The default modes are:
- Server: run without any command line argument. By default, the server open TCP connection on port 1024 and RMI connection on port 1025. The maximum concurrent clients supported is 10000. For TCP connection, if the specified port is not available, the application automatically try to open the connection on an available port number (the -f/--fixed option disable this behaviour). The verbosity livel of the server is Info (3).
> java -jar Codex.jar [options]
- Client: specify two arguments, the ip address of the server and the port nuymber. By default, the client uses Socket/TCP connection and starts in graphical mode (GUI).
> java -jar Codex.jar \<serverIp\> \<port\> [options]

<p>
The behaviour of the application can be modify based on the following options

| Short Flag| Long Flag       | Mode   | Argument      | Description                     |
|-----------|-----------------|--------|---------------|----------------------------------
| -h        | --help          | none   | none          | Show application usage and help |
| -s        | --socketPort    | server | integer from 1024 to 65535          | Port number for TCP connection |
| -m        | --rmiPort       | server | integer from 1024 to 65535          | Port number for RMI connection |
| -f        | --fixed         | server | none          | Open connection on specified port, do not automatically find an available port |
| -l        | --limit         | server | integer >0    | Limit concurrent clients on the server |
| -v        | --verbosity     | server | integer from 1 to 4    | Verbosity level of the server log (1=Error,2=Warning,3=Info,4=Verbose) |
| -r        | --rmi           | client | none          | Run client with RMI connection |
| -t        | --tui           | client | none          | Run client in textual mode |
</p>