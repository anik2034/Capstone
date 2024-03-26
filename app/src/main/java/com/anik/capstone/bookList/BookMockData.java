package com.anik.capstone.bookList;

import com.anik.capstone.model.BookModel;
import com.anik.capstone.model.Borrowing;
import com.anik.capstone.model.BorrowingStatus;
import com.anik.capstone.model.ReadingStatus;
import com.anik.capstone.model.rating.Rating;

import java.util.ArrayList;
import java.util.List;

public class BookMockData {
    public static List<BookModel> getLibraryBooks() {
        List<BookModel> books = new ArrayList<>();

        books.add(new BookModel("1234567890", "https://almabooks.com/wp-content/uploads/2016/10/9781847493699.jpg", "Jane Austen", "Pride and Prejudice", "Romance", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.BORROWED, "Monica Geller", "29/06/2002"), new Rating(3.5f, 4f, 3f, 4f, 3f)));
        books.add(new BookModel("1348934720", "https://images.penguinrandomhouse.com/cover/9781101884164", "Diana Gabaldon", "Outlander", "Historical Romance", ReadingStatus.IN_PROGRESS, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("2398109342", "https://nicholassparks.com/wp-content/uploads/2022/08/TheNotebook.jpg", "Nicholas Sparks", "The Notebook", "Contemporary Romance", ReadingStatus.PAUSED, new Borrowing(BorrowingStatus.BORROWED), new Rating(3.5f, 4f, 3f, 4f, 3f)));
        books.add(new BookModel("6789023333", "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1665087720i/17347634.jpg", "Jojo Moyes", "Me Before You", "Contemporary Romance", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED), new Rating(3.5f, 4f, 3f, 4f, 3f)));
        books.add(new BookModel("2398289233", "https://m.media-amazon.com/images/I/61RmfGsyCrL._AC_UF1000,1000_QL80_.jpg", "Charlotte Bronte", "Jane Eyre", "Classic Romance", ReadingStatus.NOT_STARTED, new Borrowing(BorrowingStatus.BORROWED, "Lana Del Ray", "02/03/2024")));
        books.add(new BookModel("9876543233", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRlcRggzCrkeKx5BPKuJrZVUX1PEv1yg-7EQlMWVW2LLA&s", "Margaret Mitchell", "Gone with the Wind", "Historical Romance", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED), new Rating(3.5f, 4f, 3f, 4f, 3f)));
        books.add(new BookModel("5678904321", "https://d28hgpri8am2if.cloudfront.net/book_images/onix/cvr9781476764832/the-time-travelers-wife-9781476764832_hr.jpg", "Audrey Niffenegger", "The Time Traveler's Wife", "Fantasy Romance", ReadingStatus.IN_PROGRESS, new Borrowing(BorrowingStatus.BORROWED), new Rating(3.5f, 4f, 3f, 4f, 3f)));
        books.add(new BookModel("9517535455", "https://m.media-amazon.com/images/I/717M42Ap2ML._AC_UF1000,1000_QL80_.jpg", "Rainbow Rowell", "Attachments", "Contemporary Romance", ReadingStatus.NOT_STARTED, new Borrowing(BorrowingStatus.NOT_BORROWED), new Rating(3.5f, 4f, 3f, 4f, 3f)));
        books.add(new BookModel("8529876542", "https://m.media-amazon.com/images/I/61fbVx3W5cL._AC_UF1000,1000_QL80_.jpg", "John Green", "The Fault in Our Stars", "Young Adult Romance", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.BORROWED), new Rating(3.5f, 4f, 3f, 4f, 3f)));
        books.add(new BookModel("1597536842", "https://zangakbookstore.am/uploads/images/products/336b07cc268b03dabb8066d16701f994.jpg", "André Aciman", "Call Me by Your Name", "Romance", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED), new Rating(3.5f, 4f, 3f, 4f, 3f)));

