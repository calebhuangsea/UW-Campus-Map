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
import EdgeList from "./EdgeList";
import Map from "./Map";
import Point from "./Point"

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";

interface AppState {
    edgeList: Array<string>;//line information
    pointList: Array<string>;//point information
}

class App extends Component<{}, AppState> { // <- {} means no props.

  constructor(props: any) {
    super(props);
    this.state = {
        edgeList : [],
        pointList: []
    };
  }

  render() {
    return (
      <div>
          {/*<AnimateExample></AnimateExample>*/}
          <div id="title">
              <h1 id="app-title">Line Mapper!</h1>
          </div>
        <div id="mapBox">
          <Map edgeList={this.state.edgeList} pointList={this.state.pointList}/>
        </div>
        <EdgeList
          onChange={() => {}}
          onDraw={(value : string) => {
              this.setEdgeList(value);
          }}
          onClear={(value : string) => {
              this.setState({edgeList : []});
          }}
        />
          <Point
              onChange={() => {}}
              onDraw={(value : string) => {
                  this.setPointList(value);
              }}
              onClear={(value : string) => {
                  this.setState({pointList : []});
              }}
          />
      </div>
    );
  }

    /**
     * set lines information according to given string
     * Every line should have 5 elements separated by space
     * where first 4 elements needs to be number in range 0 - 4000
     * the fifth element is color
     * if length, type, range not satisfied, alert
     * @param values string for lines
     */
  setEdgeList(values : string) {
      if (values.length !== 0) {
          let edges: string[] = values.split("\n");
          let flag: boolean = false;
          let lines: Array<string> = edges;
          for (let i = 0; i < lines.length && !flag; i++) {
              let line: string[] = lines[i].split(" ");
              if (line.length !== 5) {
                  console.log(1);
                  flag = true;
              }
              for (let j = 0; j < line.length - 1; j++) {
                  if (isNaN(Number(line[j]))) {
                      console.log(2222);
                      console.log(Number(line[j]));
                      flag = true;
                  }
                  if (parseInt(line[j]) < 0 || parseInt(line[j]) > 4000) {
                      console.log(333);
                      flag = true;
                  }
              }
          }
          if (flag) {
              alert("invalid input in edge box!!");
          } else {
              this.setState({edgeList: values.split("\n")});
          }
      }
  }

    /**
     * set points information according to given string
     * Every point should have 3 elements separated by space
     * where first 2 elements needs to be number in range 0 - 4000
     * the third element is color
     * if length, type, range not satisfied, alert
     * @param values string for lines
     */
    setPointList(values : string) {
        if (values.length !== 0) {
            let edges: string[] = values.split("\n");
            let flag: boolean = false;
            let lines: Array<string> = edges;
            for (let i = 0; i < lines.length && !flag; i++) {
                let line: string[] = lines[i].split(" ");
                if (line.length !== 3) {
                    flag = true;
                }
                for (let j = 0; j < line.length - 1; j++) {
                    if (!Number(line[j])) {
                        flag = true;
                    }
                    if (parseInt(line[j]) < 0 || parseInt(line[j]) > 4000) {
                        flag = true;
                    }
                }
            }
            // if (flag) {
            //     alert("invalid input in point box!!");
            // } else {
                this.setState({pointList: values.split("\n")});
            // }
        }
    }
}

export default App;
