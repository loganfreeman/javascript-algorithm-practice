package com.flicklib.folderscanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.List;

import org.apache.commons.vfs2.CacheStrategy;
import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystem;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.cache.WeakRefFilesCache;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;
import org.apache.commons.vfs2.provider.ram.RamFileProvider;
import org.junit.Before;
import org.junit.Test;

public class ScanningTests {

    DefaultFileSystemManager manager;
    Scanner scanner;
    
    
    @Before
    public void setup() throws FileSystemException {
        scanner = new AdvancedFolderScanner(new MovieNameExtractor());
        
        manager = new DefaultFileSystemManager();
        manager.setFilesCache(new WeakRefFilesCache());
        manager.setCacheStrategy(CacheStrategy.ON_RESOLVE);
        manager.addProvider("mock", new RamFileProvider() {
            @Override
            protected FileSystem doCreateFileSystem(FileName name, FileSystemOptions fileSystemOptions) throws FileSystemException {
                return new MockFileSystem(name, fileSystemOptions);
            }
        });
        
        Builder b = new Builder(manager).get("mock://root").setFolder();
        
        {
            Builder folder = b.get("testCase1").setFolder().get("Tamara.Drewe.2010.DVDRip.XviD").setFolder();
            folder.get("file1.avi").setSize(50000000);
            folder.get("file2.avi").setSize(40000000);
            folder.get("file1.srt").setSize(50000);
            folder.get("file2.srt").setSize(50000);
            
        }
        
    }
    
    
    
    @Test
    public void test() throws FileSystemException {
        FileObject file = manager.resolveFile("mock://root/testCase1");
        List<FileGroup> files = scanner.scan(Collections.singleton(file), new MockAsyncMonitor());
        
        assertNotNull("files", files);
        assertEquals("files size", 1, files.size());
        FileGroup first = files.get(0);
        assertEquals("title", "tamara drewe", first.getTitle());
        assertEquals("audio", "en", first.getAudioLanguage().getLanguage());
        assertEquals("location", 1, first.getLocations().size());
        FileLocation fl = first.getLocations().iterator().next();
        assertEquals("location label", "testCase1", fl.getLabel());
        assertEquals("location path", "mock:///root/testCase1/Tamara.Drewe.2010.DVDRip.XviD", fl.getPath().toString());
        
        assertEquals("file list", 4, first.getFiles().size());
        assertNotNull("file1.avi found", first.getFileByName("file1.avi"));
        assertNotNull("file2.avi found", first.getFileByName("file2.avi"));
        assertNotNull("file1.srt found", first.getFileByName("file1.srt"));
        assertNotNull("file2.srt found", first.getFileByName("file2.srt"));
        
    }

}
