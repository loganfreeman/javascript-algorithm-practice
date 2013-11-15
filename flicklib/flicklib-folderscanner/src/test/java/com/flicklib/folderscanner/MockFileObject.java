/**
 * 
 */
package com.flicklib.folderscanner;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.provider.AbstractFileName;
import org.apache.commons.vfs2.provider.ram.RamFileObject;
import org.apache.commons.vfs2.provider.ram.RamFileSystem;

/**
 * @author zsombor
 *
 */
public class MockFileObject extends RamFileObject {

    long size = -1;
    
    
    public MockFileObject(AbstractFileName name, RamFileSystem fs) {
        super(name, fs);
    }
    
    @Override
    protected long doGetContentSize() throws Exception {
        if (size>=0) {
            return size;
        }
        return super.doGetContentSize();
    }

    
    public void setSize(long size) {
        this.size = size;
    }

    
    public static void setSize(FileObject obj, long size) {
        if (obj instanceof MockFileObject) {
            ((MockFileObject)obj).setSize(size);
        } else {
            System.err.println("setSize for "+obj+" is not implemented!");
        }
    }
}
