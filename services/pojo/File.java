package com.traineeproject.core.services.pojo;

public class File {
    private String fileName;
    private String filePath;
    private String fileExtension;
    private String fileStatus;
    private String fileWhenActivated;
    private String fileWhomActivated;

    public File(String fileName, String filePath, String fileExtension, String fileStatus, String fileWhenActivated, String fileWhomActivated) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileExtension = fileExtension;
        this.fileStatus = fileStatus;
        this.fileWhenActivated = fileWhenActivated;
        this.fileWhomActivated = fileWhomActivated;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getFileWhenActivated() {
        return fileWhenActivated;
    }

    public void setFileWhenActivated(String fileWhenActivated) {
        this.fileWhenActivated = fileWhenActivated;
    }

    public String getFileWhomActivated() {
        return fileWhomActivated;
    }

    public void setFileWhomActivated(String fileWhomActivated) {
        this.fileWhomActivated = fileWhomActivated;
    }
}
