import React from "react"
import "./styles.css"
import "bootstrap/dist/css/bootstrap.css";

export default class AuthorsPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            authors: []
        };
    }

    componentDidMount() {
        fetch("/api/v1/authors", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: "{}"
        })
            .then(response => response.json())
            .then(json => this.setState({authors: json.authors}));

    }

    render() {
        return (
            <React.Fragment>
                <div>
                    <table className="table">
                        <thead className="thead-dark">
                        <tr>
                            <th scope="col">Name</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.authors.map((author, index) => (
                                <tr key={index} className="table-row">
                                    <td>{author.name}</td>
                                </tr>
                            ))
                        }
                        </tbody>
                    </table>
                </div>
            </React.Fragment>
        )
    }
}


