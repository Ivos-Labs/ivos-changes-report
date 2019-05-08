# ivos-changes-report
Create changes report


Expected format

 E.g 1 when multiple lines was modified

```JAVA    
    // JIRA-12345 Jira title [
    // Change description
    if (value.... // added/modified lines 
    
    else {...
    // JIRA-12345]
```

 E.g 2 when only one line was changed

```JAVA  
    // JIRA-12345 Jira title 
    // Change description
    if (value.... // added/modified line
```
    
    
 tasks.txt file example

```properties
    source=C:\IVOS-SDC\dev\workspace\projects\ivos-changes-report\

    JIRA-10001 Title 1

    #JIRA-10002 Title 2
    JIRA-10003 Title 3
```

Tree

    -changes-report.jar
    -tasks.txt
    -Template.xlsx
    


Execution

```bash
    $ java -jar changes-report.jar
```

