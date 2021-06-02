//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    //REFACTOR ALL THE REPEATED LINES OF CODE
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
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime mockedCurrentTime = LocalTime.parse("08:00:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(mockedCurrentTime);
        assertFalse(restaurant.isRestaurantOpen());
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

    //Three tests for getTotalAmount. 1) Amount should not be negative - covered in method when_getTotalAmount_called_totalAmount_should_be_returned()
    //This first test case was used to develop the function getTotalAmount() using TDD
    //2) when_getTotalAmount_called_with_selected_menu_items_totalAmount_should_be_total_cost_of_all_items
    //3) when_getTotalAmount_called_with_zero_menu_items_totalAmount_should_be_zero

    @Test
    public void when_getTotalAmount_called_totalAmount_should_be_returned(){
        //Arrange
        menuCreationMethod();
        List<String> selectedItems = Collections.singletonList("Vegetable lasagne");
        //Act
        int totalAmount = restaurant.getTotalAmount(selectedItems);
        //Assert
        //Changed the assert to actually check if the value is not negative
        assertThat(totalAmount, greaterThan(0));
    }

    @Test
    public void when_getTotalAmount_called_with_selected_menu_items_totalAmount_should_be_total_cost_of_all_items(){
        //Arrange
        menuCreationMethod();
        List<String> selectedItems = Arrays.asList("Vegetable lasagne", "Sweet corn soup"); /*Just to check if it works in case the selected menu item's order is different*/
        //Act
        int totalAmount = restaurant.getTotalAmount(selectedItems);
        //Assert
        assertEquals(388, totalAmount);
    }

    @Test
    public void when_getTotalAmount_called_with_zero_menu_items_totalAmount_should_be_zero(){
        //Arrange
        menuCreationMethod();
        List<String> selectedItems = Collections.emptyList();
        //Act
        int totalAmount = restaurant.getTotalAmount(selectedItems);
        //Assert
        assertEquals(0, totalAmount);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}