package com.exercise.project.fixtures;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.github.javafaker.Faker;
import com.github.slugify.Slugify;

public abstract class BaseFixtures<T> {

    private Faker faker;

    public BaseFixtures() {
        faker = Faker.instance(Locale.ENGLISH);
    }

    public Faker faker() {
        return faker;
    }

    public List<T> createMany(Integer number) {
        List<T> datas = new ArrayList<T>();
        for (int i = 0; i < number; i++) {
            datas.add(create());
        }

        return datas;
    }

    protected String slugify(String text) {
        return Slugify
            .builder()
            .lowerCase(true)
            .build()
            .slugify(text) + "-" + (new Random()).nextInt();
    }

    public abstract void store(T data);

    public abstract T create();

}
