/**
 * Created by L on 18/10/13.
 */
public class SongInfo {
    private String title;
    private String name_usual;
    private String url;


    public void setTitle(String title) {
        this.title=title;
    }
    public String getTitle(){
        return title;
    }

    public void setName_usual(String name_usual){
        this.name_usual=name_usual;
    }

    public String getName_usual(){
        return name_usual;
    }

    public void setUrl(String url){
        this.url=url;
    }
    public String getUrl(){
        return url;
    }

    @Override
    public String toString() {
        return "[title="+title+", name_usual="+name_usual+", url="+url+"]";
    }
}
