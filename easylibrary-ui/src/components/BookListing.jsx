import { useState, useMemo } from "react";
import BookCard from "./BookCard";
import Dropdown from "./Dropdown";
import SearchBox from "./SearchBox";

export default function BookListing({ books }) {
  const [searchText, setSearchText] = useState("");
  const [sortOption, setSortOption] = useState("Title");


  const filteredAndSortedBooks = useMemo(() => {
    //Verify if theres a books
    if(!Array.isArray(books)) {
      return [];
    }

    //Filter the books based on the search text
    let filteredBooks = books.filter((book) => {
      const searchLower = searchText.toLowerCase();
      return (
        book.title.toLowerCase().includes(searchLower) ||
        book.author.name.toLowerCase().includes(searchLower)
      );
    });

    return filteredBooks.sort((a, b) => {
      if (sortOption === "Title") {
        return a.title.localeCompare(b.title);
      } else if (sortOption === "Author") {
        return a.author.name.localeCompare(b.author.name);
      } else if (sortOption === "Publication Date") {
        return new Date(a.publishedYear) - new Date(b.publishedYear);
      }
      return 0;
    });
  
  }, [books, searchText, sortOption]);


  const handleSearchChange = (value) => {
    setSearchText(value);
  }

  const handleSortChange = (value) => {
    setSortOption(value);
  }


  return (
    <div>
      <div className="top-book-listing">
        <SearchBox
          label="Search Books "
          placeholder="Search by title or author"
          value={searchText}
          handleSearch={(value) => handleSearchChange(value)} 
        />
        <Dropdown
          label="Sort By "
          options={["Title", "Author", "Publication Date"]}
          selectedValue={sortOption}
          handleSort={(value) => handleSortChange(value)}
        />
      </div>
      <div className="book-listing">
        {filteredAndSortedBooks.length > 0 ? (
          <div className="book-grid">
            {filteredAndSortedBooks.map((book) => (
              <BookCard key={book.id} book={book} />
            ))}
          </div>
        ) : (
          <p>No books available.</p>
        )}
      </div>
    </div>
  );
}
