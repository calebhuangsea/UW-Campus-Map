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

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.ShortestPath;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.Set;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        CampusMap campusMap = new CampusMap();
        Set<String> buildings = campusMap.buildingNames().keySet();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        //findPath end point, return shortest path between two buildings
        //Request Params: start : start building
        //                  end : end building
        //return: shortest path if two buildings exist
        //return: null if missing start or end parameters
        //return:
        Spark.get("findPath", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String start = request.queryParams("start");
                String end = request.queryParams("end");
                // if one of parameters is missed
                if (start == null || end == null) {
                    Spark.halt(400, "Missing Parameters!");
                }
                Gson gson = new Gson();
                Path<Point> shortestPath = null;
                try {
                    shortestPath = campusMap.findShortestPath(start, end);
                } catch (Exception e) {
                    Spark.halt(400, "Building not Found!");
                }
                String json = gson.toJson(shortestPath);
                return json;
            }
        });

        Spark.get("allBuildings", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Gson gson = new Gson();
                String json = gson.toJson(buildings);
                return json;
            }
        });

        Spark.get("shortestBuilding", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Gson gson = new Gson();
                String start = request.queryParams("start");
                Path shortestPath = null;
                double cost = Double.MAX_VALUE;
                for (String building : buildings) {
                    if (!start.equals(building)) {
                        Path newPath = campusMap.findShortestPath(start, building);
                        if (cost > newPath.getCost()) {
                            shortestPath = newPath;
                            cost = newPath.getCost();
                        }
                    }
                }
                return gson.toJson(shortestPath);
            }
        });
    }

}
