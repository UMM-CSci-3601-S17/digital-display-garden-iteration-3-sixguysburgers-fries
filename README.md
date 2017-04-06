# Digital Display Garden
[![Build Status](https://travis-ci.org/UMM-CSci-3601-S17/digital-display-garden-iteration-3-sixguysburgers-fries.svg?branch=master)](https://travis-ci.org/UMM-CSci-3601-S17/digital-display-garden-iteration-3-sixguysburgers-fries/branches)
Software Design S2017, Iteration 3, Team _Six Guys Burgers 'n Fries_ 

This repository is a fork from [Iteration 2 , _Team Grimaldi_](https://github.com/UMM-CSci-3601-S17/digital-display-garden-iteration-2-grimaldi), who forked from [Iteration 1 , Team _Claude Arabo_](https://github.com/UMM-CSci-3601-S17/digital-display-garden-iteration-1-claudearabo).

### Note, for fixing self-port locks:  
##### Step One: Find the Process that's hoarding the port
##### Step Two: Kill the Process

This only works if you accidently locked the port on yourself, otherwise seek an admin or restart the computer. However, restarting the computer is frowned upon in the lab.

to kill the process type: "kill PID"  
For example:
$ kill 3944  
$  
If nothing is returned, the the process was successfully killed.

Finding the PID is a lot harder. Three methods are shown for reduncancy's sake.

METHOD 1: Use Fuser.

$ fuser 4567/tcp  
<pre>4567/tcp:            10030</pre>  
$ kill 10030

METHOD 2: Try using netstat(works best with full-screen terminal), find the port number under Local Address, follow the row to the last column for the PID.

$ netstat -tulpn  
<pre>tcp6       0      0 :::4567                 :::\*                    LISTEN      10123/java</pre>  
$ kill 10123

METHOD 3: Type top, and pick a random Java process. If it doesn't work, keep trying!

$ top   
<pre>10123 mitch809     . . .      java</pre>  
$ kill 10123


## Purchased Stories
* Updating the Database
* Graphical Data Representation
* Upload and Display/Store one Image per plant.

## Documentation
* [Excel File Requirements](Documentation/ExcelFileRequirements.md)  
* [Excel Parser Documentation](Documentation/ExcelParser.md) 



## Libraries used
### Client-Side
* **Angular 2**
* **Jasmine** and **Karma** 

### Server-Side
* **Java** 
* **Spark** is used for the server operations
* **JUnit** is used for testing
* **Apache** is used for importing and exporting data in .xlsx format
* **zxing** is used for generating QR codes (supports reading them if we want) 
* **joda** is used for making an unique LiveUploadID


## Resources

- [Bootstrap Components][bootstrap]
- [Mongo's Java Drivers (Mongo JDBC)][mongo-jdbc]
- [What _is_ Angular 2... why TypeScript?][angular-2]
- [What _is_ webpack...?][whats-webpack]
- [Testing Angular 2 with Karma/Jasmine][angular2-karma-jasmine]

[angular-2]: https://www.infoq.com/articles/Angular2-TypeScript-High-Level-Overview
[angular2-karma-jasmine]: http://twofuckingdevelopers.com/2016/01/testing-angular-2-with-karma-and-jasmine/
[labtasks]: LABTASKS.md
[travis]: https://travis-ci.org/
[whats-webpack]: https://webpack.github.io/docs/what-is-webpack.html
[bootstrap]: https://getbootstrap.com/components/ 
[mongo-jdbc]: https://docs.mongodb.com/ecosystem/drivers/java/ 
