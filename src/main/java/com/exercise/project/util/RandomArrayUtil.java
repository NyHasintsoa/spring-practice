package com.exercise.project.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomArrayUtil {

    public static <L> Collection<L> getRandomUniqueElements(List<L> datas, int count) {
        if (datas == null || datas.size() == 0 || count <= 0) {
            return new ArrayList<L>();
        }
        if (count >= datas.size()) {
            Collections.shuffle(datas);
            return datas;
        }
        List<L> shuffled = datas;
        Random random = new Random();
        for (int i = shuffled.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            L temp = shuffled.get(i);
            shuffled.set(i, shuffled.get(j));
            shuffled.set(j, temp);
        }

        return shuffled.subList(0, count);
    }

    public static <T> T getRandomElement(List<T> datas) {
        if (datas == null || datas.size() == 0) {
            return null;
        }
        Random random = new Random();
        return datas.get(random.nextInt(datas.size()));
    }

}
