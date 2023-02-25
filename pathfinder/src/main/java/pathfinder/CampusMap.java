/*
 * Copyright (C) 2022 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

/**
 * A campus map is a model, where it accepts request from controller
 * and do some computation according to our campus_paths.csv and campus_buildings.csv files and
 * return data.
 * A campus map allows user to find the shortest path between two buildings
 * Also allows user to get information for short names and long names of buildings
 * A campus map stores campus buildings and campus path information in a graph,
 * where each building represent a node, each path between two buildings represent
 * an edge. A building has a short name and long name.
 */
public class CampusMap implements ModelAPI {
    private Graph<Point, Double> graph;
    private Map<String, CampusBuilding> campusBuildings;
    private List<CampusPath> campusPaths;
    private final boolean DEBUG = false;

    // AF:
    // A CampusMap represents a campus map with unique buildings and unique paths between buildings
    // A campusMap stores campus Paths with an ArrayList, where every Path has
    //      its start coordinate and end coordinate and distance
    // A campusMap stores campus buildings with a HashMap, where key = shortName, value = building
    //      where every building has its coordinate and shortname and longname
    // A campusMap is a graph that uses Point to represent buildings' location and double to represent
    //      distances between two buildings. Evert two buildings(Points) connect to each other
    //      by paths(double)
    // A campusMap is empty if there is no building in it.

    // RI
    // graph != null
    // campusBuildings != null
    // campusPaths != null
    // Every key and entry in campusBuildings != null
    // Every entry in campusPath != null
    // Every coordinate in buildings and paths is not negative
    // Every building cannot have duplicate outgoing paths
    // Every building is unique

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        assert (graph != null) : "graph is null";
        assert (campusBuildings != null) : "campusBuildings is null";
        assert (campusPaths != null) : "campusPaths is null";
        if (DEBUG) {
            // CampusPath entry
            for (CampusPath campusPath : campusPaths) {
                assert (campusPath != null) : "entry in campusPath is null";
                assert (campusPath.getX1() >= 0) : "x1 in campus path is negative";
                assert (campusPath.getX2() >= 0) : "x2 in campus path is negative";
                assert (campusPath.getY1() >= 0) : "y1 in campus path is negative";
                assert (campusPath.getY2() >= 0) : "y2 in campus path is negative";
            }

            Set<String> names = campusBuildings.keySet();
            for (String name : names) {
                assert (name != null) : "keys in campusBuildings is null";
                CampusBuilding campusBuilding = campusBuildings.get(name);
                assert (campusBuilding != null) : "value in campusBuildings is null";
                assert (campusBuilding.getX() >= 0) : "x in campus building is negative";
                assert (campusBuilding.getY() >= 0) : "y in campus building is negative";
            }
        }
    }


    /**
     * construct a campus map
     * Store information we parsed from campus_paths.csv and campus_buildings.csv to a graph
     * and also store campus buildings and campus paths individually in our campus map.
     */
    public CampusMap() {
        graph = new Graph<Point, Double>();
        campusPaths = CampusPathsParser.parseCampusPaths("campus_paths.csv");
        campusBuildings = new HashMap<>();
        List<CampusBuilding> buildings = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        for (CampusBuilding building : buildings) {
            campusBuildings.put(building.getShortName(), building);
        }
        for (CampusPath campusPath : campusPaths) {
            graph.addEdge(new Point(campusPath.getX1(), campusPath.getY1()),
                    new Point(campusPath.getX2(), campusPath.getY2()), campusPath.getDistance());
        }
        checkRep();
    }

    @Override
    public boolean shortNameExists(String shortName) {
        checkRep();
        Set<String> strings = campusBuildings.keySet();
        for (String string : strings) {
            if (campusBuildings.get(string).getShortName().equals(shortName)) {
                return true;
            }
        }
        checkRep();
        return false;
    }

    @Override
    public String longNameForShort(String shortName) {
        checkRep();
        if (!shortNameExists(shortName)) {
            throw new IllegalArgumentException("Short Name doesn't exist");
        }
        checkRep();
        return buildingNames().get(shortName);
    }

    @Override
    public Map<String, String> buildingNames() {
        checkRep();
        Map<String, String> map =  new HashMap<>();
        Set<String> strings = campusBuildings.keySet();
        for (String string : strings) {
            map.put(string, campusBuildings.get(string).getLongName());
        }
        checkRep();
        return map;
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        checkRep();
        if (startShortName == null || endShortName == null || !shortNameExists(startShortName) || !shortNameExists(endShortName)) {
            throw new IllegalArgumentException("Given building short name is null or not exist");
        }
        CampusBuilding start = campusBuildings.get(startShortName);
        CampusBuilding end = campusBuildings.get(endShortName);
        checkRep();
        return ShortestPath.findShortestPath(graph, new Point(start.getX(), start.getY()), new Point(end.getX(), end.getY()));
    }
}
