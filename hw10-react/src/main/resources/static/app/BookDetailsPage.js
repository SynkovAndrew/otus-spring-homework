import React from 'react'
import './styles.css'
import 'bootstrap/dist/css/bootstrap.css';
import {Route} from "react-router-dom";

export default class BookDetailsPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            genres: [],
            bookAuthors: [],
            book: {
                genre: {}
            }
        };

        this.updateBook = this.updateBook.bind(this);
        this.onNameChange = this.onNameChange.bind(this);
        this.onYearChange = this.onYearChange.bind(this);
        this.onGenreChange = this.onGenreChange.bind(this);
    }

    onNameChange(event) {
        this.setState({
            book: {
                ...this.state.book,
                name: event.target.value
            }
        });
    }

    onYearChange(event) {
        this.setState({
            book: {
                ...this.state.book,
                year: event.target.value
            }
        });
    }

    onGenreChange(event) {
        this.setState({
            book: {
                ...this.state.book,
                genre: {
                    ...this.state.book.genre,
                    id: event.target.value
                }
            }
        });
    }

    updateBook() {
        fetch('/api/v1/book/' + this.props.match.params.bookId, {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                genreId: this.state.book.genre.id,
                name: this.state.book.name,
                year: this.state.book.year
            })
        })
            .then(response => response.json())
            .then(json => this.setState({book: json}))
    }

    componentDidMount() {
        const bookId = this.props.match.params.bookId;

        fetch('/api/v1/book/' + bookId)
            .then(response => response.json())
            .then(json => this.setState({book: json}));

        fetch('/api/v1/genres')
            .then(response => response.json())
            .then(json => this.setState({genres: json.genres}));

        fetch('/api/v1/book/' + bookId + '/authors')
            .then(response => response.json())
            .then(json => this.setState({bookAuthors: json.authors}));
    }

    render() {
        return (
            <React.Fragment>
                <div className="details-page">
                    <div className="form-group">
                        <label>Name:</label>
                        <input type="text"
                               value={this.state.book.name}
                               onChange={this.onNameChange}
                               className="form-control"/>
                    </div>
                    <div className="form-group">
                        <label>Year:</label>
                        <input type="text"
                               value={this.state.book.year}
                               onChange={this.onYearChange}
                               className="form-control"/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="genre">Genre:</label>
                        <select value={this.state.book.genre.id}
                                onChange={this.onGenreChange}
                                className="custom-select">
                            {
                                this.state.genres.map((genre) => (
                                    <option key={genre.id} value={genre.id}>{genre.name}</option>
                                ))
                            }
                        </select>
                    </div>
                    <button className="btn btn-primary" onClick={this.updateBook}>Save</button>
                    <Route render={history => (
                       <button className="btn btn-secondary" onClick={() => history.push('/')}>Back</button>
                    )}/>
                </div>
            </React.Fragment>
        )
    }
}


