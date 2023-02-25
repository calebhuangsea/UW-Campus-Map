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

import {icon, LatLng, LatLngExpression} from "leaflet";
import React, { Component } from "react";
import {MapContainer, Marker, TileLayer, useMapEvent} from 'react-leaflet'
import "leaflet/dist/leaflet.css";
import MapLine from "./MapLine";
import {
    UW_LATITUDE,
    UW_LATITUDE_CENTER, UW_LATITUDE_OFFSET, UW_LATITUDE_SCALE,
    UW_LONGITUDE,
    UW_LONGITUDE_CENTER,
    UW_LONGITUDE_OFFSET,
    UW_LONGITUDE_SCALE
} from "./Constants";


// This defines the location of the map. These are the coordinates of the UW Seattle campus
const position : LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER];

interface MapProps {
    edgeList : Array<string>;//line information
    onClick : () => any;
    hover : (latlng : LatLngExpression) => any;
    // pointList : LatLngExpression[];
    startLoc : number[];
    endLoc : number[];
    currMarker : number[];
}

function ClickPoint({props}: { props: MapProps}) {
    useMapEvent('click', (e) => {
        props.onClick();
    });
    return null;
}

function Hover({props} : {props:MapProps}) {
    useMapEvent('mousemove', (e) => {
        props.hover(e.latlng);
    });
    return null;
}

class MapComponent extends Component<MapProps, {}> {

    render() {
        var myIcon = icon({
            iconUrl: 'https://unpkg.com/leaflet@1.6.0/dist/images/marker-icon.png',
            iconAnchor: [11.5, 41]
        });

        let mapLines: any = [];
        for (let i = 1; i <= this.props.edgeList.length; i++) {
            let line: Array<string> = this.props.edgeList[i - 1].split(" ");
            mapLines.push(<MapLine key={i + "line"} color={line[4]} x1={parseInt(line[0])} y1={parseInt(line[1])}
                                   x2={parseInt(line[2])} y2={parseInt(line[3])}/>);
        }

        let markers: any = [];
        markers.push(<Marker icon={myIcon} key={"marker start"} position={[this.props.startLoc[0], this.props.startLoc[1]]}></Marker>);
        markers.push(<Marker icon={myIcon} key={"marker end"} position={[this.props.endLoc[0], this.props.endLoc[1]]}></Marker>);
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
                    <ClickPoint props={this.props}></ClickPoint>
                    <Hover props={this.props}></Hover>
                    {
                        <Marker icon={myIcon} key={"marker undefined"} position={[this.props.currMarker[0],this.props.currMarker[1]]}></Marker>
                    }
                    {
                        mapLines
                    }
                    {
                        markers
                    }
                </MapContainer>
            </div>
        );
    }
}

export default MapComponent;
