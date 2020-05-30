import React from 'react'
import './styles.css'
import 'bootstrap/dist/css/bootstrap.css';

export default class BookDetailsPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            genres: [],
            bookAuthors: [],
            book: {
                genre: {
                }
            }
        };

        this.updateBook = this.updateBook.bind(this);
    }

    updateBook() {
        fetch('/api/v1/book/' + this.props.bookId, {
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
                <div>
                    <div className="form-group">
                        <label htmlFor="name">Name:</label>
                        <input type="text" value={this.state.book.name} className="form-control" id="name"/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="year">Year:</label>
                        <input type="text" value={this.state.book.year} className="form-control" id="year"/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="genre">Genre:</label>
                        <select value={this.state.book.genre.id} className="custom-select">
                            {
                                this.state.genres.map((genre) => (
                                    <option value={genre.id}>{genre.name}</option>
                                ))
                            }
                        </select>
                    </div>
                    <button className="btn btn-primary" onClick={this.updateBook}>Save</button>
                    <button className="btn btn-secondary">
                      Cancel
                    </button>
                </div>
            </React.Fragment>
        )
    }
}