        return books;
    }

    public static List<BookModel> getWishlistBooks() {
        List<BookModel> books = new ArrayList<>();
        books.add(new BookModel("0853124578", "https://agathachristie.imgix.net/hcus-paperback/Jacket_AndThenThereWereNoneUS.jpg?auto=compress,format&fit=clip&q=65&w=400", "Agatha Christie", "And Then There Were None", "Mystery/Thriller", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("6448844434", "https://m.media-amazon.com/images/I/71-YShuOLdL._AC_UF894,1000_QL80_.jpg", "Arthur Conan Doyle", "The Adventures of Sherlock Holmes", "Mystery/Thriller", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("3698521477", "https://cdn.waterstones.com/bookjackets/large/9780/7538/9780753827666.jpg", "Gillian Flynn", "Gone Girl", "Thriller/Mystery", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("2585225852", "https://images-na.ssl-images-amazon.com/images/I/81UOA8fDGaL._AC_UL600_SR600,600_.jpg", "Stieg Larsson", "The Girl with the Dragon Tattoo", "Mystery/Thriller", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("4741474147", "https://m.media-amazon.com/images/I/71jDX01PzaL._AC_UF894,1000_QL80_.jpg", "Dan Brown", "The Da Vinci Code", "Mystery/Thriller", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("3696369636", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRUgXmbahM1bPPS1UD0npotDggur5gMMoLXulrqMHIiVw&s", "Tana French", "In the Woods", "Mystery/Thriller", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("7894561233", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJJCOcLhTX4TNBpRtFrKJCblTpaTfS6hIe6sTcREHgdw&s", "Gillian Flynn", "Sharp Objects", "Thriller/Mystery", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("9876543211", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_F0rehE9twks_QLDljTrtxtes_6mKA_FfC2HgWSbB5g&s", "Gone", "Lee Child", "Mystery/Thriller", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("0281726554", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4I6Ckv9gB_bcNuAUIZjDeM5anIpQKLTirHxqXOf2Ukg&s", "Tess Gerritsen", "The Surgeon", "Mystery/Thriller", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("1111111111", "https://m.media-amazon.com/images/I/51cLnFHabhS.jpg", "Thomas Harris", "The Silence of the Lambs", "Thriller", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));

        return books;
    }

    public static List<BookModel> getRecommendationsBooks() {
        List<BookModel> books = new ArrayList<>();
        books.add(new BookModel("9780547928227", "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1546071216l/5907.jpg", "J.R.R. Tolkien", "The Hobbit", "Fantasy", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("9780345538376", "https://images.booksense.com/images/689/381/9780553381689.jpg", "George R.R. Martin", "A Game of Thrones", "Fantasy", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("9780575075925", "https://m.media-amazon.com/images/I/91U6rc7u0yL._AC_UF1000,1000_QL80_.jpg", "Brandon Sanderson", "Mistborn: The Final Empire", "Fantasy", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("9780756404741", "https://images.penguinrandomhouse.com/cover/9780756413712", "Patrick Rothfuss", "The Name of the Wind", "Fantasy", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("9780553382563", "https://upload.wikimedia.org/wikipedia/en/0/00/WoT01_TheEyeOfTheWorld.jpg", "Robert Jordan", "The Eye of the World", "Fantasy", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("9780316038379", "https://m.media-amazon.com/images/I/816JhuO1cyS._AC_UF1000,1000_QL80_.jpg", "Leigh Bardugo", "Shadow and Bone", "Fantasy", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("9780316207586", "https://m.media-amazon.com/images/I/81WbBVgtVNL._AC_UF1000,1000_QL80_.jpg", "Marissa Meyer", "Cinder", "Fantasy", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("9780765331724", "https://m.media-amazon.com/images/I/91UDzcPH-nL._AC_UF1000,1000_QL80_.jpg", "Brandon Sanderson", "The Way of Kings", "Fantasy", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("9780575077172", "https://m.media-amazon.com/images/I/716LpMKQ3iL._AC_UF1000,1000_QL80_.jpg", "Neil Gaiman", "American Gods", "Fantasy", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));
        books.add(new BookModel("9780756404079", "https://m.media-amazon.com/images/I/81tbBNvLFKL._AC_UF894,1000_QL80_.jpg", "Patrick Rothfuss", "The Wise Man's Fear", "Fantasy", ReadingStatus.FINISHED, new Borrowing(BorrowingStatus.NOT_BORROWED)));

        return books;
    }
}
