package org.Darwyi.courseProject.model;

public class Lecture extends Material {
    private static final long serialVersionUID = 1L;
    private String body;

    public Lecture(long id, String title, String body){
        super(id, title);
        this.body = body;
    }

    public String getBody(){ return body; }
    public void setBody(String body){ this.body = body; }

    @Override public String typeName(){ return "Текстова лекція"; }
    @Override public String contentInfo(){
        return "текст, %d симв.".formatted(body == null ? 0 : body.length());
    }
}
