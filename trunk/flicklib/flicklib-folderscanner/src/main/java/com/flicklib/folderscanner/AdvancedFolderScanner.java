/*
 * This file is part of Flicklib.
 *
 * Copyright (C) Francis De Brabandere
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flicklib.folderscanner;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flicklib.tools.LevenshteinDistance;

/**
 * Scans a folder for movies
 * 
 * @author francisdb
 */
@Singleton
public class AdvancedFolderScanner implements Scanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdvancedFolderScanner.class);

    /**
     * If a folder contains no other than these it is a movie folder
     * TODO make regex?
     */
    private static final String[] MOVIE_SUB_DIRS =
            new String[]{"subs", "subtitles", "cd1", "cd2", "cd3", "cd4", "sample", "covers", "cover", "approved", "info" };

    private final MovieNameExtractor movieNameExtractor;
   
    @Inject
    public AdvancedFolderScanner(final MovieNameExtractor movieNameExtractor) {
        this.movieNameExtractor = movieNameExtractor;
    }

    public AdvancedFolderScanner() {
        this.movieNameExtractor = new MovieNameExtractor();
    }


    private List<FileGroup> movies;
    private String currentLabel;

    /**
     * Scans the folders
     * 
     * @param folders
     * @return a List of MovieInfo
     *
     * TODO get rid of the synchronized and create a factory or pass all state data
     */
    @Override
    public synchronized List<FileGroup> scan(final Set<FileObject> folders, AsyncMonitor monitor) {
        movies = new ArrayList<FileGroup>();
        
        if (monitor != null) {
            monitor.start();
        }
        for (FileObject folder : folders) {
            try {
                URL url = folder.getURL();
                if (folder.exists()) {
                    currentLabel = folder.getName().getBaseName();
                    LOGGER.info("scanning "+url);
                    try {
                        browse(folder, monitor);
                    } catch (InterruptedException ie) {
                        LOGGER.info("task is cancelled!" + ie.getMessage());
                        return null;
                    }
                }else{
                    LOGGER.warn("folder "+folder.getURL()+" does not exist!");
                }
            } catch (FileSystemException e) {
                LOGGER.error("error during checking  " + folder + ", " + e.getMessage(), e);
            }
        }
        if (monitor != null) {
            monitor.finish();
        }
        return movies;
    }

    /**
     * 
     * @param folder
     * @param monitor 
     * @return true, if it contained movie file
     * @throws InterruptedException 
     * @throws FileSystemException 
     */
    private boolean browse(FileObject folder, AsyncMonitor monitor) throws InterruptedException, FileSystemException {
        URL url = folder.getURL();
        LOGGER.trace("entering "+url);
        FileObject[] files = folder.getChildren();
        if (monitor != null) {
            if (monitor.isCanceled()) {
                throw new InterruptedException("at "+url);
            }
            monitor.step("scanning " + url);
        }

        Set<String> plainFileNames = new HashSet<String>();
        int subDirectories = 0;
        int compressedFiles = 0;
        Set<String> directoryNames = new HashSet<String>();
        for (FileObject f : files) {
            if (isDirectory(f)) {
                subDirectories ++;
                directoryNames.add(f.getName().getBaseName().toLowerCase());
            } else {
                String ext = getExtension(f);
                if(ext == null){
                    LOGGER.trace("Ignoring file without extension: "+f.getURL());
                }else{
                    if (MovieFileType.getTypeByExtension(ext)==MovieFileType.COMPRESSED) {
                        compressedFiles ++;
                    }
                    if (ext != null && MovieFileFilter.VIDEO_EXTENSIONS.contains(ext)) {
                        plainFileNames.add(getNameWithoutExt(f));
                    }
                }
            }
        }
        // check for multiple compressed files, the case of:
        // Title_of_the_film/abc.rar
        // Title_of_the_film/abc.r01
        // Title_of_the_film/abc.r02
        if (compressedFiles > 0) {
            FileGroup fg = initStorableMovie(folder);
            fg.getLocations().add(new FileLocation(currentLabel, folder.getURL()));
            addCompressedFiles(fg, files );
            add(fg);
            return true;
        }
        if (subDirectories >= 2 && subDirectories <= 5) {
            // the case of :
            // Title_of_the_film/cd1/...
            // Title_of_the_film/cd2/...
            // with an optional sample/subs directory
            // Title_of_the_film/sample/
            // Title_of_the_film/subs/
            // Title_of_the_film/subtitles/
            // or
            // Title_of_the_film/bla1.avi
            // Title_of_the_film/bla2.avi
            // Title_of_the_film/sample/
            // Title_of_the_film/subs/
            if (isMovieFolder(directoryNames)) {
                FileGroup fg = initStorableMovie(folder);
                fg.getLocations().add(new FileLocation(currentLabel, folder.getURL()));
                for(String cdFolder:getCdFolders(directoryNames)){
                    addCompressedFiles(fg, files, cdFolder);
                }
                for(FileObject file: folder.getChildren()){
                    if(!isDirectory(file)){
                        String ext = getExtension(file);
                        if (MovieFileFilter.VIDEO_EXT_EXTENSIONS.contains(ext)) {
                            fg.getFiles().add(createFileMeta(file, MovieFileType.getTypeByExtension(ext)));
                        }
                    }
                }
                add(fg);
                return true;
            }
        }
        boolean subFolderContainMovie = false;
        for (FileObject f : files) {
            final String baseName = f.getName().getBaseName();
            if (isDirectory(f) && !baseName.equalsIgnoreCase("sample") && !baseName.startsWith(".")) {
                subFolderContainMovie |= browse(f, monitor);
            }
        }
        
        
        // We want to handle the following cases:
        // 1,
        // Title_of_the_film/abc.avi
        // Title_of_the_film/abc.srt
        // --> no subdirectory, one film -> the title should be name of the
        // directory
        //  
        // 2,
        // Title_of_the_film/abc-cd1.avi
        // Title_of_the_film/abc-cd1.srt
        // Title_of_the_film/abc-cd2.srt
        // Title_of_the_film/abc-cd2.srt
        //

        if (subDirectories>0 && subFolderContainMovie) {
            return genericMovieFindProcess(files) || subFolderContainMovie;
        } else {
            
            int foundFiles = plainFileNames.size();
            switch (foundFiles) {
                case 0:
                    return subFolderContainMovie;
                case 1: {
                    FileGroup fg = initStorableMovie(folder);
                    fg.getLocations().add(new FileLocation(currentLabel, folder.getURL()));
                    addFiles(fg, files,  plainFileNames.iterator().next());
                    add(fg);
                    return true;
                }
                case 2: {
                    Iterator<String> it = plainFileNames.iterator();
                    String name1 = it.next();
                    String name2 = it.next();
                    if (LevenshteinDistance.distance(name1, name2) < 3) {
                        // the difference is -cd1 / -cd2
                        FileGroup fg = initStorableMovie(folder);

                        fg.getLocations().add(new FileLocation(currentLabel, folder.getURL()));
                        addFiles(fg, files, name1);
                        add(fg);
                        return true;
                    }
                    // the difference is significant, we use the generic
                    // solution
                }
                default: {
                    return genericMovieFindProcess(files);
                }
            }
        }
    }

    private boolean isDirectory(FileObject f) throws FileSystemException {
        return f.getType().hasChildren();
    }

    /**
     * check that every sub folder name is some common movie related folder name, eg 'cd1', 'cd2', 'sample', etc...
     * 
     * @param subDirectoryNames
     * @return
     */
    private boolean isMovieFolder(Set<String> subDirectoryNames){
        boolean movieFolder = true;
        List<String> valid = Arrays.asList(MOVIE_SUB_DIRS);
        Iterator<String> iter = subDirectoryNames.iterator();
        String next;
        while(iter.hasNext() && movieFolder){
            next = iter.next();
            movieFolder = valid.contains(next);
            if(!movieFolder){
                LOGGER.trace("not movie folder because: " + next);
            }
        }
        return movieFolder;
    }

    /**
     * Return a set of directory names, which starts with 'cd','disk' or 'part'.
     * 
     * @param subDirectoryNames
     * @return
     */
    private Set<String> getCdFolders(Set<String> subDirectoryNames){
        Set<String> valid = new HashSet<String>();
        for(String folder:subDirectoryNames){
            if(folder.startsWith("cd") || folder.startsWith("disk") || folder.startsWith("part")){
                valid.add(folder);
            }
        }
        return valid;
    }

    /**
     * add the compressed files to the file group, which are in the specified directory.
     * @param sm
     * @param fg
     * @param fileList
     * @param folderName
     * @throws FileSystemException 
     */
    private void addCompressedFiles(FileGroup fg, FileObject[] fileList, String folderName) throws FileSystemException {
        for (FileObject f : fileList) {
            if (f.getType().hasChildren() && folderName.equals(f.getName().getBaseName().toLowerCase())) {
                addCompressedFiles(fg, f.getChildren());
            }
        }
    }

    /**
     * initialize a FileGroup 
     * @param folder
     * @param sm
     * @return
     * @throws FileSystemException 
     */
    private FileGroup initStorableMovie(FileObject folder) throws FileSystemException {
        FileGroup fg = new FileGroup();
        fg.setAudioLanguage(movieNameExtractor.getLanguageSuggestion(folder));
        fg.setTitle(movieNameExtractor.removeCrap(folder));
        return fg;
    }

    /**
     * Handle the one directory with several different movies case.
     * @param files
     * @return true, if a movie found
     * @throws FileSystemException 
     */
    private boolean genericMovieFindProcess(FileObject[] files) throws FileSystemException {
        Map<String, FileGroup> foundMovies = new HashMap<String, FileGroup>();
        for (FileObject f : files) {
            if (!f.getType().hasChildren()) {
                String extension = getExtension(f);
                if (MovieFileFilter.VIDEO_EXT_EXTENSIONS.contains(extension)) {
                    String baseName = movieNameExtractor.removeCrap(f);
                    FileGroup m = foundMovies.get(baseName);
                    if (m == null) {
                        m = new FileGroup();
                        m.setTitle(baseName);
                        m.setAudioLanguage(movieNameExtractor.getLanguageSuggestion(f));
                        m.getLocations().add(new FileLocation(currentLabel, f.getParent().getURL()));
                        foundMovies.put(baseName, m);
                    }
                    m.getFiles().add(createFileMeta(f, MovieFileType.getTypeByExtension(extension)));
                }
            }
        }
        boolean foundMovie = false;
        for (FileGroup m : foundMovies.values()) {
            if (m.isValid()) {
                add(m);
                foundMovie = true;
            }
        }
        return foundMovie;
    }

    /**
     * add the files, which has similar names, to the movie object
     * 
     * @param sm
     * @param files
     * @param next
     * @throws FileSystemException 
     */
    private void addFiles(FileGroup fg, FileObject[] files, String plainFileName) throws FileSystemException {
        for (FileObject f : files) {
            if (!f.getType().hasChildren()) {
                String baseName = getNameWithoutExt(f);
                String ext = getExtension(f);
                if (MovieFileFilter.VIDEO_EXT_EXTENSIONS.contains(ext)) {
                    if (LevenshteinDistance.distance(plainFileName, baseName) <= 3) {
                        fg.getFiles().add(createFileMeta(f, MovieFileType.getTypeByExtension(ext)));
                    }
                }
            }
        }
    }
    
    private void addCompressedFiles(FileGroup fg, FileObject[] files) throws FileSystemException {
        for (FileObject f : files) {
            if (!f.getType().hasChildren()) {
                String ext = getExtension(f);
                if(ext == null){
                    LOGGER.trace("Ignoring file without extension: "+f.getURL());
                }else{
                    MovieFileType type = MovieFileType.getTypeByExtension(ext);
                    if (type==MovieFileType.COMPRESSED || type==MovieFileType.NFO || type==MovieFileType.SUBTITLE) {
                        fg.getFiles().add(createFileMeta(f, type));
                    }
                }
            }
        }
    }

    protected FileMeta createFileMeta(FileObject f, MovieFileType type) throws FileSystemException {
        return new FileMeta(f.getName().getBaseName(), type, f.getContent().getSize());
    }

    private void add(FileGroup movie) {
        LOGGER.info("film:"+movie.getTitle()+" found at: "+movie.getLocations()+" {"+movie.getFiles()+'}');
        movie.guessReleaseType();
        movies.add(movie);
    }

    private String getExtension(FileObject file) {
        return getExtension(file.getName().getBaseName());
    }

    private String getExtension(String fileName) {
        int lastDotPos = fileName.lastIndexOf('.');
        if (lastDotPos != -1 && lastDotPos != 0 && lastDotPos < fileName.length() - 1) {
            return fileName.substring(lastDotPos + 1).toLowerCase();
        }
        return null;
    }

    private String getNameWithoutExt(FileObject file) {
        String name = file.getName().getBaseName();
        int lastDotPos = name.lastIndexOf('.');
        if (lastDotPos != -1 && lastDotPos != 0 && lastDotPos < name.length() - 1) {
            return name.substring(0, lastDotPos);
        }
        return name;
    }

}
