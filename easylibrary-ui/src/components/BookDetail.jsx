import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import apiClient from "../api/apiClient";

export default function BookDetail() {
  const { id } = useParams();
  const [book, setBook] = useState(null);

  useEffect(() => {
    apiClient
      .get(`/books/${id}`)
      .then((res) => setBook(res.data))
      .catch((err) => console.error(err));
  }, [id]);

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
    </div>
  );
}
