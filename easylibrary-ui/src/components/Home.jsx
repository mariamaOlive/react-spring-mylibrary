import books from "../data/books";

import BookListing from "./BookListing";

export default function Home() {
    return (
        <div className="home">
            <BookListing books={books} />
        </div>
    );
}