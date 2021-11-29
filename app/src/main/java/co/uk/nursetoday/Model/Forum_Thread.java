package co.uk.nursetoday.Model;

public class Forum_Thread {

    String topic, author, date, message;


    public Forum_Thread(String topic, String author, String date, String message) {
        this.topic = topic;
        this.author = author;
        this.date = date;
        this.message = message;
    }

    public Forum_Thread() {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
