import React from 'react'
import './styles.css'
import 'bootstrap/dist/css/bootstrap.css';
import {DeleteBookButton} from "./DeleteBookButton";
import {DeleteCommentButton} from "./DeleteCommentButton";

export default class BookCommentsPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            comments: []
        };

        this.reloadComments = this.reloadComments.bind(this);
        this.goToBookDetailsPage = this.goToBookDetailsPage.bind(this);
    }

    componentDidMount() {
        this.reloadComments();
    }

    reloadComments() {
        const bookId = this.props.match.params.bookId;

        fetch('/api/v1/book/' + bookId + '/comments')
            .then(response => response.json())
            .then(json => this.setState({comments: json.comments}));
    }

    goToBookDetailsPage() {
        const {history} = this.props;
        history.push('/')
    }

    render() {
        return (
            <React.Fragment>
                <div>
                    <table className="table">
                        <thead className="thead-dark">
                        <tr>
                            <th scope="col">Comment</th>
                            <th scope="col" className="short-column"/>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.comments.map((comment, index) => (
                                <tr key={index} className="table-row">
                                    <td>{comment.value}</td>
                                    <td>
                                        <DeleteCommentButton commentId={comment.id} reloadComments={this.reloadComments}/>
                                    </td>
                                </tr>
                            ))
                        }
                        </tbody>
                    </table>
                </div>
                <div>
                    <button className="btn btn-secondary button-margin"
                            onClick={this.goToBookDetailsPage}>Back</button>
                </div>
            </React.Fragment>
        )
    }
}


