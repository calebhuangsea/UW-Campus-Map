/*
 * Point.tsx rrepresents the point input box and draw
 */

import React, {Component} from 'react';

interface PointProps {
    onChange: () => any;//call when text is changed
    onDraw: (edges: string) => any;//call back textvalues when click draw
    onClear: (edges: string) => any;//call back textvalues when click clear
}

interface PointState {
    textValues : string;//textvalues in the text box
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class Point extends Component<PointProps, PointState> {

    constructor(props : any) {
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
                <p>Points(x y color)</p>
                <p>x and y coordinates should between 0-4000 </p>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={(e) => {this.textChange(e);}}
                    value={this.state.textValues}
                /> <br/>
                <button onClick={() => {this.props.onDraw(this.state.textValues)}}>Draw Point</button>
                <button onClick={() => {this.setState({textValues : ""}); this.props.onClear("")}}>Clear Points</button>
            </div>
        );
    }
}

export default Point;
