package com.apsan.atlasgame.repo;

import android.content.Context;

import com.apsan.atlasgame.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorldCities {

    public List<String> WorldCitiesList;

    public WorldCities(Context context) throws IOException {
        WorldCitiesList = getAllCitiesList(context);
    }

    public List<String> getWorldCitiesWithLetter(Character starting)  {

        List<String> newCountryList = new ArrayList();
        for (String i : WorldCitiesList) {
            if (i.charAt(0) == Character.toUpperCase(starting))
                newCountryList.add(i);
        }

        return newCountryList;
    }

    public static List<String> getAllCitiesList(Context context) throws IOException {
        String line = "";
        List<List<String>> csvArray = new ArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                context.getResources().openRawResource(R.raw.worldcities_clean)));
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            csvArray.add(Arrays.asList(values));
        }
        List<String> citiesList = new ArrayList();
        for (List<String> i : csvArray) {
            String city = i.get(0).substring(0,1).toUpperCase() + i.get(0).substring(1);
            citiesList.add(city);
        }
        return citiesList;
    }
}
