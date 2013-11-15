package com.flicklib.folderscanner;

public class FileMeta {
    private final String name;

    private final MovieFileType type;

    private final long size;

    public FileMeta(String name, MovieFileType type, long size) {
        super();
        this.name = name;
        this.type = type;
        this.size = size;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the type
     */
    public MovieFileType getType() {
        return type;
    }

    /**
     * @return the size
     */
    public long getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "File(name:" + name + ",type:" + type + ",size:" + size + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FileMeta) {
            FileMeta f = (FileMeta) obj;
            return f.name .equals(name) && f.type.equals(type) && f.size == size;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return (int) (name.hashCode() ^ type.hashCode() ^ size);
    }
}
