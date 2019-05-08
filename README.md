# ivos-changes-report

The changes-report.jar generates an excel with all the changes found by task / requirement / jira / etc, using comments within the files of a specified directory



1. **Expected Comment format**
     - **When multiple lines was modified**
       - At the begining of a new/modified block code
         ```
             task_id taks_title  [
             change_description
         ```
        - At the end of a new/modified block code
          ```
             task_id task_title]
            ```
     - **When only one line was changed**
         ```
            task_id task_title
            change_description
         ```
        
2. **Examples**         
     - **When multiple lines was modified**
       - For `.java, .js` 
         ```JAVA    
         // JIRA-12345 Jira title [
         // Change description
         if (cond1.... // added/modified lines 
                       // added/modified lines 
         else {...     // added/modified lines 
                       // added/modified lines 
         // JIRA-12345]
         ```
       - For `.xml`     
         ```xml    
         <!-- JIRA-12345 Jira title [ -->
         <!-- Change description -->
         <tag>         <!-- added/modified lines -->
             <subtag>  <!-- added/modified lines -->
             </subtag> <!-- added/modified lines -->
         </tag>        <!-- added/modified lines -->
         <!-- JIRA-12345] -->
          ```
       - For `.properties, .sh`  
         ```properties    
         # JIRA-12345 Jira title [
         # Change description
         prop1=value1
         prop2=value2
         prop3=value3
         # JIRA-12345]
         ```
       - For `.bat` 
         ```bat    
         :: JIRA-12345 Jira title [
         :: Change description
         call  ...
         xcopy ...
         xcopy ...
         :: JIRA-12345]
         ```
     - **When only one line was changed**
       - For `.java, .js` 
         ```JAVA     
         // JIRA-12345 Jira title 
         // Change description
         if (cond0 && cond1.... // added/modified line
         ```
    
    
3. **`tasks.txt` file example**

```properties
    source=C:\IVOS-SDC\dev\workspace\projects\project-a-parent\

    JIRA-10001 Title 1

    #JIRA-10002 Title 2
    JIRA-10003 Title 3
```

4. **Expected folder content**
   
```  
   Folder 
   ├───changes-report.jar
   ├───tasks.txt
   └───Template.xlsx
```  
    


5. **Execution**
   
   The following statment will generate `n` excel files one for each task/jira/etc in `tasks.txt`
```bash
    $ java -jar changes-report.jar
```

6. **Excel**
   The excel will contain a table with files, projects and the detail of the changes

|                           File                                 | Project/Component   |   Detail        |
| :---                                                           |     :---:           |          ---:   |
| module-x/src/main/java/com/ivoslabs/<br>**Main.java**   | modele-x | Description<br> line: 98 `if (cond...`  |
| module-y/<br>**pom.xml**                                | module-y | Description<br> line: 32 `<version>...` |




