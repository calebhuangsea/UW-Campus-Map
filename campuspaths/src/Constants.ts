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

// Latitude of the UW Seattle campus
import {LatLngExpression} from "leaflet";

export const UW_LATITUDE : number = 47.65878405511131;

// Offset to translate coordinate system
export const UW_LATITUDE_OFFSET : number = 807.35188;

// Scale to translate coordinate system
export const UW_LATITUDE_SCALE : number = -0.00000576766;

// Longitude of the UW Seattle campus
export const UW_LONGITUDE : number = -122.31305164734569;

// Offset to translate coordinate system
export const UW_LONGITUDE_OFFSET : number = 1370.6408;

// Scale to translate coordinate system
export const UW_LONGITUDE_SCALE : number = 0.00000848028;

// Map center
export const UW_LATITUDE_CENTER = 47.65440627742146;

// Map center
export const UW_LONGITUDE_CENTER = -122.30476957834502;

export const BUILDINGS : Map<string, number[]> = {
// @ts-ignore
    "BAG" : {value : [1914.5103,1709.8816]},
    "BAG (NE)" : {value : [1878.3786,1661.4083]},
    "BGR" : {value : [1671.5499,1258.4333]},
    "CSE" : {value : [2259.7112,1715.5273]},
    "CS2" : {value : [2315.0936,1780.7913]},
    "DEN" : {value : [1890.0,892.57144]},
    "EEB" : {value : [2159.9587,1694.8192]},
    "EEB (S)" : {value : [2135.2099,1741.5387]},
    "GWN" : {value : [2022.3254,1210.9561]},
    "KNE" : {value : [1876.6109,1165.2467]},
    "KNE (E)" : {value : [1874.338,1212.4713]},
    "KNE (SE)" : {value : [1835.1946,1241.0081]},
    "KNE (S)" : {value : [1812.7187,1230.149]},
    "KNE (SW)" : {value : [1787.2124,1218.5323]},
    "LOW" : {value : [2375.6262,1576.1262]},
    "MGH" : {value : [1973.1382,1433.6676]},
    "MGH (E)" : {value : [2043.1036,1514.917]},
    "MGH (S)" : {value : [2057.1756,1564.762]},
    "MGH (SW)" : {value : [1971.6531,1551.6673]},
    "MLR" : {value : [2184.7074,1045.0386]},
    "MOR" : {value : [2317.1749,1859.502]},
    "MUS" : {value : [2202.5882,957.31147]},
    "MUS (E)" : {value : [2261.6656,1011.4323]},
    "MUS (SW)" : {value : [2243.4795,1040.5275]},
    "MUS (S)" : {value : [2255.3257,1048.2583]},
    "OUG" : {value : [1724.1276,1208.4754]},
    "PAA" : {value : [1625.2679,1783.5181]},
    "PAB" : {value : [1560.6467,1698.3767]},
    "SAV" : {value : [1951.8672,1094.7886]},
    "SUZ" : {value : [1895.8038,1325.861]},
    "T65" : {value : [1370.6408,807.35188]},
    "FSH" : {value : [1061.8213,1779.6903]},
    "MCC" : {value : [2345.7143,528.64286]},
    "MCC (S)" : {value : [2437.4981,610.1679]},
    "UBS" : {value : [1373.6078,556.55779]},
    "UBS (Secret)" : {value : [1440.1364,520.85309]},
    "RAI" : {value : [2024.5103,993.01223]},
    "RAI (E)" : {value : [2094.2987,956.90276]},
    "ROB" : {value : [2309.4107,1979.0003]},
    "CHL" : {value : [1707.6629,1671.5098]},
    "CHL (NE)" : {value : [1746.0487,1672.52]},
    "CHL (SE)" : {value : [1762.7162,1708.8855]},
    "IMA" : {value : [2722.3352,1710.2859]},
    "HUB" : {value : [2269.7856,1364.3777]},
    "HUB (West Food)" : {value : [2270.8682,1388.1961]},
    "HUB (South Food)" : {value : [2309.3025,1450.4487]},
    "MNY" : {value : [1684.1768,1297.0716]},
    "MNY (NW)" : {value : [1658.923,1295.8089]},
    "PAR" : {value : [1715.3571,1060.4286]},
    "MCM" : {value : [2446.9314,898.06137]},
    "MCM (SW)" : {value : [2419.3153,921.79995]},
    "CMU" : {value : [2344.8512,1114.6251]},
}
