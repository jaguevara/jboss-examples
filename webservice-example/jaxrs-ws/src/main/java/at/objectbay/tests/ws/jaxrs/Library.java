package at.objectbay.tests.ws.jaxrs;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.jboss.logging.Logger;

import at.objectbay.tests.ws.soap.FabrikBean;
import at.objectbay.tests.ws.soap.FabrikBeanService;
import at.objectbay.tests.ws.soap.Person;

@Path("/")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class Library {

    private static final Logger log = Logger.getLogger(Library.class);

    private static Map<String, Book> books = new LinkedHashMap<String, Book>();
    static {
        Book[] bookarr = new Book[] { 
                new Book("001", "The Judgment"), 
                new Book("002", "The Stoker"), 
                new Book("003", "Jackals and Arabs"), 
                new Book("004", "The Refusal") 
        };
        for (Book book : bookarr) {
            books.put(book.getIsbn(), book);
        }
    }

    @GET
    @Path("/books")
    public Collection<Book> getBooks() {
        Collection<Book> result = books.values();
        log.infof("getBooks: %s", result);
        return result;
    }

    @GET
    @Path("/book/{isbn}")
    public Book getBook(@PathParam("isbn") String id) {
        Book book = books.get(id);
        log.infof("getBook: %s", book);
        return book;
    }

    @PUT
    @Path("/book/{isbn}")
    public Book addBook(@PathParam("isbn") String id, @QueryParam("title") String title) {
        Book book = new Book(id, title);
        log.infof("addBook: %s", book);
        books.put(id, book);
        return book;
    }

    @POST
    @Path("/book/{isbn}")
    public Book updateBook(@PathParam("isbn") String id, String title) {
        Book book = books.get(id);
        if (book != null) {
            book.setTitle(title);
        }
        log.infof("updateBook: %s", book);
        return book;
    }

    @DELETE
    @Path("/book/{isbn}")
    public Book removeBook(@PathParam("isbn") String id) {
        Book book = books.remove(id);
        log.infof("removeBook: %s", book);
        return book;
    }

	@GET
	@Path("/person")
	public Person getPerson() {
		log.infof("getPerson");
		return new FabrikBeanService().getPort(FabrikBean.class).getPerson();
	}
}