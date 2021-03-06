import React, {Component} from 'react'

export class DeleteCommentButton extends Component {
    constructor(props) {
        super(props);
    }

    deleteComment = () => {
        fetch('/api/v1/comment', {
            method: "DELETE",
            body: JSON.stringify({commentId: this.props.commentId}),
            headers : {
                'Content-Type': 'application/json'
            }
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