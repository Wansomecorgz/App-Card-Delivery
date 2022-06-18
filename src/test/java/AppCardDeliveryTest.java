import com.codeborne.selenide.selector.ByText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldFillFormCorrectly() {
        $("[data-test-id='city'] input").val("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys("Backspace");
        String dateOfMeeting = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").setValue(dateOfMeeting);
        $("[data-test-id='name'] input").val("Родион Раскольников");
        $("[data-test-id='phone'] input").val("+79218776534");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(appear, Duration.ofSeconds(15));
        $("[data-test-id='notification']").shouldHave(text("Встреча успешно забронирована на " + dateOfMeeting));
        $x(".//button").click();
    }

    @Test
    public void shouldCheckIncorrectCity() {
        $("[data-test-id='city'] input").val("Минас-Тирит");
        $("[data-test-id='date'] input").doubleClick().sendKeys("Backspace");
        String dateOfMeeting = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").setValue(dateOfMeeting);
        $("[data-test-id='name'] input").val("Гэндальф Серый");
        $("[data-test-id='phone'] input").val("+79218776534");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='city']").shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldCheckIncorrectDate() {
        $("[data-test-id='city'] input").val("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys("Backspace");
        String dateOfMeeting = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").setValue(dateOfMeeting);
        $("[data-test-id='name'] input").val("Родион Раскольников");
        $("[data-test-id='phone'] input").val("+79218776534");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='date']").shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void shouldCheckIncorrectName() {
        $("[data-test-id='city'] input").val("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys("Backspace");
        String dateOfMeeting = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").setValue(dateOfMeeting);
        $("[data-test-id='name'] input").val("Aragorn son of Aratorn");
        $("[data-test-id='phone'] input").val("+79218776534");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='name']").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldCheckIncorrectPhone() {
        $("[data-test-id='city'] input").val("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys("Backspace");
        String dateOfMeeting = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").setValue(dateOfMeeting);
        $("[data-test-id='name'] input").val("Родион Раскольников");
        $("[data-test-id='phone'] input").val("89218776534");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='phone']").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldCheckCheckbox() {
        $("[data-test-id='city'] input").val("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys("Backspace");
        String dateOfMeeting = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").setValue(dateOfMeeting);
        $("[data-test-id='name'] input").val("Родион Раскольников");
        $("[data-test-id='phone'] input").val("+79218776534");
        //$("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='agreement']").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    public void shouldCheckEmptyCity() {
        $("[data-test-id='city'] input").val("");
        $("[data-test-id='date'] input").doubleClick().sendKeys("Backspace");
        String dateOfMeeting = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").setValue(dateOfMeeting);
        $("[data-test-id='name'] input").val("Гэндальф Серый");
        $("[data-test-id='phone'] input").val("+79218776534");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='city']").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldCheckEmptyName() {
        $("[data-test-id='city'] input").val("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys("Backspace");
        String dateOfMeeting = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").setValue(dateOfMeeting);
        $("[data-test-id='name'] input").val("");
        $("[data-test-id='phone'] input").val("+79218776534");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='name']").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldCheckEmptyPhone() {
        $("[data-test-id='city'] input").val("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys("Backspace");
        String dateOfMeeting = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").setValue(dateOfMeeting);
        $("[data-test-id='name'] input").val("Родион Раскольников");
        $("[data-test-id='phone'] input").val("");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='phone']").shouldHave(text("Поле обязательно для заполнения"));
    }
}
