package image;

public class Image {
    private String path;
    private String thumb;

    public String getPath() {
        return path;
    }

    public Image() {
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Image(String path, String thumb) {
        this.path = path;
        this.thumb = thumb;
    }
}
