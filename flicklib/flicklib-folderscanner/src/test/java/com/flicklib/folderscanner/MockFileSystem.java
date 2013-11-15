package com.flicklib.folderscanner;

import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.provider.AbstractFileName;
import org.apache.commons.vfs2.provider.ram.RamFileSystem;

public class MockFileSystem extends RamFileSystem {

    public MockFileSystem(FileName rootName, FileSystemOptions fileSystemOptions) {
        super(rootName, fileSystemOptions);
    }

    @Override
    protected FileObject createFile(AbstractFileName name) throws Exception {
        return new MockFileObject(name, this);
    }


}
