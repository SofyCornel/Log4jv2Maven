package quiz.model;

import com.opencsv.bean.CsvBindByName;
import org.example.Constants;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Objects;
@XmlRootElement
public class Quiz {
    @CsvBindByName(column = Constants.ID)
    long id;
    @CsvBindByName(column = Constants.GUEST)
    String guest;
    @CsvBindByName(column = Constants.PERSONAL)
    String personal;

    public Quiz(long id, String guest, String personal) {
        this.id = id;
        this.guest = guest;
        this.personal = personal;
    }

    public Quiz(String guest, String personal) {
        Date date = new Date();
        this.id = date.getTime();
        this.guest = guest;
        this.personal = personal;
    }

    public Quiz() {
    }

    public Quiz(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", guest='" + guest + '\'' +
                ", personal='" + personal + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return id == quiz.id && Objects.equals(guest, quiz.guest) && Objects.equals(personal, quiz.personal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, guest, personal);
    }
}
