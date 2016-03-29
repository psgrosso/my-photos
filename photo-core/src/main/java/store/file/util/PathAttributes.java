package store.file.util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;


public final class PathAttributes {
    private static Path lastPath;
    private static BasicFileAttributes lastAttributes;

    public static boolean isDirectory(@NotNull Path path) {
        BasicFileAttributes attrs = getAttributes(path);
        return attrs != null && attrs.isDirectory();
    }

    public static boolean isFile(@NotNull Path path) {
        BasicFileAttributes attrs = getAttributes(path);
        return attrs != null && attrs.isRegularFile() && Files.isReadable(path);
    }

    public static long getFileSize(@NotNull Path path) {
        BasicFileAttributes attrs = getAttributes(path);
        return (attrs == null) ? 0L : (isFile(path)) ? attrs.size() : 0L;
    }

    private static BasicFileAttributes getAttributes(final Path path) {
        if (lastPath == null || !lastPath.equals(path)) {
            lastPath = path;
            try {
                lastAttributes = Files.readAttributes(path,
                        BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
            } catch (IOException e) {
                return null;
            }
        }
        return lastAttributes;
    }
}
