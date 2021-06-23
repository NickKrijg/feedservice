package com.kwetter.feed.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.kwetter.feed.controllers.FeedController;
import com.kwetter.feed.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
@EnableCaching
public class FeedService {

    final Gson builder = new GsonBuilder()
            .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (jsonElement, type, context) -> new Date(jsonElement.getAsJsonPrimitive().getAsLong()))
            .create();

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Cacheable("feedCache")
    public Iterable<Post> getFeed() {
        System.out.println("Not used the cache");

        String feedString = redisTemplate.opsForValue().get("feedCache");

        return Arrays.asList(builder.fromJson(feedString, Post[].class));
    }

    public boolean setFeed(List<Post> posts) {
        try{
            redisTemplate.opsForValue().set("feedCache", builder.toJson(posts));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @CacheEvict(value = "feedCache", allEntries = true)
    public void deleteAllByUsername(String username) {
        String feedString = redisTemplate.opsForValue().get("feedCache");
        var gson = new Gson();

        var postList = new LinkedList<>(Arrays.asList(gson.fromJson(feedString, Post[].class)));

        postList.removeIf(post -> post.getUsername().equals(username));

        redisTemplate.delete("feedCache");
        redisTemplate.opsForValue().set("feedCache", gson.toJson(postList));
    }
}
