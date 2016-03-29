package store;


import model.photo.element.Photo;
import model.photo.element.PhotoElement;
import model.photo.identifier.PhotoIdentifier;

import java.io.InputStream;

public interface PhotoElementStore {
    PhotoElement getPhotoElement(PhotoIdentifier photoIdentifier);
    InputStream getPhoto(PhotoIdentifier photoIdentifier);

    void createPhotoElement(PhotoIdentifier photoIdentifier);
    void createPhoto(Photo photo, InputStream photoContents);
    void updatePhoto(Photo photo, InputStream photoContents);
}
