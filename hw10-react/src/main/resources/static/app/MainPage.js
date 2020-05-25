import React from 'react'
import './styles.css'
import 'bootstrap/dist/css/bootstrap.css';
import {HashRouter, NavLink, Route} from "react-router-dom";
import BooksPage from "./BooksPage";
import AuthorsPage from "./AuthorsPage";
import GenresPage from "./GenresPage";

export default class MainPage extends React.Component {

    render() {
        return (
            <HashRouter>
                <div>
                    <h1>Simple SPA</h1>
                    <ul className="header">
                        <li><NavLink to="/">Books</NavLink></li>
                        <li><NavLink to="/authors">Authors</NavLink></li>
                        <li><NavLink to="/genres">Genres</NavLink></li>
                    </ul>
                    <div className="content">
                        <Route exact path="/" component={BooksPage}/>
                        <Route path="/authors" component={AuthorsPage}/>
                        <Route path="/genres" component={GenresPage}/>
                    </div>
                </div>
            </HashRouter>
        )
    }
}


