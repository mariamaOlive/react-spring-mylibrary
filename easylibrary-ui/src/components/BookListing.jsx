import BookCard from "./BookCard";

export default function BookListing({ books }) {
  return (
    <div className="book-listing">
      {books.length > 0 ? (
        <div className="book-grid">
          {books.map((book) => (
            <BookCard key={book.id} book={book} />
          ))}
        </div>
      ) : (
        <p>No books available.</p>
      )}
    </div>
  );
}
