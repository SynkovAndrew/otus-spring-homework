import React from 'react'
import './styles.css'
import 'bootstrap/dist/css/bootstrap.css';
import {HashRouter, NavLink, Route} from "react-router-dom";
import {DeleteBookButton} from "./DeleteBookButton";
import AuthorsPage from "./AuthorsPage";
import GenresPage from "./GenresPage";
import BookDetailsPage from "./BookDetailsPage";

export default class BooksPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            books: []
        };
        this.reloadBooks = this.reloadBooks.bind(this);
    }

    componentDidMount() {
        this.reloadBooks();
    }

    reloadBooks() {
        fetch('/api/v1/books')
            .then(response => response.json())
            .then(json => this.setState({books: json.books}));
    }

    render() {
        return (
            <React.Fragment>
                <HashRouter>
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
                                        <td>
                                            <button type="button" className="btn btn-success">
                                                <NavLink to={'/book/' + book.id + '/comments'}>Comments</NavLink>
                                            </button>
                                        </td>
                                        <td>
                                            <button type="button" className="btn btn-primary">
                                                <NavLink to={'/book/' + book.id}>Edit</NavLink>
                                            </button>
                                        </td>
                                        <td>
                                            <DeleteBookButton bookId={book.id} reloadBooks={this.reloadBooks}/>
                                        </td>
                                    </tr>
                                ))
                            }
                            </tbody>
                        </table>
                    </div>
                    <div className="content main-div">
                        <Route exact path="/book/:bookId" component={BookDetailsPage}/>
                        <Route exact path="/book/:bookId/comments" component={BookDetailsPage}/>
                    </div>
                </HashRouter>
            </React.Fragment>
        )
    }
}


