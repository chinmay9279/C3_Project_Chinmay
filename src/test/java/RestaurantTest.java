//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    //REFACTOR ALL THE REPEATED LINES OF CODE
    //Major portion of the ARRANGE part has been refactored to reduce redundancy. That code is below
    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");
    Restaurant restaurant = spy(new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime));

    public void menuCreationMethod() {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //Arrange for a Mock Current Time
        LocalTime mockedCurrentTime = LocalTime.parse("13:00:00");

        //Arrange to use the mocked current time when getCurrentTime method is called
        Mockito.when(restaurant.getCurrentTime()).thenReturn(mockedCurrentTime);

        //Act & Assert to check if the restaurant isRestaurantOpen results in TRUE return value
        assertEquals(true, restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //Arrange for a Mock Current Time
        LocalTime mockedCurrentTime = LocalTime.parse("08:00:00");

        //Arrange to use the mocked current time when getCurrentTime method is called
        Mockito.when(restaurant.getCurrentTime()).thenReturn(mockedCurrentTime);

        //Act & Assert to check if the restaurant isRestaurantOpen results in FALSE return value
        assertEquals(false, restaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        menuCreationMethod();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        menuCreationMethod();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        menuCreationMethod();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    @Test
    public void when_getTotalAmount_called_totalAmount_should_be_returned(){
        //Create a mock list of selected Menu Items
        List<String> selectedItems = Arrays.asList("Sweet corn soup","Vegetable lasagne");
        //Find Total Amount by calling the method. Method is not implemented. Just returns 0.
        int totalAmount = restaurant.getTotalAmount(selectedItems);
        //Assert
        assertNotEquals(0, totalAmount);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}