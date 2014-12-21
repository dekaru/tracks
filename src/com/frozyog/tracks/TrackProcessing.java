package com.frozyog.tracks;

import javafx.util.Pair;

import java.util.ArrayList;


public class TrackProcessing {

    public static void main(String[] args) {

        // identify elevations
        float[] elevations = {-3, -2, 0, 1, 2, 3, 1, 1, 4, 5, 4, 8};
        ArrayList<Pair<Integer, Integer>> climbs = identifyClimbs(elevations);
        for (int i = 0; i < climbs.size(); i++) {
            System.out.println("[" + climbs.get(i).getKey() + ", " + climbs.get(i).getValue() + "]");
        }

        // identify segments

    }


    /**
     * Find all climbs (consecutive ascending elevations) on a given array of elevations profile.
     *
     * Example input:  {-3, -2, 0, 1, 2, 3, 1, 1, 4, 5, 4, 8}
     * Example output: {[2, 6], [8, 9]}
     *
     * @param elevations an array of floats which represents different elevations
     * @return An ArrayList of Pair<Integer, Integer> with the starting and ending indexes of all climbs included
     * in the provided elevation profile.
     */
    public static ArrayList<Pair<Integer, Integer>> identifyClimbs(float[] elevations) {

        ArrayList<Pair<Integer, Integer>> climbs = new ArrayList<Pair<Integer, Integer>>();

        int startPoint = -1;

        // iterate from 0 to n-1
        for (int i = 0; i < elevations.length; i++) {
            // we've reached the end of the elevations profile
            if (i+1 == elevations.length) {
                // if we were on a climb...
                if (startPoint != -1) {
                    // ... add the climb to the list
                    climbs.add(new Pair<Integer, Integer>(startPoint, i));
                }
            // we haven't reached the end of the elevations profile
            } else {
                // keep detecting climbs
                if (elevations[i + 1] > elevations[i]) {
                    // if we weren't already on a climb, we should initialize it
                    if (startPoint == -1) {
                        startPoint = i;
                    }

                }

                // no climb is detected
                if (elevations[i + 1] <= elevations[i]) {
                    // a starting point different to -1 means we were on a climb
                    if (startPoint != -1) {
                        // add the climb to the list
                        climbs.add(new Pair<Integer, Integer>(startPoint, i));

                        // reset our startPoint
                        startPoint = -1;
                    }
                }
            }
        }

        return climbs;
    }
}
