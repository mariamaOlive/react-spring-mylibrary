import { useNavigate } from 'react-router-dom'

export default function BookCard({ book }) {
  const navigate = useNavigate()

  return (
    <div className="book-card">
      <div className="image-container"  onClick={() => navigate(`/book/${book.id}`)}>
        <img src={`/thumbs/${book.imageName}`} alt={book.title} />
      </div>
      <span className="book-title book-description">{book.title}</span>
      <span className="book-year book-description">{book.publishedYear}</span>
    </div>
  );
}
