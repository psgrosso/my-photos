package store.file;


import model.attribute.Value;
import model.attribute.Values;
import model.photo.PhotoKind;
import model.photo.element.Photo;
import model.photo.element.PhotoAlbum;
import model.photo.element.PhotoCollection;
import model.photo.element.PhotoElement;
import model.photo.element.PhotoYear;
import org.jetbrains.annotations.NotNull;
import store.file.util.PathAttributes;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FilePhotoWalker {
    private static final Pattern ALBUM_PATTERN = Pattern.compile("^([\\d]{4})\\.([\\d]{2})\\.([\\d]{2}) (.+)$");
    private static final Set<String> PHOTO_EXTENSIONS;
    private static final long MIN_SIZE = 1024L;
    
    private PhotoElement tryCollection(@NotNull Path path) {
        if (PathAttributes.isDirectory(path)) {
            Values values = PhotoKind.COLLECTION.valuesBuilderFor(path.getFileName().toString()).build();
            return new PhotoCollection(values);
        }
        return null;
    }

    private PhotoElement tryYear(@NotNull Path path) {
        try {
            String yearValue = path.getFileName().toString();
            PhotoYear.parseYear(yearValue);
            if (PathAttributes.isDirectory(path)) {
                // Year is OK, let's try parent directory
                PhotoElement collection = tryCollection(path.getParent());
                if (collection != null) {
                    return PhotoKind.YEAR.create(collection, PhotoKind.YEAR.valuesBuilderFor(yearValue).build());
                }
            }
        }
        catch (IllegalArgumentException e) {
            // TODO add log with the error
        }
        return null;
    }

    private PhotoElement tryAlbum(@NotNull Path path) {
        String candidate = path.getFileName().toString();
        final Matcher matcher = ALBUM_PATTERN.matcher(candidate);
        if (!matcher.matches() || !PathAttributes.isDirectory(path)) {
            return null;
        }
        try {
            int year = PhotoYear.parseYear(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2));
            int date = Integer.parseInt(matcher.group(3));
            Values albumValues = PhotoAlbum.valuesFor(matcher.group(4), year, month, date);
            if (albumValues != null) {
                PhotoElement parentYear = tryYear(path.getParent());
                if (parentYear != null) {
                    return PhotoKind.ALBUM.create(parentYear, albumValues);
                }
            }
        }
        catch (NumberFormatException e) {
            // NumberFormatException is a subclass of IllegalArgumentException
        }
        return null;
    }

    private PhotoElement tryPhoto(@NotNull Path path) {
        String candidate = path.getFileName().toString();
        int index = candidate.lastIndexOf('.');
        if (index == -1) {
            return null;
        }
        String extension = candidate.substring(index).toLowerCase();
        if (PHOTO_EXTENSIONS.contains(extension) && PathAttributes.isFile(path)) {
            long size = PathAttributes.getFileSize(path);
            if (size >= MIN_SIZE) {
                PhotoElement album = tryAlbum(path.getParent());
                if (album != null) {
                    Values photoValues = PhotoKind.PHOTO.valuesBuilderFor(candidate)
                            .with(new Value(Photo.F_SIZE, size)).build();
                    return PhotoKind.PHOTO.create(album, photoValues);
                }
            }
        }
        return null;
    }

    static {
        String[] extensions = {".jpg", ".mov", ".mp4", ".mp3", ".wav", ".jpeg"};
        PHOTO_EXTENSIONS = new HashSet<>();
        PHOTO_EXTENSIONS.addAll(Arrays.asList(extensions));
    }
}
