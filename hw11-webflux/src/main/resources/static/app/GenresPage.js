import React from 'react'
import './styles.css'
import 'bootstrap/dist/css/bootstrap.css';

export default class GenresPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            genres: []
        };
    }

    componentDidMount() {
        fetch('/api/v1/genres')
            .then(response => response.json())
            .then(json => this.setState({genres: json.genres}));

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
                            this.state.genres.map((genre, index) => (
                                <tr key={index} className="table-row">
                                    <td>{genre.name}</td>
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


