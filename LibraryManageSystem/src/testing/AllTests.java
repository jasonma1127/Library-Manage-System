package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ testUserRegister.class, testUserLogin.class, testAddBookItem.class, testSearchBookItem.class, testBorrowBooks.class, testReturnBooks.class, testShowBooksBorrowedByUser.class, testShowBookItems.class })
public class AllTests {

}
