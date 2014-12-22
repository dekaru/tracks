package com.frozyog.tracks;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TrackProcessingTest {

    @Test
    public void testIdentifySegmentInTrack() throws Exception {

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
        ArrayList<Pair<Double, Double>> segment1 = new ArrayList<Pair<Double, Double>>(Arrays.asList(
                new Pair<Double, Double>(1.0, 2.0),
                new Pair<Double, Double>(1.5, 2.3),
                new Pair<Double, Double>(2.0, 1.8)
        ));
        Assert.assertTrue(TrackProcessing.identifySegmentInTrack(track, segment1));

        ArrayList<Pair<Double, Double>> segment2 = new ArrayList<Pair<Double, Double>>(Arrays.asList(
                new Pair<Double, Double>(1.0, 2.0),
                new Pair<Double, Double>(1.5, 2.3),
                new Pair<Double, Double>(2.0, 1.1)
        ));
        Assert.assertFalse(TrackProcessing.identifySegmentInTrack(track, segment2));
    }

    @Test
    public void testIdentifyClimbs() throws Exception {
        float[] elevations = {-3, -2, 0, 1, 2, 3, 1, 1, 4, 5, 4, 8};
        ArrayList<Pair<Integer, Integer>> climbs = TrackProcessing.identifyClimbs(elevations);
        float[][] climbsArr = {
                {climbs.get(0).getKey(), climbs.get(0).getValue()},
                {climbs.get(1).getKey(), climbs.get(1).getValue()},
                {climbs.get(2).getKey(), climbs.get(2).getValue()},
            };
        Assert.assertArrayEquals(climbsArr, new float[][]{{0, 5}, {7, 9}, {10,11}});
    }
}