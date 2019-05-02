package com.ivoslabs.changesreport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reporter {

    private static final String IGNORE = ",.class,.log,.ctxt,.mtj.tmp,.jar,.war,.nar,.ear,.zip,.tar.gz,.rar,.factorypath,.project,.classpath,.svn,.apt_generated,.apt_generated_tests,.settings,target,logs,bin";

    private List<String> ignore = Arrays.asList(IGNORE.split(","));

    public static void main(String[] args) throws IOException {

	// get all files ignoring wars ears target etc

//	String path = "C:\\TELCEL-SDC\\dev\\workspace\\sicatelWSBES-parent\\sicatelWSBES-client-impl";
	String path = "C:\\TELCEL-SDC\\dev\\workspace\\sicatelWSBES-parent";

	List<File> files = new Reporter().findFile(new File(path));
	for (File file : files) {
	    System.out.println(file.getPath());
	}

    }
    
    
    public void gen(String dir) {
	List<Task> jiras = new ArrayList<>();
	
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
