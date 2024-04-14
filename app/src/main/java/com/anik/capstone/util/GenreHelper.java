package com.anik.capstone.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class GenreHelper {
   private static final HashSet<String> genres = new HashSet<>(Arrays.asList(
            "fiction",
            "non-fiction",
            "mystery",
            "thriller",
            "romance",
            "fantasy",
            "science fiction",
            "horror",
            "historical fiction",
            "biography",
            "autobiography",
            "memoir",
            "poetry",
            "self-help",
            "business",
            "travel",
            "children's literature",
            "young adult",
            "crime",
            "adventure",
            "humor",
            "drama",
            "satire",
            "biography",
            "philosophy",
            "religion",
            "art",
            "cooking",
            "science",
            "health"
    ));

   public static List<String> getGenres(List<String> subject){
       List<String> result = new ArrayList<>();
       int length = 10;
       if(subject.size() < 10) length = subject.size();
       for(int  i = 0; i < length; i++){
           if(genres.contains(subject.get(i).toLowerCase())){
               result.add(subject.get(i));
           }
       }
       return result;
   }
}
