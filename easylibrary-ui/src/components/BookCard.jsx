export default function BookCard({ book }) {
  return (
    <div className="book-card">
      <div className="image-container">
        <img src={book.imageUrl} alt={book.title} />
      </div>
      <span className="book-title">{book.title}</span>
    </div>
  );
}
