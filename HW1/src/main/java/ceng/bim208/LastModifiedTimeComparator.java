package ceng.bim208;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;

/**
 * Compare the last modified date/time of two paths
 */
class LastModifiedTimeComparator implements Comparator<Path> {
    @Override
    public int compare(Path p1, Path p2) {
        try {
            BasicFileAttributes attr1 = Files.readAttributes(p1, BasicFileAttributes.class);
            BasicFileAttributes attr2 = Files.readAttributes(p2, BasicFileAttributes.class);

            return attr2.lastModifiedTime().compareTo(attr1.lastModifiedTime());
        } catch (IOException exception) {
            return 0;
        }
    }
}
