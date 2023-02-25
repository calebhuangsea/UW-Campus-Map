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

import React, { Component } from "react";
import MapComponent from "./MapComponent";
import Select from "./Select";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";
import {LatLngExpression} from "leaflet";
import {
    BUILDINGS,
    UW_LATITUDE,
    UW_LATITUDE_OFFSET,
    UW_LATITUDE_SCALE,
    UW_LONGITUDE,
    UW_LONGITUDE_OFFSET,
    UW_LONGITUDE_SCALE
} from "./Constants";


interface AppState {
    edgeList: Array<string>;//line information
    startLoc : number[];
    endLoc : number[];
    currMarker : number[];
    currName : string;
    startName : string;
    endName : string;
}

class App extends Component<{}, AppState> { // <- {} means no props.

    constructor(props: any) {
        super(props);
        this.state = {
            edgeList: [],
            startLoc : [-10000, -10000],
            endLoc : [-10000, -10000],
            currMarker : [-10000, -10000],
            startName : "",
            endName : "",
            currName : ""
        }
    }

    render() {
        return (
            <div>
                {/*<AnimateExample></AnimateExample>*/}
                <div id="title">
                    <h1 id="app-title">UW Campus Paths</h1>
                </div>
                <div>
                    <div id="selectBox">
                        <Select
                            onClear={() => {
                                this.clearSelect();
                            }}
                            onChangeStart={(value : string) => {
                                this.setStartMarker(value);
                            }}
                            onChangeEnd={(value : string) => {
                                this.setEndMarker(value);
                            }}
                            // @ts-ignore
                            startName={this.state.startName}
                            endName={this.state.endName}
                            currName={this.state.currName}
                        >
                        </Select>
                    </div>
                    <div id="mapBox">
                        <MapComponent
                            edgeList={this.state.edgeList}
                            onClick={() => {
                                this.setClickMarker(this.state.currMarker);
                            }}
                            hover={(value : LatLngExpression) => {
                                this.setCurrMarker(value);
                            }}
                            startLoc={this.state.startLoc}
                            endLoc={this.state.endLoc}
                            currMarker={this.state.currMarker}/>
                    </div>
                </div>
            </div>
        );
    }

    drawLine() {
        if (this.state.startName !== "" && this.state.endName !== "") {
            this.setName([this.state.startName, this.state.endName]);
        }
    }

    drawSelectLine(start : string, end: string) {
        if (start === "") {
            start = this.state.startName;
        }
        if (end === "") {
            end = this.state.endName;
        }
        if (start !== "" && end !== "") {
            this.setName([start, end]);
        }
    }

    setClickMarker(value : number[]) {
        // @ts-ignore
        if (this.state.startLoc[0] === -10000) {
            this.setState({startName : this.state.currName});
            this.setState({startLoc : this.state.currMarker});
            // @ts-ignore
        } else if (this.state.endLoc[0] === -10000) {
            this.setState({endName : this.state.currName});
            this.setState({endLoc : this.state.currMarker});
        }
        this.drawLine();
    }

    setStartMarker(value : string) {
        if (value !== "") {
            // @ts-ignore
            let position: number[] = BUILDINGS[value]["value"];
            this.setState({startName : value, startLoc: [UW_LATITUDE + (position[1] - UW_LATITUDE_OFFSET) * UW_LATITUDE_SCALE, UW_LONGITUDE + (position[0] - UW_LONGITUDE_OFFSET) * UW_LONGITUDE_SCALE]});
        }
        this.drawSelectLine(value, "");
    }

    setEndMarker(value : string) {
        if (value !== "") {
            // @ts-ignore
            let position: number[] = BUILDINGS[value]["value"];
            this.setState({endName : value, endLoc: [UW_LATITUDE + (position[1] - UW_LATITUDE_OFFSET) * UW_LATITUDE_SCALE, UW_LONGITUDE + (position[0] - UW_LONGITUDE_OFFSET) * UW_LONGITUDE_SCALE]});
        }
        this.drawSelectLine("", value);
    }

    clearSelect() {
        this.setState({
            edgeList : [],
            startName : "",
            endName : "",
            startLoc : [-10000, -10000],
            endLoc : [-10000, -10000]
        })
    }

    setCurrMarker(value : LatLngExpression) {
        // @ts-ignore
        if (this.state.startLoc[0] === -10000 || this.state.endLoc[0] === -10000) {
            let distance: number = 10000000000;
            let getBuilding: string = "";
            // @ts-ignore
            let lat: number = value['lat'];
            lat = ((lat - UW_LATITUDE) / UW_LATITUDE_SCALE) + UW_LATITUDE_OFFSET
            // @ts-ignore
            let lng: number = value['lng'];
            lng = ((lng - UW_LONGITUDE) / UW_LONGITUDE_SCALE) + UW_LONGITUDE_OFFSET
            // @ts-ignore
            for (let key of Object.keys(BUILDINGS)) {
                // @ts-ignore
                let position: number[] = BUILDINGS[key]["value"];
                let dist: number = (lng - position[0]) * (lng - position[0]) + (lat - position[1]) * (lat - position[1]);
                if (dist < distance) {
                    distance = dist;
                    getBuilding = key;
                }
            }
            // @ts-ignore
            let position: number[] = BUILDINGS[getBuilding]["value"];
            this.setState({currMarker: [UW_LATITUDE + (position[1] - UW_LATITUDE_OFFSET) * UW_LATITUDE_SCALE, UW_LONGITUDE + (position[0] - UW_LONGITUDE_OFFSET) * UW_LONGITUDE_SCALE]})
            this.setState({currName : getBuilding});
            if (this.state.startLoc[0] !== -10000) {
                this.drawSelectLine(this.state.startName, this.state.currName);
            }
        } else {
            this.setState({currMarker : [-100000, -100000]});
        }
    }

    /**
     * set lines information according to given string
     * Every line should have 5 elements separated by space
     * where first 4 elements needs to be number in range 0 - 4000
     * the fifth element is color
     * if length, type, range not satisfied, alert
     * @param values string for lines
     */
    async setName(values : string[]) {
        if (values.length !== 0) {
            // let flag: boolean = false;
            if (values[0] === "" || values[1] === "") {
                alert("invalid input in building names box!!");
            } else {
                await this.findPathRequest(values[0], values[1]);
            }
        }
    }

    findPathRequest = async (start : string, end : string) => {
        // This does the exact same thing as makeRequestLong(), in the exact same way.
        // It's just written using a much shorter syntax with less unnecessary variables.
        // The following is basically exactly the structure of what you're going to what to
        // use in HW9 to make a request: you can model your code off of this.
        // Make sure you understand how it works, so you can modify it to do what you want!
        try {
            let response = await fetch("http://localhost:4567/findPath?start=" + start + "&end=" + end);
            if (!response.ok) {
                alert("Make Sure You Have Select a Building!");
                return;
            }
            let json = await response.json();
            let paths : JSON[]  = json["path"];
            let results : string[] = [];
            for (let i = 0; i < paths.length; i++) {
                let path : JSON = paths[i];
                // @ts-ignore
                let start : JSON = path['start'];
                // @ts-ignore
                let x0 : string = start['x'];
                // @ts-ignore
                let y0 : string = start['y'];
                // @ts-ignore
                let end : JSON = path['end'];
                // @ts-ignore
                let x1 : string = end['x'];
                // @ts-ignore
                let y1 : string = end['y'];
                results.push(x0 + " " + y0 + " " + x1 + " " + y1 + " purple");
            }
            this.setState({edgeList : results});
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };
}

export default App;