import BookCard from "./BookCard";

export default function BookListing({ books }) {
  return (
    <div className="book-listing">
      <div className="book-grid">
        {books.length > 0 ? (
          books.map((book) => (
            <BookCard key={book.bookId} book={book} />
          ))
        ) : (
          <p>No books available.</p>
        )}
      </div>
    </div>
  );
}
