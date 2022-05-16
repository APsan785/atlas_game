package com.apsan.atlasgame.repo;

import android.content.Context;

import com.apsan.atlasgame.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountriesRepo {
    public List<String> CountriesList;

    public CountriesRepo(Context context) throws IOException {
        CountriesList = getAllCountriesList(context);
    }

    public List<String> getCountriesWithLetter(Character starting)  {

        List<String> newCountryList = new ArrayList();
        for (String i : CountriesList) {
            if (i.charAt(0) == Character.toUpperCase(starting))
                newCountryList.add(i);
        }

        return newCountryList;
    }

    public static List<String> getAllCountriesList(Context context) throws IOException {
        String line = "";
        List<List<String>> csvArray = new ArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                context.getResources().openRawResource(R.raw.countries_of_world)));
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            csvArray.add(Arrays.asList(values));
        }
        List<String> countriesList = new ArrayList();
        for (List<String> i : csvArray) {
            String country = i.get(0).substring(0,1).toUpperCase() + i.get(0).substring(1);
            country = country.substring(0, country.length() - 1);
            countriesList.add(country);
        }
        return countriesList;
    }
}
