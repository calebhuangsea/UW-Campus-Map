/*
 * Select boxes for start and end buildings
 * Allows user to pick start and end buildings
 * and update data to our App
 */

import React, {Component} from 'react';

interface SelectListProps {
    onClear: () => any;//call back when click clear
    onChangeStart: (start : string) => any;//call back start box value when click clear
    onChangeEnd: (end : string) => any;//call back end box value when click clear
    startName : string; //start building's name
    endName : string;// end building's name
    currName : string;//current building name
}

interface SelectListState {
    buildingList : string[];// store every building's short name
    startSelect : string;
    endSelect : string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class SelectList extends Component<SelectListProps, SelectListState> {

    constructor(props : any) {
        super(props);
        this.state = {
            buildingList :[],
            startSelect: "",
            endSelect:""
        }
    }


    componentDidMount() {
        this.findBuildings();
    }

    render() {
        let startOptions : any = [];
        startOptions.push(<option key={-1} value={""}>Please select a start building</option>);
        for (let i = 0; i < this.state.buildingList.length; i++) {
            let name : string = this.state.buildingList[i];
            startOptions.push(<option key={"end : " + i} value={name}>{name}</option>);
        }

        let endOptions : any = [];
        endOptions.push(<option key={-1} value={""}>Please select an end building</option>);
        for (let i = 0; i < this.state.buildingList.length; i++) {
            let name : string = this.state.buildingList[i];
            endOptions.push(<option key={i} value={name}>{name}</option>);
        }

        return (
            <div>
                <div id="buildingInfo">
                    <div id="startName">
                        <h3>Start Building Name: {this.props.startName}</h3>
                    </div>
                    <div id="endName">
                        <h3>End Building Name: {this.props.endName}</h3>
                    </div>
                    <div id="currName">
                        <h3>Current Building Name: {this.props.currName}</h3>
                    </div>
                </div>
                <div id="select-list">
                    <h4>Start Building</h4>
                    <select value={this.props.startName} id={"start"} onChange={(e) => {this.props.onChangeStart(e.target.value); this.setState({startSelect : e.target.value})}}>
                        {
                            startOptions
                        }
                    </select>
                    <h4>End Building</h4>
                    <select value={this.props.endName} id={"end"} onChange={(e) => {this.props.onChangeEnd(e.target.value); this.setState({endSelect : e.target.value})}}>
                        {
                            endOptions
                        }
                    </select>
                    <br/>
                    <button onClick={() => {
                        this.setState({startSelect : "", endSelect : ""})
                        this.props.onClear()}}>Clear Lines</button>
                </div>
                <div id="Help">
                    <h2>How to use Husky Campus Map?</h2>
                    <p>1. Move Your Mouse On the Map to Locate the Building You Want!</p>
                    <p>2. Click Two Buildings to See the Shortest Path Between Them!</p>
                    <p>3. Select Two Buildings From the Dropdown Boxes to See the Shortest Path Between Them!</p>
                </div>
            </div>
        );
    }

    clearSelect() {
        // @ts-ignore
        this.setState({startSelect : "", endSelect : ""})
        this.props.onChangeStart("");
        this.props.onChangeEnd("");
    }

    findBuildings = async () => {
        // This does the exact same thing as makeRequestLong(), in the exact same way.
        // It's just written using a much shorter syntax with less unnecessary variables.
        // The following is basically exactly the structure of what you're going to what to
        // use in HW9 to make a request: you can model your code off of this.
        // Make sure you understand how it works, so you can modify it to do what you want!
        try {
            let response = await fetch("http://localhost:4567/allBuildings");
            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return;
            }
            let json = await response.json();
            let results : string[] = [];
            for (let i = 0; i < json.length; i++) {
                results.push(json[i]);
            }
            this.setState({buildingList : results});
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    }
}

export default SelectList;
