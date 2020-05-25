import React from 'react'
import './styles.css'
import 'bootstrap/dist/css/bootstrap.css';

export default class BooksPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            books: []
        };
    }

    componentDidMount() {
        fetch('/api/v1/books')
            .then(response => response.json())
            .then(json => this.setState({books: json.books}));

    }

    render() {
        return (
            <React.Fragment>
                <div>
                    <table className="table">
                        <thead className="thead-dark">
                        <tr>
                            <th scope="col">Name</th>
                            <th scope="col">Genre</th>
                            <th scope="col">Year</th>
                            <th scope="col" className="short-column"/>
                            <th scope="col" className="short-column"/>
                            <th scope="col" className="short-column"/>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.books.map((book, index) => (
                                <tr key={index} className="table-row">
                                    <td>{book.name}</td>
                                    <td>{book.genre.name}</td>
                                    <td>{book.year}</td>
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


