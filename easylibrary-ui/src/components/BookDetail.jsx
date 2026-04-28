import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import apiClient from "../api/apiClient";

export default function BookDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [book, setBook] = useState(null);
  const [deleting, setDeleting] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    apiClient
      .get(`/books/${id}`)
      .then((res) => setBook(res.data))
      .catch((err) => console.error(err));
  }, [id]);

  const handleDelete = async () => {
    if (!window.confirm("Delete this book?")) return;

    setDeleting(true);
    setError(null);

    try {
      await apiClient.delete(`/books/${id}`);
      navigate("/", { replace: true });
    } catch (err) {
      setError("Could not delete the book. Please try again.");
      console.error(err);
    } finally {
      setDeleting(false);
    }
  };

  if (!book) return <p>Loading...</p>;

  return (
    <div className="book-detail">
      <h1>{book.title}</h1>
      <img className="book-detail-image" src={`/thumbs/${book.imageName}`} alt={book.title} />
      <p>
        <strong>Author:</strong> {book.author.name}
      </p>
      <p>
        <strong>Published:</strong> {book.publishedYear}
      </p>
      <button
        className="delete-book-button"
        type="button"
        onClick={handleDelete}
        disabled={deleting}
      >
        {deleting ? "Deleting..." : "Delete Book"}
      </button>
      {error && <p className="error-text">{error}</p>}
    </div>
  );
}
