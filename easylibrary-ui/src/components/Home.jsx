import { useEffect, useState } from "react";
// import defaultBooks from "../data/books";

import BookListing from "./BookListing";
import apiClient from "../api/apiClient";

export default function Home() {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const response = await apiClient.get("/books");
        setBooks(response.data);
      } catch (error) {
        console.error("Error fetching books:", error);
        setError(
          error.response?.data?.message ||
            "Failed to fetch books. Please try again.",
        );
      } finally {
        setLoading(false);
      }
    };
    fetchBooks();
  }, []);

  if (loading) {
    return (
      <div className="loading-container">
        <span>Loading products...</span>
      </div>
    );
  }

  if (error) {
    return (
      <div className="error-container">
        <span>Error: {error}</span>
      </div>
    );
  }

  return (
    <div className="home">
      <BookListing books={books} />
    </div>
  );
}
