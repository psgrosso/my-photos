package command;


import model.photo.element.Photo;
import model.photo.element.PhotoElement;
import org.jetbrains.annotations.NotNull;

public final class PutPhotoCommand extends BaseCommand {
    private final Photo photo;

    public PutPhotoCommand(@NotNull Photo photo) {
        super(CommandType.PUT_PHOTO);
        this.photo = photo;
    }

    public PhotoElement getPhotoElement() {
        return photo;
    }

    @Override
    public long getDataLength() {
        return photo.getSize();
    }
}
