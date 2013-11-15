package com.flicklib.folderscanner;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;

public class Builder {

    DefaultFileSystemManager manager;

    FileObject object;
    
    public Builder(DefaultFileSystemManager manager) {
        this.manager = manager;
    }

    public Builder(DefaultFileSystemManager manager, FileObject fn) {
        this.manager = manager;
        this.object = fn;
    }
    
    
    public Builder get(String name) throws FileSystemException {
        if (object == null) {
            return new Builder(manager, manager.resolveFile(name));
        } else {
            return new Builder(manager, manager.resolveFile(object, name));
        }
    }
    
    public Builder setFolder() throws FileSystemException {
        object.createFolder();
        return this;
    }
    
    public Builder setSize(long size) throws FileSystemException {
        object.createFile();
        MockFileObject.setSize(object, size);
        return this;
    }
    
    
}
