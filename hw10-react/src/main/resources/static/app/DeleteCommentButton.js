import React, {Component} from 'react'

export class DeleteCommentButton extends Component {
    constructor(props) {
        super(props);

        this.deleteComment = this.deleteComment.bind(this);
    }

    deleteComment() {
        fetch('/api/v1/comment', {
            method: "DELETE", body: {commentId: this.props.commentId}
        })
            .then(response => response.json())
            .then(json => this.props.reloadComments())
    }

    render() {
        return (
            <React.Fragment>
                <button onClick={this.deleteComment}
                        className="btn btn-danger"
                        type='button'>Delete
                </button>
            </React.Fragment>
        )
    }
}