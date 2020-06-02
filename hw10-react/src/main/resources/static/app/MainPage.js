import React from 'react'
import './styles.css'
import 'bootstrap/dist/css/bootstrap.css';
import {HashRouter, NavLink, Route} from "react-router-dom";
import BooksPage from "./BooksPage";
import AuthorsPage from "./AuthorsPage";
import GenresPage from "./GenresPage";
import BookDetailsPage from "./BookDetailsPage";
import BookCommentsPage from "./BookCommentsPage";

export default class MainPage extends React.Component {
    render() {
        return (
            <HashRouter>
                <div>
                    <nav className="navbar navbar-expand-lg navbar-dark bg-dark ">
                        <div className="collapse navbar-collapse" id="navbarNav">
                            <ul className="navbar-nav header-menu">
                                <li className="nav-item">
                                    <NavLink className="nav-link" to="/">Books</NavLink>
                                </li>
                                <li className="nav-item">
                                    <NavLink className="nav-link" to="/authors">Authors</NavLink>
                                </li>
                                <li className="nav-item">
                                    <NavLink className="nav-link" to="/genres">Genres</NavLink>
                                </li>
                            </ul>
                        </div>
                    </nav>
                    <div className="content main-div">
                        <Route exact path="/" component={BooksPage}/>
                        <Route path="/authors" component={AuthorsPage}/>
                        <Route path="/genres" component={GenresPage}/>
                        <Route exact path="/book/:bookId" component={BookDetailsPage}/>
                        <Route exact path="/book/:bookId/comments" component={BookCommentsPage}/>
                    </div>
                </div>
            </HashRouter>
        )
    }
}


