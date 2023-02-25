package pathfinder.parser;

import java.util.ArrayList;

public class parse {
    public static void main(String[] args) {
        String files = "BAG,Bagley Hall (East Entrance),1914.5103,1709.8816\n" +
                "BAG (NE),Bagley Hall (Northeast Entrance),1878.3786,1661.4083\n" +
                "BGR,By George,1671.5499,1258.4333\n" +
                "CSE,Paul G. Allen Center for Computer Science & Engineering,2259.7112,1715.5273\n" +
                "CS2,Bill & Melinda Gates Center For Computer Science & Engineering,2315.0936,1780.7913\n" +
                "DEN,Denny Hall,1890.0,892.57144\n" +
                "EEB,Electrical Engineering Building (North Entrance),2159.9587,1694.8192\n" +
                "EEB (S),Electrical Engineering Building (South Entrance),2135.2099,1741.5387\n" +
                "GWN,Gowen Hall,2022.3254,1210.9561\n" +
                "KNE,Kane Hall (North Entrance),1876.6109,1165.2467\n" +
                "KNE (E),Kane Hall (East Entrance),1874.338,1212.4713\n" +
                "KNE (SE),Kane Hall (Southeast Entrance),1835.1946,1241.0081\n" +
                "KNE (S),Kane Hall (South Entrance),1812.7187,1230.149\n" +
                "KNE (SW),Kane Hall (Southwest Entrance),1787.2124,1218.5323\n" +
                "LOW,Loew Hall,2375.6262,1576.1262\n" +
                "MGH,Mary Gates Hall (North Entrance),1973.1382,1433.6676\n" +
                "MGH (E),Mary Gates Hall (East Entrance),2043.1036,1514.917\n" +
                "MGH (S),Mary Gates Hall (South Entrance),2057.1756,1564.762\n" +
                "MGH (SW),Mary Gates Hall (Southwest Entrance),1971.6531,1551.6673\n" +
                "MLR,Miller Hall,2184.7074,1045.0386\n" +
                "MOR,Moore Hall,2317.1749,1859.502\n" +
                "MUS,Music Building (Northwest Entrance),2202.5882,957.31147\n" +
                "MUS (E),Music Building (East Entrance),2261.6656,1011.4323\n" +
                "MUS (SW),Music Building (Southwest Entrance),2243.4795,1040.5275\n" +
                "MUS (S),Music Building (South Entrance),2255.3257,1048.2583\n" +
                "OUG,Odegaard Undergraduate Library,1724.1276,1208.4754\n" +
                "PAA,Physics/Astronomy Building A,1625.2679,1783.5181\n" +
                "PAB,Physics/Astronomy Building,1560.6467,1698.3767\n" +
                "SAV,Savery Hall,1951.8672,1094.7886\n" +
                "SUZ,Suzzallo Library,1895.8038,1325.861\n" +
                "T65,Thai 65,1370.6408,807.35188\n" +
                "FSH,Fishery Sciences Building,1061.8213,1779.6903\n" +
                "MCC,McCarty Hall (Main Entrance),2345.7143,528.64286\n" +
                "MCC (S),McCarty Hall (South Entrance),2437.4981,610.1679\n" +
                "UBS,University Bookstore,1373.6078,556.55779\n" +
                "UBS (Secret),University Bookstore (Secret Entrance),1440.1364,520.85309\n" +
                "RAI,Raitt Hall (West Entrance),2024.5103,993.01223\n" +
                "RAI (E),Raitt Hall (East Entrance),2094.2987,956.90276\n" +
                "ROB,Roberts Hall,2309.4107,1979.0003\n" +
                "CHL,Chemistry Library (West Entrance),1707.6629,1671.5098\n" +
                "CHL (NE),Chemistry Library (Northeast Entrance),1746.0487,1672.52\n" +
                "CHL (SE),Chemistry Library (Southeast Entrance),1762.7162,1708.8855\n" +
                "IMA,Intramural Activities Building,2722.3352,1710.2859\n" +
                "HUB,Student Union Building (Main Entrance),2269.7856,1364.3777\n" +
                "HUB (West Food),Student Union Building (West Food Entrance),2270.8682,1388.1961\n" +
                "HUB (South Food),Student Union Building (South Food Entrance),2309.3025,1450.4487\n" +
                "MNY,Meany Hall (Northeast Entrance),1684.1768,1297.0716\n" +
                "MNY (NW),Meany Hall (Northwest Entrance),1658.923,1295.8089\n" +
                "PAR,Parrington Hall,1715.3571,1060.4286\n" +
                "MCM,McMahon Hall (Northwest Entrance),2446.9314,898.06137\n" +
                "MCM (SW),McMahon Hall (Southwest Entrance),2419.3153,921.79995\n" +
                "CMU,Communications Building,2344.8512,1114.6251";
        String[] split = files.split("\n");
        for (int i = 0; i < split.length; i++) {
            ArrayList<Integer> integers = new ArrayList<>();
            for (int j = 0; j < split[i].length(); j++) {
                if (split[i].charAt(j) == ',') {
                    integers.add(j);
                }
            }
            System.out.println("\"" + split[i].substring(0, integers.get(0)) + "\" : {value : [" + split[i].substring(1 + integers.get(integers.size() - 2)) + "]},");
        }
    }
}
