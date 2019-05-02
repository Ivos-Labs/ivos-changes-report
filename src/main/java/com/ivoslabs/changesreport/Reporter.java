package com.ivoslabs.changesreport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class Reporter {

    private static final String IGNORE = ",.class,.log,.ctxt,.mtj.tmp,.jar,.war,.nar,.ear,.zip,.tar.gz,.rar,.factorypath,.project,.classpath,.svn,.apt_generated,.apt_generated_tests,.settings,target,logs,bin";

    private List<String> ignore = Arrays.asList(IGNORE.split(","));

    public static void main(String[] args) throws IOException {
	new Reporter().gen();
    }

    public void gen() {
	StringBuilder dir = new StringBuilder();
	List<Task> tasks = this.getTasksList(dir);
	List<Change> changes = this.getChangesList(tasks, dir.toString());

    }

    private List<Task> getTasksList(StringBuilder dir) {
	List<Task> list = new ArrayList<Task>();
	return list;
    }

    private List<Change> getChangesList(List<Task> tasks, String dir) {
	List<Change> list = new ArrayList<Change>();

	List<File> files = this.findFile(new File(dir));
	for (File file : files) {
	    list.addAll(this.getChangesByFile(file, tasks));
	}

	return list;
    }

    private List<Change> getChangesByFile(File file, List<Task> tasks) {
	List<Change> list = new ArrayList<>();

	new TextFile().readFile(file.getPath(), new BiConsumer<Reader, String>() {

	    @Override
	    public void accept(Reader reader, String curLine) {

		String clndLine = curLine.replaceAll(" ", "");

		if (reader.isOpen()) {

		    // check if is close 
		    if (reader.getChange() != null && clndLine.startsWith("//") && clndLine.equals("//" + reader.getChange().getTask().getNumber() + "]")) {
			// close
			
			 reader.setOpen(Boolean.FALSE);
			 reader.getChange().setFinalLine(reader.getLine());
			 reader.setChange(null);
		    }
		    
		} else if (tasks.stream().map(t -> t.getNumber()).anyMatch(n -> curLine.contains(n))) {
		    // if is comment
		    
		    Task task = tasks.stream().filter(t -> curLine.contains(t.getNumber())).findFirst().get();
		    
		    // create new change
		    Change  change = new Change();
		    change.setTask(task);
		    change.setInitLine(reader.getLine());
		    list.add(change);
		    
		    if (clndLine.endsWith("]")) {
			reader.setOpen(Boolean.TRUE);
			reader.setChange(change);
		    } else {
			 change.setFinalLine(reader.getLine());
		    }
		}
	    }
	});

	return list;
    }

    public List<File> findFile(File dir) {
	List<File> list = new ArrayList<>();

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
	return list;
    }

}
