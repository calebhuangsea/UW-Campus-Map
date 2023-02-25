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

import { LatLngExpression } from "leaflet";
import React, { Component } from "react";
import { MapContainer, TileLayer } from 'react-leaflet'
import "leaflet/dist/leaflet.css";
import MapLine from "./MapLine";
import {UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER} from "./Constants";

// This defines the location of the map. These are the coordinates of the UW Seattle campus
const position : LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER];

interface MapProps {
    edgeList : Array<string>;//line information
    pointList : Array<string>;//line information
}

class Map extends Component<MapProps, {}> {
    constructor(props : any) {
        super(props);
    }


    render() {
        let mapLines : any = [];
        for (let i = 1; i <= this.props.edgeList.length; i++) {
            let line: Array<string> = this.props.edgeList[i - 1].split(" ");
            mapLines.push(<MapLine key={i + "line"} color={line[4]} x1={parseInt(line[0])} y1={parseInt(line[1])}
                                   x2={parseInt(line[2])} y2={parseInt(line[3])}/>);
        }
        for (let i = 1; i <= this.props.pointList.length; i++) {
            let line: Array<string> = this.props.pointList[i - 1].split(" ");
            mapLines.push(<MapLine key={i + "pointleft"} color={line[2]} x1={parseInt(line[0]) - 5} y1={parseInt(line[1]) - 5}
                                   x2={parseInt(line[0]) - 5} y2={parseInt(line[1]) + 5}/>);

            mapLines.push(<MapLine key={i + "pointup"} color={line[2]} x1={parseInt(line[0]) - 3} y1={parseInt(line[1]) + 5}
                                   x2={parseInt(line[0]) + 5} y2={parseInt(line[1]) + 5}/>);

            mapLines.push(<MapLine key={i + "pointright"} color={line[2]} x1={parseInt(line[0]) + 3} y1={parseInt(line[1]) + 5}
                                   x2={parseInt(line[0]) + 5} y2={parseInt(line[1]) - 5}/>);

            mapLines.push(<MapLine key={i + "pointbottom"} color={line[2]} x1={parseInt(line[0]) - 3} y1={parseInt(line[1]) - 5}
                                   x2={parseInt(line[0]) + 5} y2={parseInt(line[1]) - 5}/>);
        }

      return (
      <div id="map">
        <MapContainer
          center={position}
          zoom={15}
          scrollWheelZoom={false}
        >
          <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
            {
                mapLines
            }
        </MapContainer>
      </div>
    );
  }
}

export default Map;
