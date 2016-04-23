package command;


import model.photo.identifier.PhotoIdentifier;
import org.jetbrains.annotations.NotNull;

public final class GetPhotoCommand extends BaseCommand {
    private final PhotoIdentifier photoIdentifier;

    public GetPhotoCommand(@NotNull PhotoIdentifier photoIdentifier) {
        super(CommandType.GET_PHOTO);
        this.photoIdentifier = photoIdentifier;
    }

    public PhotoIdentifier getPhotoIdentifier() {
        return photoIdentifier;
    }
}
