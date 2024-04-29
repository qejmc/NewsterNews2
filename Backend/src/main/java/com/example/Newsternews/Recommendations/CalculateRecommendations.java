package com.example.Newsternews.Recommendations;

import com.example.Newsternews.DatabaseManagement.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class CalculateRecommendations {

    @Autowired
    UserRepository userRepository;

    public String getRecommendedTopics(String currentTopics)
    {
        StringBuilder recommendedTopics = new StringBuilder();

        ArrayList<Integer> topicIntList = new ArrayList<>();

        String[] currentTopicsList = currentTopics.split(" ");

        // Parses the space-delineated topic list into list of integer topics numbers
        for(int i = 0; i < currentTopicsList.length; i++)
        {
            topicIntList.add(Integer.parseInt(currentTopicsList[i]));
        }

        // Recommendation logic - for each topic the user is subscribed to, add it to recommendations
        // Only add the recommendation if they are not already subscribed to it
        for(int i = 0; i < topicIntList.size(); i++)
        {
            switch (topicIntList.get(i))
            {
                case 1:
                    if(!topicIntList.contains(1))
                        //TODO: Change these to suitable recommendations
                        recommendedTopics.append("2");
                    break;

                case 2:
                    if(!topicIntList.contains(2))
                        recommendedTopics.append("3");
                    break;


            }

            recommendedTopics.append(" ");
        }

        return recommendedTopics.toString();

    }
}
