package jpa.main;

import jpa.configuration.JPAConfig;
import jpa.entity.BookDetailsEntity;
import jpa.entity.BookEntity;
import jpa.entity.CategoryEntity;
import jpa.repository.BookCategory;
import jpa.repository.CategoryRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class MainTestBook {
    static ApplicationContext context =new AnnotationConfigApplicationContext(JPAConfig.class);
    static BookCategory bookCategory = (BookCategory) context.getBean("bookCategory");
    static CategoryRepository categoryRepository = (CategoryRepository) context.getBean("categoryRepository");


    public static void main(String[] args) {
        createNewBookEntryWithNewCategory();
        createNewBookEtry();
        findByAuthor("Roger");
        findByNameAndAuthor("linux", "Roger");
        findByNameOrAuthor("linux", "Roger");
        findByPriceLessThan(80);
        findByBookDetailsIsbn("ISIBF1219321");
        findByNameContaining("Nu");
    }


    public static void findByAuthor(String author){
        List<BookEntity> bookEntityList = bookCategory.findByAuthor(author);
        if(bookEntityList != null){
            System.out.println("\nFind " + bookEntityList.size() + " books which author = " + author);
            for(BookEntity bookEntity : bookEntityList){
                System.out.println(bookEntity.toString());
            }
        }
    }


    public static void findByNameAndAuthor(String name, String author){
        List<BookEntity> bookEntityList = bookCategory.findByNameAndAuthor(name, author);
        if(bookEntityList != null){
            System.out.println("\nFind " + bookEntityList.size() + " books which name = " + name + " and author = " + author);
            for(BookEntity bookEntity : bookEntityList){
                System.out.println(bookEntity.toString());
            }
        }
    }


    public static void findByNameOrAuthor(String name, String author){
        List<BookEntity> bookEntityList = bookCategory.findByNameOrAuthor(name, author);
        if(bookEntityList != null){
            System.out.println("\nFind " + bookEntityList.size() + " books which name = " + name + " or author = " + author);
            for(BookEntity bookEntity : bookEntityList){
                System.out.println(bookEntity.toString());
            }
        }
    }


    public static void findByPriceLessThan(int price){
        List<BookEntity> bookEntityList = bookCategory.findByBookDetailsPriceLessThan(price);
        if(bookEntityList != null){
            System.out.println("\nFind " + bookEntityList.size() + " books price less than = " + price);
            for(BookEntity bookEntity : bookEntityList){
                System.out.println(bookEntity.toString());
            }
        }
    }


    public static void findByNameContaining(String name){
        List<BookEntity> bookEntityList = bookCategory.findByNameContaining(name);
        if(bookEntityList != null){
            System.out.println("\nFind " + bookEntityList.size() + " books which containing name " + name);
            for(BookEntity bookEntity : bookEntityList){
                System.out.println(bookEntity.toString());
            }
        }
    }


    public static void findByUsingQuery(){
        List<BookEntity> bookEntityList = bookCategory.getAll();
        if(bookEntityList != null){
            System.out.println("\nFind " + bookEntityList.size() + " books");
            for(BookEntity bookEntity : bookEntityList){
                System.out.println(bookEntity.toString());
            }
        }
    }


    public static void findByBookDetailsIsbn(String isbn){
        BookEntity bookEntity = bookCategory.findByBookDetailsIsbn(isbn);
        if(bookEntity != null){
            System.out.println("\nFind book which isbn = " + isbn);
            System.out.println(bookEntity.toString());
        }
    }
    public static void createNewBookEtry(){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1);


        BookEntity bookEntity = createNewBook();
        bookEntity.setCategory(categoryEntity);
        bookCategory.save(bookEntity);
    }


    public static void createNewBookEntryWithNewCategory(){
        CategoryEntity categoryEntity = createNewCategory();
        categoryRepository.save(categoryEntity);


        BookEntity bookEntity = createNewBook();
        bookEntity.setCategory(categoryEntity);
        bookCategory.save(bookEntity);
    }


    private static CategoryEntity createNewCategory(){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("IT");
        categoryEntity.setDescription("IT books");
        return categoryEntity;
    }


    private static BookEntity createNewBook(){
        BookDetailsEntity bookDetailsEntity = new BookDetailsEntity();
        bookDetailsEntity.setIsbn("ISIBF1219321");
        bookDetailsEntity.setNumberOfPage(23);
        bookDetailsEntity.setPrice(65);
        bookDetailsEntity.setPublishDate(LocalDate.now());


        BookEntity bookEntity = new BookEntity();
        bookEntity.setName("Java A-Z");
        bookEntity.setAuthor("Roger");
        bookEntity.setBookDetails(bookDetailsEntity);
        bookDetailsEntity.setBook(bookEntity);


        return bookEntity;
    }
}
