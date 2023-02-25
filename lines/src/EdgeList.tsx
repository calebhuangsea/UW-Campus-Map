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

import React, {Component} from 'react';

interface EdgeListProps {
    onChange: () => any;//call when text is changed
    onDraw: (edges: string) => any;//call back textvalues when click draw
    onClear: (edges: string) => any;//call back textvalues when click clear
}

interface EdgeListState {
    textValues : string;//textvalues in the text box
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {

    constructor(props : EdgeListProps) {
        super(props);
        this.state = {textValues : "" }
    }

    textChange(e : any) {
        this.setState(
            {textValues : e.target.value}
        )
    }

    render() {
        return (
            <div id="edge-list">
                <p>Edges(x1 y1 x2 y2 COLOR)</p>
                <p>Points(x y)</p>
                <p>x and y coordinates should between 0-4000 </p>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={(e) => {this.textChange(e);}}
                    value={this.state.textValues}
                /> <br/>
                <button onClick={() => {this.props.onDraw(this.state.textValues)}}>Draw Lines</button>
                <button onClick={() => {this.setState({textValues : ""}); this.props.onClear("")}}>Clear Lines</button>
            </div>
        );
    }
}

export default EdgeList;
