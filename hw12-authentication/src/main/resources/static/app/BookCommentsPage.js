import React from 'react'
import './styles.css'
import 'bootstrap/dist/css/bootstrap.css';
import {DeleteCommentButton} from "./DeleteCommentButton";

export default class BookCommentsPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            comments: [],
            comment: ""
        };
    }

    componentDidMount() {
        this.reloadComments();
    }

    onCommentChange = (event) => {
        this.setState({
            comment: event.target.value
        });
    }

    reloadComments = () => {
        const bookId = this.props.match.params.bookId;

        fetch('/api/v1/book/' + bookId + '/comments')
            .then(response => response.json())
            .then(json => this.setState({comments: json.comments}));
    }

    goToBookDetailsPage = () => {
        const {history} = this.props;
        history.push('/')
    }

    addComment = () => {
        const comment = this.state.comment;
        if (comment !== null && comment !== "") {
            fetch('/api/v1/comment', {
                method: "POST",
                body: JSON.stringify({
                    bookId: this.props.match.params.bookId,
                    comment: comment
                }),
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => response.json())
                .then(json =>  {
                    this.reloadComments()
                    this.setState({
                        comment: ""
                    });
                })
        }
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
                                        <DeleteCommentButton
                                            commentId={comment.id}
                                            reloadComments={this.reloadComments}/>
                                    </td>
                                </tr>
                            ))
                        }
                        </tbody>
                    </table>
                </div>
                <div>
                    <div className="form-group margin-5">
                        <textarea rows={4}
                                  value={this.state.comment}
                                  onChange={this.onCommentChange}
                                  className="form-control"/>
                    </div>
                </div>
                <div>
                    <button className="btn btn-primary margin-5"
                            onClick={this.addComment}>Comment
                    </button>
                    <button className="btn btn-secondary margin-5"
                            onClick={this.goToBookDetailsPage}>Back
                    </button>
                </div>
            </React.Fragment>
        )
    }
}


