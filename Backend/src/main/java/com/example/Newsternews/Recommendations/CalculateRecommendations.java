package com.example.Newsternews.Recommendations;

import com.example.Newsternews.DatabaseManagement.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;

public class CalculateRecommendations {

    public static String getRecommendedTopics(String currentTopics)
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
                    if(!topicIntList.contains(5)) {
                        topicIntList.add(5);
                        recommendedTopics.append("5 ");
                    }
                    break;

                case 2:
                    if(!topicIntList.contains(3)) {
                        topicIntList.add(3);
                        recommendedTopics.append("3 ");
                    }
                    break;

                case 3:
                    if(!topicIntList.contains(2)) {
                        topicIntList.add(2);
                        recommendedTopics.append("2 ");
                    }
                    break;

                case 4, 9:
                    if(!topicIntList.contains(8)) {
                        topicIntList.add(8);
                        recommendedTopics.append("8 ");
                    }
                    break;

                case 5, 8:
                    if(!topicIntList.contains(9)) {
                        topicIntList.add(9);
                        recommendedTopics.append("9 ");
                    }
                    break;

                case 6:
                    if(!topicIntList.contains(7)) {
                        topicIntList.add(7);
                        recommendedTopics.append("7 ");
                    }
                    break;

                case 7:
                    if(!topicIntList.contains(6)) {
                        topicIntList.add(6);
                        recommendedTopics.append("6 ");
                    }
                    break;

                default:
                    break;
            }
        }

        // If there were no recommendations that could be made, default to using business and politics
        if(recommendedTopics.isEmpty())
        {
            recommendedTopics.append("1 4");
        }

        return recommendedTopics.toString();

    }
}
