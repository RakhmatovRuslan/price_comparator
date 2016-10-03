package entity;

import javafx.beans.property.SimpleBooleanProperty;

import java.io.File;

/**
 * Created by RUSLAN on 16.06.2016.
 */
public class TemplateFile {
    private Integer id;
    private String fileName;
    private File template;
    private SimpleBooleanProperty checked = new SimpleBooleanProperty(false);

    public TemplateFile() {
    }

    public TemplateFile(File template) {
        this.template = template;
    }

    public File getTemplate() {
        return template;
    }

    public void setTemplate(File template) {
        this.template = template;
    }

    public boolean getChecked() {
        return checked.get();
    }

    public SimpleBooleanProperty checkedProperty() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
