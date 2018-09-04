package com.example.ml;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class RecomandationSystem{

    public static List<Long> getRecommendationsForUser(int userId)
            throws IOException, TasteException {
        List<Long> recommendedBooks = new LinkedList<>();

        // Load historical data about user preferences
        DataModel model = new FileDataModel(new File("user_book.csv"));

        // Compute the similarity between users, according to their preferences
        UserSimilarity similarity = new EuclideanDistanceSimilarity(model);

        // Group the users with similar preferences
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1,
                similarity, model);

        // Create a recommender
        UserBasedRecommender recommender = new GenericUserBasedRecommender(
                model, neighborhood, similarity);
        List<RecommendedItem> recommendations = recommender.recommend(userId, 5);

        for (RecommendedItem recommendation : recommendations) {
            if(recommendation.getValue() > 7.5)
            recommendedBooks.add(recommendation.getItemID());
            System.out.println("User might like the book with ID: "
                    + recommendation.getItemID() + " (predicted preference :"
                    + recommendation.getValue() + ")");
        }
        return recommendedBooks;
    }
}