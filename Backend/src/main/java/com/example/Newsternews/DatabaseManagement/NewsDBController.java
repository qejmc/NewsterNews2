package com.example.Newsternews.DatabaseManagement;

import com.example.Newsternews.API.APIHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.LinkedList;

@RestController
public class NewsDBController {
    @Autowired
    NewsRepository newsRepository;

    /*
    Uses 3 separate cron jobs since the bing news API is limited to 3 calls per second on the free tier
    For now, scheduled to run 10 seconds after another but can be configured.
    Better than sleeping anyways...
     */

    @Scheduled(cron = "0 0 7,11,17 * * ?")
    //@Scheduled(cron = "0 49 19 * * ?")
    public void saveNewsArticles() throws IOException {
        // Delete the old news
        newsRepository.deleteAll();

        LinkedList<News> result1 = APIHandler.SearchNews(Topic.SEARCHTERM1);
        for (int i = 0; i < result1.size(); i++) {
            newsRepository.save(result1.get(i));
        }

        result1 = APIHandler.SearchNews(Topic.SEARCHTERM2);
        for (int i = 0; i < result1.size(); i++) {
            newsRepository.save(result1.get(i));
        }

        result1 = APIHandler.SearchNews(Topic.SEARCHTERM3);
        for (int i = 0; i < result1.size(); i++) {
            newsRepository.save(result1.get(i));
        }
    }

    @Scheduled(cron = "10 0 7,11,17 * * ?")
    //@Scheduled(cron = "10 49 19 * * ?")
    public void saveNewsArticles2() throws IOException {
        LinkedList<News> result1 = APIHandler.SearchNews(Topic.SEARCHTERM4);
        for (int i = 0; i < result1.size(); i++) {
            newsRepository.save(result1.get(i));
        }

        result1 = APIHandler.SearchNews(Topic.SEARCHTERM5);
        for (int i = 0; i < result1.size(); i++) {
            newsRepository.save(result1.get(i));
        }

        result1 = APIHandler.SearchNews(Topic.SEARCHTERM6);
        for (int i = 0; i < result1.size(); i++) {
            newsRepository.save(result1.get(i));
        }
    }

    @Scheduled(cron = "20 0 7,11,17 * * ?")
    //@Scheduled(cron = "20 49 19 * * ?")
    public void saveNewsArticles3() throws IOException {
        LinkedList<News> result1 = APIHandler.SearchNews(Topic.SEARCHTERM7);
        for (int i = 0; i < result1.size(); i++) {
            newsRepository.save(result1.get(i));
        }

        result1 = APIHandler.SearchNews(Topic.SEARCHTERM8);
        for (int i = 0; i < result1.size(); i++) {
            newsRepository.save(result1.get(i));
        }

        result1 = APIHandler.SearchNews(Topic.SEARCHTERM9);
        for (int i = 0; i < result1.size(); i++) {
            newsRepository.save(result1.get(i));
        }
    }

}
