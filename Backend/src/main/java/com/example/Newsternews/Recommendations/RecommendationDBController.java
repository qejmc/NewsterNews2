package com.example.Newsternews.Recommendations;

import com.example.Newsternews.DatabaseManagement.*;
import com.example.Newsternews.EmailManagement.EmailService.EmailSendingService;
import com.example.Newsternews.EmailManagement.RecommendationEmailTemplate;
import com.example.Newsternews.Keys.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static com.example.Newsternews.DatabaseManagement.Topic.SearchTermList;
import static com.example.Newsternews.EmailManagement.EmailFormatter.getNewsEmailSubject;
import static com.example.Newsternews.Recommendations.CalculateRecommendations.getRecommendedTopics;

@RestController
public class RecommendationDBController
{
    @Autowired
    NewsRepository newsRepository;

    @Autowired
    UserRepository userRepository;

    private final EmailSendingService emailSendingService;

    public RecommendationDBController(EmailSendingService emailSendingService) {
        this.emailSendingService = emailSendingService;
    }

    //@Scheduled(cron = "0 40 17 * * ?")
    @Scheduled(cron = "0 0 15 * * ?")
    public ResponseEntity sendRecommendationEmails() throws IOException, MessagingException, InterruptedException {
        List<User> userList = userRepository.findAll();

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            int optIn = user.getOptIn();

            if(optIn == 0)
            {
                continue;
            }

            int frequency = user.getFrequency();
            ArrayList<Integer> topicIntList = new ArrayList<>();
            String topicStr = getRecommendedTopics(user.getTopics());

            String[] topicStrList = topicStr.split(" ");
            for (int j = 0; j < topicStrList.length; j++) {
                topicIntList.add(Integer.parseInt(topicStrList[j]));
            }

            LinkedList<RecommendationEmailTemplate> emailTemplateList = new LinkedList<>();

            for (int j = 0; j < topicIntList.size(); j++) {
                String searchTerm = SearchTermList[topicIntList.get(j)-1];
                emailTemplateList.add(getSearchResults(searchTerm));
                Thread.sleep(5000);
            }
            String body1 = "";
            for (int j = 0; j < emailTemplateList.size(); j++) {
                body1 += emailTemplateList.get(j).getEmailBody();
            }

            Random random = new Random();

            int random_int = random.nextInt(3);

            sendEmail(user.getEmail(), body1);
        }

        return ResponseEntity.ok("Emails have been sent.");
    }

    public void sendEmail(String emaillAddress, String body) throws MessagingException
    {
        this.emailSendingService.sendEmail(emaillAddress, "Your recommendations from NewsterNews",
                Keys.RECOMMENDATION_EMAIL_HEADER + body);
        System.out.println("Recommendations are Sent");
    }

    // Randomly picks 3 articles per search term
    public RecommendationEmailTemplate getSearchResults(String searchTerm)
    {
        int topic = Topic.getSearchTermValue(searchTerm);

        LinkedList<News> searchResults = newsRepository.findByTopics(topic);

        Random rand = new Random();

        // 0-9 index; 10 articles queried by API interface
        int topic_index = rand.nextInt(10);

        RecommendationEmailTemplate template = new RecommendationEmailTemplate();
        template.setTopic(searchTerm);
        template.setName(searchResults.get(topic_index).getNewsTitle());
        template.setDescription(searchResults.get(topic_index).getDesc());
        template.setUrl(searchResults.get(topic_index).getNewsUrl());

        return template;
    }
}
