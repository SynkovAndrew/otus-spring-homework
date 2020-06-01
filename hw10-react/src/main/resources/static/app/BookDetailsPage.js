import React from 'react'
import './styles.css'
import 'bootstrap/dist/css/bootstrap.css';

export default class BookDetailsPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            genres: [],
            authors: [],
            book: {
                genre: {},
                authors: []
            }
        };

        this.updateBook = this.updateBook.bind(this);
        this.goToBooksPage = this.goToBooksPage.bind(this);
        this.onNameChange = this.onNameChange.bind(this);
        this.onYearChange = this.onYearChange.bind(this);
        this.onGenreChange = this.onGenreChange.bind(this);
        this.onAuthorChange = this.onAuthorChange.bind(this);
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

    onAuthorChange(event) {
        const options = event.target.options;
        const selectedAuthors = [];
        const authors = this.state.authors;

        for (let i = 0, l = options.length; i < l; i++) {
            if (options[i].selected) {
                let found = authors.find(author => author.id === parseInt(options[i].value));
                if (found !== undefined) {
                    selectedAuthors.push(found);
                }
            }
        }

        this.setState({
            book: {
                ...this.state.book,
                authors: selectedAuthors
            }
        });
    }

    goToBooksPage() {
        const {history} = this.props;
        history.push('/')
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
                year: this.state.book.year,
                authorIds: this.state.book.authors.map(a => a.id)
            })
        })
            .then(response => response.json())
            .then(json => this.setState({book: json}))

        this.goToBooksPage();
    }

    componentDidMount() {
        const bookId = this.props.match.params.bookId;

        fetch('/api/v1/book/' + bookId)
            .then(response => response.json())
            .then(json => this.setState({book: json}));

        fetch('/api/v1/genres')
            .then(response => response.json())
            .then(json => this.setState({genres: json.genres}));

        fetch('/api/v1/authors')
            .then(response => response.json())
            .then(json => this.setState({authors: json.authors}));
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
                        <label>Genre:</label>
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
                    <div className="form-group">
                        <label htmlFor="genre">Authors:</label>
                        <select multiple={true}
                                value={
                                    this.state.book.authors.map(a => a.id)
                                }
                                onChange={this.onAuthorChange}
                                className="custom-select">
                            {
                                this.state.authors.map((author) => (
                                    <option key={author.id} value={author.id}>{author.name}</option>
                                ))
                            }
                        </select>
                    </div>
                    <button className="btn btn-primary button-margin" onClick={this.updateBook}>Save</button>
                    <button className="btn btn-secondary button-margin" onClick={this.goToBooksPage}>Back</button>
                </div>
            </React.Fragment>
        )
    }
}


