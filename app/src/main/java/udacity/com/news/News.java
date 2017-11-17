package udacity.com.news;

// Класс новость
public class News {

    // Заголовок статьи
    private String title;

    // Имя раздела
    private String sectionName;

    // Дата публикации
    private String date;

    // Ссылка
    private String url;

    // Конструктор
    public News(String title, String sectionName, String date, String url) {
        this.title = title;
        this.sectionName = sectionName;
        this.date = date;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }
}
