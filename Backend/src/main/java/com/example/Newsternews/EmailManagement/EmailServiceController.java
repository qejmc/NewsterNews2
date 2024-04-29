package com.example.Newsternews.EmailManagement;

import com.example.Newsternews.EmailManagement.EmailService.EmailSendingService;
import com.example.Newsternews.Keys.Keys;
import com.example.Newsternews.DatabaseManagement.News;
import com.example.Newsternews.DatabaseManagement.Topic;
import com.example.Newsternews.DatabaseManagement.User;
import com.example.Newsternews.DatabaseManagement.NewsRepository;
import com.example.Newsternews.DatabaseManagement.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static com.example.Newsternews.EmailManagement.EmailFormatter.getNewsEmailSubject;

@RestController
public class EmailServiceController {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    UserRepository userRepository;

    private final EmailSendingService emailSendingService;

    public EmailServiceController(EmailSendingService emailSendingService) {
        this.emailSendingService = emailSendingService;
    }

    //@Scheduled(cron = "0 0 8,12,18 * * ?") //At 8am, 12am, and 6pm
    @Scheduled(cron = "0 19,20 20 * * ?")
    public ResponseEntity sendNewsEmails() throws IOException, MessagingException, InterruptedException {
        List<User> userList = userRepository.findAll();
        LocalTime ArizonaNow = LocalTime.now(ZoneId.of("America/Phoenix"));
        int hour = ArizonaNow.getHour();

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            int frequency = user.getFrequency();
            ArrayList<Integer> topicIntList = new ArrayList<>();
            String topicStr = user.getTopics();
            String[] topicStrList = topicStr.split(" ");
            for (int j = 0; j < topicStrList.length; j++) {
                topicIntList.add(Integer.parseInt(topicStrList[j]));
            }

            LinkedList<EmailTemplate> emailTemplateList = new LinkedList<>();

            //TODO: Remove
            for (int j = 0; j < topicIntList.size(); j++) {
                String searchTerm = Topic.SearchTermList[topicIntList.get(j)-1];
                emailTemplateList.add(getSearchResults(searchTerm));
                Thread.sleep(5000);
            }
            String body1 = "";
            for (int j = 0; j < emailTemplateList.size(); j++) {
                body1 += emailTemplateList.get(j).getEmailBody();
            }

            Random random = new Random();

            int random_int = random.nextInt(3);

            String emailSubject = getNewsEmailSubject(emailTemplateList.get(random_int));

            sendEmail(user.getEmail(), emailSubject, body1);
            //END REMOVE

            if(hour == 8){
                for (int j = 0; j < topicIntList.size(); j++) {
                    String searchTerm = Topic.SearchTermList[topicIntList.get(j)-1];
                    emailTemplateList.add(getSearchResults(searchTerm));
                    Thread.sleep(5000);
                }
                String body = "";
                for (int j = 0; j < emailTemplateList.size(); j++) {
                    body += emailTemplateList.get(j).getEmailBody();
                }
                sendEmail(user.getEmail(), "Newster News - Check out the Latest News", body);

            }else if(hour == 18){
                if(frequency != 1){
                    for (int j = 0; j < topicIntList.size(); j++) {
                        String searchTerm = Topic.SearchTermList[topicIntList.get(j)-1];
                        emailTemplateList.add(getSearchResults(searchTerm));
                        Thread.sleep(5000);
                    }
                    String body = "";
                    for (int j = 0; j < emailTemplateList.size(); j++) {
                        body += emailTemplateList.get(j).getEmailBody();
                    }
                    sendEmail(user.getEmail(), "Newster News - Check out the Latest News", body);
                }
            }else if(hour == 12){ //hour == 12
                if(frequency == 3){
                    for (int j = 0; j < topicIntList.size(); j++) {
                        String searchTerm = Topic.SearchTermList[topicIntList.get(j)-1];
                        emailTemplateList.add(getSearchResults(searchTerm));
                        Thread.sleep(5000);
                    }
                    String body = "";
                    for (int j = 0; j < emailTemplateList.size(); j++) {
                        body += emailTemplateList.get(j).getEmailBody();
                    }
                    sendEmail(user.getEmail(), "Newster News - Check out the Latest News", body);
                }
            }
        }

        return ResponseEntity.ok("Emails have been sent.");
    }

    public void sendEmail(String emaillAddress, String subject, String body) throws MessagingException
    {
        this.emailSendingService.sendEmail(emaillAddress, subject,
                Keys.EMAIL_HEADER + body);
        System.out.println("Mail is Sent");
    }

    // Randomly picks 3 articles per search term
    public EmailTemplate getSearchResults(String searchTerm)
    {
        int topic = Topic.getSearchTermValue(searchTerm);

        LinkedList<News> searchResults = newsRepository.findByTopics(topic);

        Random rand = new Random();

        // 0-9 index; 10 articles queried by API interface
        int topic_index = rand.nextInt(10);

        EmailTemplate template = new EmailTemplate();
        template.setName1(searchResults.get(topic_index).getNewsTitle());
        template.setDescription1(searchResults.get(topic_index).getDesc());
        template.setUrl1(searchResults.get(topic_index).getNewsUrl());

        // Non-repeats
        int topic_index2 = topic_index;
        while(topic_index2 == topic_index) {
            topic_index2 = rand.nextInt(10);
        }

        template.setName2(searchResults.get(topic_index2).getNewsTitle());
        template.setDescription2(searchResults.get(topic_index2).getDesc());
        template.setUrl2(searchResults.get(topic_index2).getNewsUrl());

        // Non-repeats
        int topic_index3 = topic_index2;
        while(topic_index3 == topic_index2 || topic_index3 == topic_index) {
            topic_index3 = rand.nextInt(10);
        }

        template.setName3(searchResults.get(topic_index3).getNewsTitle());
        template.setDescription3(searchResults.get(topic_index3).getDesc());
        template.setUrl3(searchResults.get(topic_index3).getNewsUrl());

        return template;
    }

}