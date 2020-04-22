/**
 * 
 */
package com.ivoslabs.changesreport.dtos;

import java.io.File;

/**
 * @author www.ivoslabs.com
 *
 */
public class Rsc {

    private String project;

    private String parentPath;

    private String path;

    private String name;

    private File file;

    public Rsc() {
        this.file = new File("tasks.txt");
    }

    /**
     * Gets the project
     *
     * @return {@code String} The project
     */
    public String getProject() {
        return this.project;
    }

    /**
     * Sets the project
     *
     * @param project {@code String} The project to set
     */
    public void setProject(String project) {
        this.project = project;
    }

    /**
     * Gets the path
     *
     * @return {@code String} The path
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Sets the path
     *
     * @param path {@code String} The path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Gets the name
     *
     * @return {@code String} The name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name
     *
     * @param name {@code String} The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the file
     *
     * @return {@code File} The file
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Sets the file
     *
     * @param file {@code File} The file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Gets the parentPath
     *
     * @return {@code String} The parentPath
     */
    public String getParentPath() {
        return this.parentPath;
    }

    /**
     * Sets the parentPath
     *
     * @param parentPath {@code String} The parentPath to set
     */
    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Rsc [project=" + project + ", path=" + path + ", name=" + name + " ]";
    }

}
