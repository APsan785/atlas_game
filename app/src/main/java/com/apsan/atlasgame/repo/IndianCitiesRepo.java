package com.apsan.atlasgame.repo;

import android.content.Context;

import com.apsan.atlasgame.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IndianCitiesRepo {

    public List<String> citiesList;

    public IndianCitiesRepo(Context context) throws IOException {
        citiesList = getAllCitiesList(context);
    }

    public List<String> getIndianCitiesWithLetter(Character starting)  {

        List<String> newCityList = new ArrayList();
        for (String i : citiesList) {
            if (i.charAt(0) == Character.toUpperCase(starting))
                newCityList.add(i);
        }

        return newCityList;
    }

    public static List<String> getAllCitiesList(Context context) throws IOException {
        String line = "";
        List<List<String>> csvArray = new ArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                context.getResources().openRawResource(R.raw.indian_cities_states)));
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            csvArray.add(Arrays.asList(values));
        }
        List<String> citiesList = new ArrayList();
        for (List<String> i : csvArray) {
            citiesList.add(i.get(0).substring(0,1).toUpperCase() + i.get(0).substring(1));
        }
        return citiesList;
    }
}
