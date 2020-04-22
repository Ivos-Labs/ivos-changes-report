package com.ivoslabs.changesreport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ivoslabs.changesreport.dtos.Change;
import com.ivoslabs.changesreport.dtos.Reader;
import com.ivoslabs.changesreport.dtos.Rsc;
import com.ivoslabs.changesreport.dtos.Task;
import com.ivoslabs.changesreport.excel.Excel;

/**
 * 
 * @author www.ivoslabs.com
 *
 */
public class Reporter {

    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(Reporter.class);

    static {
        Locale.setDefault(Locale.ENGLISH);
        TimeZone.setDefault(TimeZone.getTimeZone("America/Mexico_City"));
    }

    private static final String IGNORE = ",.class,.log,.ctxt,.mtj.tmp,.jar,.war,.nar,.ear,.zip,.tar.gz,.rar,.factorypath,.project,.classpath,.svn,.apt_generated,.apt_generated_tests,.settings,target,logs,bin";

    private List<String> ignore = Arrays.asList(IGNORE.split(","));

    public static void main(String[] args) throws IOException {
        new Reporter().gen();
    }

    public void gen() {
        StringBuilder dir = new StringBuilder();
        List<Task> tasks = this.getTasksList(dir);

        LOGGER.info("Getting changes \n  source: {}\n  tasks: {}\n", dir, String.join(",", tasks.stream().map(t -> t.getNumber()).collect(Collectors.toList())));
        List<Change> changes = this.getChangesList(tasks, dir.toString());

        changes = changes.stream().sorted((a, b) -> a.getTask().getNumber().compareTo(b.getTask().getNumber())).collect(Collectors.toList());

        List<Change> aux = new ArrayList<>();
        if (!changes.isEmpty()) {
            String prevTask = changes.get(0).getTask().getNumber();
            for (Change change : changes) {

                if (!Objects.equals(prevTask, change.getTask().getNumber())) {
                    prevTask = change.getTask().getNumber();
                    Task t = aux.get(0).getTask();
                    new Excel().write(aux, t.getNumber() + "-" + t.getDescription() + ".xlsx", t.getDescription());
                    LOGGER.info("Excel {} was created", t.getNumber() + "-" + t.getDescription() + ".xlsx");
                    aux.clear();
                    aux.add(change);
                } else {
                    aux.add(change);
                }
            }

            if (!aux.isEmpty()) {
                Task t = aux.get(0).getTask();
                new Excel().write(aux, t.getNumber() + "-" + t.getDescription() + ".xlsx", t.getDescription());
                LOGGER.info("Excel {} was created", t.getNumber() + "-" + t.getDescription() + ".xlsx");
            }

        }

    }

    /**
     * Get task list and dir in header of tasks.txt
     * 
     * @param dir
     * @return
     */
    private List<Task> getTasksList(StringBuilder dir) {
        List<Task> list = new ArrayList<Task>();

        new TextFile().readFile(new Rsc(), new BiConsumer<Reader, String>() {

            @Override
            public void accept(Reader t, String u) {

                if (!u.trim().isEmpty() && !u.startsWith("#")) {
                    if (dir.toString().length() == 0 && u.contains("source=")) {
                        dir.append(u.split("=")[1]);
                    }

                    /** ind of space */
                    int ind = u.indexOf(" ");
                    if (ind != -1) {
                        Task task = new Task();
                        task.setNumber(u.substring(0, ind));
                        task.setDescription(u.substring(ind + 1));
                        list.add(task);
                    }

                }

            }
        });
        return list;
    }

    private List<Change> getChangesList(List<Task> tasks, String dir) {
        List<Change> list = new ArrayList<Change>();

        List<File> files = this.findFile(new File(dir));
        for (File file : files) {
            Rsc rsc = new Rsc();
            rsc.setFile(file);
            rsc.setName(file.getName());

            this.setPathInProject(rsc);
            list.addAll(this.getChangesByFile(rsc, tasks));
        }

        return list;
    }

