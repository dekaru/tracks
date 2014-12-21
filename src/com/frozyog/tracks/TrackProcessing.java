package com.frozyog.tracks;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;


public class TrackProcessing {

    public static void main(String[] args) {

        // identify elevations
        float[] elevations = {-3, -2, 0, 1, 2, 3, 1, 1, 4, 5, 4, 8};
        ArrayList<Pair<Integer, Integer>> climbs = identifyClimbs(elevations);
        for (int i = 0; i < climbs.size(); i++) {
            System.out.println("[" + climbs.get(i).getKey() + ", " + climbs.get(i).getValue() + "]");
        }

        // identify segments test 1
        ArrayList<Pair<Double, Double>> segment = new ArrayList<Pair<Double, Double>>(Arrays.asList(
                new Pair<Double, Double>(1.0, 2.0),
                new Pair<Double, Double>(1.5, 2.3),
                new Pair<Double, Double>(2.0, 1.8)
            ));
        ArrayList<Pair<Double, Double>> track   = new ArrayList<Pair<Double, Double>>(Arrays.asList(
                new Pair<Double, Double>(3.7, 5.2),
                new Pair<Double, Double>(1.0, 2.0),
                new Pair<Double, Double>(1.5, 2.3),
                new Pair<Double, Double>(8.0, 7.8),
                new Pair<Double, Double>(8.2, 8.8),
                new Pair<Double, Double>(9.1, 4.1),
                new Pair<Double, Double>(1.0, 2.0),
                new Pair<Double, Double>(1.5, 2.3),
                new Pair<Double, Double>(2.0, 1.8),
                new Pair<Double, Double>(5.5, 9.3)
            ));
        System.out.println(identifySegmentInTrack(track, segment));
    }



    /**
     * Determine if a segment is considered inside a track. A segment is an ordered subset of points in a track.
     * Identified segment points must all be included in order in the track for this method to return true.
     *
     * Example
     *  input:
     *      segment: (1.0, 2.0), (1.5, 2.3), (2.0, 1.8)
     *      track:   (3.7, 5.2), (1.0, 2.0), (1.5, 2.3), (8.0, 7.8), (8.2, 8.8), (9.1, 4.1), (1.0, 2.0), (1.5, 2.3), (2.0, 1.8), (5.5, 9.3)
     *  output:
     *      true
     *
     * @param track   an ArrayList of Pair<Double, Double> which represent gps points that form a track (latitude and longitude)
     * @param segment an ArrayList of Pair<Double, Double> which represent gps points that form a segment (latitude and longitude)
     * @return A boolean value determining if the provided segment is included in the provided track.
     */
    public static boolean identifySegmentInTrack(ArrayList<Pair<Double, Double>> track, ArrayList<Pair<Double, Double>> segment) {
        System.out.println("\nIdentifying segments ----> ");

        int segmentIterator = -1;

        // traverse our track
        for (int i = 0; i < track.size(); i++) {
            // get our current track's trackpoint
            Pair<Double, Double> trackpoint = track.get(i);
            System.out.print("trackpoint: " + trackpoint.getKey() + ", " + trackpoint.getValue());

            // compare to our segment
            if (segmentIterator != -1) {
                // we were traversing a matching segment... are we still doing that?
                if (trackpoint.equals(segment.get(segmentIterator))) {
                    // has the segment finished?
                    if ((segmentIterator + 1) == segment.size()) {
                        // ... yes it has
                        System.out.println("... segment has been matched!!!");
                        return true;
                    }

                    // update segment iterator
                    segmentIterator++;
                } else {
                    // show's over, guys, reset our segment iterator
                    System.out.print("... segment has been cut off");
                    segmentIterator = -1;
                }
            } else {
                // maybe this time we'll get lucky
                if (trackpoint.equals(segment.get(0))) {
                    // print out a result
                    System.out.print("... segment started");

                    // we have started matching our segment... initialize segment iterator for the next cycle
                    segmentIterator = 1;
                }
            }

            // insert new line
            System.out.println();
        }

        return false;
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
        System.out.println("\nIdentifying elevations ----> ");

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
