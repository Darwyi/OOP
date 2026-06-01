package org.Darwyi.courseProject.model;

public class VideoLecture extends Material {
    private static final long serialVersionUID = 1L;
    private String url;
    private int durationMinutes;

    public VideoLecture(long id, String title, String url, int durationMinutes){
        super(id, title);
        this.url = url;
        this.durationMinutes = durationMinutes;
    }

    public String getUrl(){ return url; }
    public int getDurationMinutes(){ return durationMinutes; }

    @Override public String typeName(){ return "Відеолекція"; }
    @Override public String contentInfo(){
        return "відео %d хв (%s)".formatted(durationMinutes, url);
    }
}
