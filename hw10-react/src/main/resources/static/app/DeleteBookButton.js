import React, {Component} from 'react'

export class DeleteBookButton extends Component {
    constructor(props) {
        super(props);
    }

    deleteBook = () => {
        fetch('/api/v1/book/' + this.props.bookId, {method: "DELETE"})
            .then(response => response.json())
            .then(json => this.props.reloadBooks())
    }

    render() {
        return (
            <React.Fragment>
                <button onClick={this.deleteBook}
                        className="btn btn-danger"
                        type='button'>Delete
                </button>
            </React.Fragment>
        )
    }
}