    private List<Change> getChangesByFile(Rsc file, List<Task> tasks) {
        List<Change> list = new ArrayList<>();

        new TextFile().readFile(file, new BiConsumer<Reader, String>() {

            @Override
            public void accept(Reader reader, String curLine) {

                String clndLine = curLine.replaceAll(" ", "");

                if (reader.isOpen()) {
                    // when reader is open reader.getChange() must return a value

                    // check if the line contains close pattern
                    // //TASK-001] JAVA
                    // <!--TASK-001]--> XML
                    // #TASK-001] PROPERTIE
                    // ::TASK-001] CMD
                    if (clndLine.contains(reader.getChange().getTask().getNumber() + "]")) {
                        // close

                        reader.setOpen(Boolean.FALSE);
                        reader.setWaitCloseTag(Boolean.FALSE);
                        reader.setWaitDesc(Boolean.FALSE);
                        reader.getChange().setFinalLine(reader.getLine() - 1);
                        reader.setChange(null);
                    } else if (reader.isWaitDesc()) {

                        if (clndLine.startsWith("//") || clndLine.startsWith("::") || clndLine.startsWith("<!--") || clndLine.startsWith("#")) {
                            // is comment
                            if (clndLine.startsWith("<!--")) {
                                reader.getChange().setDescription(curLine.trim().substring(4, curLine.trim().length() - 3).trim());
                            } else {
                                reader.getChange().setDescription(curLine.trim().substring(2).trim());
                            }

                            reader.setWaitDesc(Boolean.FALSE);
                        } else if (!clndLine.isEmpty()) {
                            // change no has description
                            reader.getChange().getContent().append("Line ").append(reader.getLine()).append(": ").append(curLine).append("\n");
                            reader.setWaitDesc(Boolean.FALSE);
                            if (!reader.isWaitCloseTag()) {
                                reader.setOpen(Boolean.FALSE);
                                reader.setWaitDesc(Boolean.FALSE);
                                reader.getChange().setFinalLine(reader.getLine());
                                reader.setChange(null);
                            }
                        }
                    } else if (!reader.isWaitCloseTag() && !clndLine.isEmpty()) {
                        // is not end neither desc
                        reader.getChange().getContent().append("Line ").append(reader.getLine()).append(": ").append(curLine).append("\n");
                        reader.setOpen(Boolean.FALSE);
                        reader.getChange().setFinalLine(reader.getLine());
                        reader.setChange(null);
                    } else if (reader.isWaitCloseTag()) {
                        reader.getChange().getContent().append("Line ").append(reader.getLine()).append(": ").append(curLine).append("\n");
                    }

                } else if (tasks.stream().map(t -> t.getNumber()).anyMatch(n -> curLine.toLowerCase().contains(n.toLowerCase()))) {
                    // if is comment

                    Task task = tasks.stream().filter(t -> curLine.toLowerCase().contains(t.getNumber().toLowerCase())).findFirst().get();

                    // create new change
                    Change change = new Change();
                    change.setTask(task);
                    change.setInitLine(reader.getLine() + 1);

                    change.setFile(file);

                    reader.setOpen(Boolean.TRUE);
                    reader.setWaitDesc(Boolean.TRUE);
                    reader.setChange(change);

                    list.add(change);

                    if (clndLine.contains("[")) {
                        reader.setWaitCloseTag(Boolean.TRUE);
                    } else {
                        reader.setWaitCloseTag(Boolean.FALSE);
                    }
                }
            }
        });

        return list;
    }

    public List<File> findFile(File dir) {
        List<File> list = new ArrayList<>();

        if (dir.exists()) {

            for (File f : dir.listFiles()) {
                String n = f.getName();
                if (f.isDirectory()) {
                    if (!ignore.contains(n)) {
                        list.addAll(findFile(f));
                    }
                } else if (this.ignore.stream().filter(na -> na.startsWith(".")).noneMatch(na -> na.equals(n))) {
                    list.add(f);
                }
            }
        } else {
            throw new ChangesReportExcecption("Path '" + dir.getPath() + "' not exists");
        }
        return list;
    }

    public void setPathInProject(Rsc rsc) {
        String project;
        File file = rsc.getFile();

        int ind = file.getPath().indexOf("\\src\\");

        if (ind != -1) {
            project = new File(file.getPath().substring(0, ind)).getName();
        } else {
            project = file.getParentFile().getName();
        }

        //
        ind = file.getPath().indexOf("\\" + project + "\\");
        rsc.setPath(file.getPath().substring(ind + 1));
        rsc.setParentPath(file.getParentFile().getPath().substring(ind + 1));

        rsc.setProject(project);
    }

}
