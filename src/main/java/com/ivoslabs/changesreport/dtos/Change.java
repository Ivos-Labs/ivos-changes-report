/**
 * 
 */
package com.ivoslabs.changesreport.dtos;

/**
 * 
 * @author www.ivoslabs.com
 *
 */
public class Change {

    private Task task;

    private Rsc file;

    private int initLine;

    private int finalLine;

    private String description;

    private StringBuilder content = new StringBuilder();

    /**
     * Gets the task
     *
     * @return {@code Task} The task
     */
    public Task getTask() {
	return this.task;
    }

    /**
     * Sets the task
     *
     * @param task {@code Task} The task to set
     */
    public void setTask(Task task) {
	this.task = task;
    }

    /**
     * Gets the file
     *
     * @return {@code Rsc} The file
     */
    public Rsc getFile() {
	return this.file;
    }

    /**
     * Sets the file
     *
     * @param file {@code Rsc} The file to set
     */
    public void setFile(Rsc file) {
	this.file = file;
    }

    /**
     * Gets the initLine
     *
     * @return {@code int} The initLine
     */
    public int getInitLine() {
	return this.initLine;
    }

    /**
     * Sets the initLine
     *
     * @param initLine {@code int} The initLine to set
     */
    public void setInitLine(int initLine) {
	this.initLine = initLine;
    }

    /**
     * Gets the finalLine
     *
     * @return {@code int} The finalLine
     */
    public int getFinalLine() {
	return this.finalLine;
    }

    /**
     * Sets the finalLine
     *
     * @param finalLine {@code int} The finalLine to set
     */
    public void setFinalLine(int finalLine) {
	this.finalLine = finalLine;
    }

    /**
     * Gets the description
     *
     * @return {@code String} The description
     */
    public String getDescription() {
	return this.description != null ? this.description : "";
    }

    /**
     * Sets the description
     *
     * @param description {@code String} The description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * Gets the content
     *
     * @return {@code StringBuilder} The content
     */
    public StringBuilder getContent() {
	return this.content;
    }

    /**
     * Sets the content
     *
     * @param content {@code StringBuilder} The content to set
     */
    public void setContent(StringBuilder content) {
	this.content = content;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Change [\n  task=" + task + ", \n  file=" + file + ", \n  initLine=" + initLine + ", \n  finalLine=" + finalLine + ", \n  description=" + description + ", \n  content=\n" + content + "]";
    }

}
