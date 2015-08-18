package com.bnana.martianrun.utils;

import com.bnana.martianrun.enums.EnemyType;

import java.util.Random;

/**
 * Created by Luca on 8/18/2015.
 */
public class RandomUtils {
    static RandomEnum<EnemyType> randomEnum = new RandomEnum<EnemyType>(EnemyType.class);

    public static EnemyType getRandomEnemyType(){
        return randomEnum.random();
    }

    private static class RandomEnum<E extends Enum> {
        private static final Random rnd = new Random();
        private final E[] values;

        public RandomEnum(Class<E> token) {
            values = token.getEnumConstants();
        }

        public E random() {
            return values[rnd.nextInt(values.length)];
        }
    }
}
