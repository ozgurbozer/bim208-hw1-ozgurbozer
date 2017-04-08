package ceng.bim208;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.zip.CRC32;

import static java.nio.file.FileVisitResult.SKIP_SUBTREE;
import static java.nio.file.FileVisitResult.CONTINUE;;

class RegularFileVisitor extends SimpleFileVisitor<Path> {
    Map<Long, List<Path>> map = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
        CRC32 crc32 = new CRC32();
        crc32.update(Files.readAllBytes(file));
        long key = crc32.getValue();
        
        if (attr.isRegularFile()) {
            List<Path> list = map.getOrDefault(key, new ArrayList<Path>());
            list.add(file);
            map.put(key, list);
        }

        return CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if (dir.toFile().isHidden())
            return SKIP_SUBTREE;

        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return CONTINUE;
    }

    public Map<Long, List<Path>> getDuplicateFileList() {
        return map;
    }
}
