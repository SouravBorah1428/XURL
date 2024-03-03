package com.crio.shorturl;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class XUrlImpl implements XUrl {

    public HashMap<String, String> longToShort = new HashMap<>();
    public HashMap<String, String> shortToLong = new HashMap<>();
    public HashMap<String, Integer> hitCount = new HashMap<>();

    @Override
    public String registerNewUrl(String longUrl) {
        // TODO Auto-generated method stub
        UUID uuid = UUID.randomUUID();

        String shortUrl = "http://short.url/" + uuid.toString().replace("-", "").substring(0, 9);

        if (longToShort.containsKey(longUrl)) {
            return longToShort.get(longUrl);
        }

        longToShort.put(longUrl, shortUrl);
        shortToLong.put(shortUrl, longUrl);
        hitCount.put(longUrl, 0);

        return shortUrl;
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        // TODO Auto-generated method stub
        if (shortToLong.containsKey(shortUrl)) {
            return null;
        } else {
            longToShort.put(longUrl, shortUrl);
            shortToLong.put(shortUrl, longUrl);
            hitCount.put(longUrl, 0);

            return longToShort.get(longUrl);
        }
    }

    @Override
    public String getUrl(String shortUrl) {
        String longUrl = shortToLong.get(shortUrl);

        // Check if the shortUrl exists in the mapping
        if (longUrl != null) {
            // Increment hit count only if the longUrl is found
            hitCount.put(longUrl, hitCount.getOrDefault(longUrl, 0) + 1);
        }

        return longUrl;
    }


    @Override
    public Integer getHitCount(String longUrl) {
        // TODO Auto-generated method stub
        return hitCount.getOrDefault(longUrl, 0);
    }

    @Override
    public String delete(String longUrl) {
        String shortUrl = longToShort.remove(longUrl);
        if (shortUrl != null) {
            // Remove the corresponding short URL from shortToLong
            shortToLong.remove(shortUrl);
            // Remove the hit count entry
            hitCount.remove(longUrl);
        }
        return shortUrl;
    }
    

}
