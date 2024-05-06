package com.example.Newsternews.EmailManagement;

public class RecommendationEmailTemplate
{
    private String topic;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String name;
    private String description;
    private String url;

    private String emailBody;

    public String getEmailBody() {
        emailBody = "<h2>" + "News from " + topic + ":</h2>";
        emailBody += "<h3>" + name + "</h3>";
        emailBody += "<i>" + description + "</i>";
        emailBody += "<p>" + url + "</p>";

        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }
}