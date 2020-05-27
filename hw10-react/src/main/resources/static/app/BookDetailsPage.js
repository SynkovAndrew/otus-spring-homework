import React from 'react'
import './styles.css'
import 'bootstrap/dist/css/bootstrap.css';

export default class BookDetailsPage extends React.Component {
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

            </React.Fragment>
        )
    }
}


