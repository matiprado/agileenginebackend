package com.agileengine.repository;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public void test() {
        List<Integer> input = new ArrayList<>();
        List<Integer> output = new ArrayList<>();
        int sum = 1;
        int zerosCount = 0;
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).equals(0)) {
                if (zerosCount == 0) {
                }
                zerosCount++;
            } else {
                sum *= input.get(i);
            }
        }

        for (int i = 0; i < input.size(); i++) {
            if (zerosCount > 1) {
                output.add(0);
            } else {
                int value = input.get(i);
                if (value == 0) {
                    value = 1;
                }
                int result = sum / value;
                output.add(result);
            }
        }
    }
}
