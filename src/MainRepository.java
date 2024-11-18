import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class MainRepository {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123456";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/MyDataBase";
    private static final String sqlByCar = "select * from users where car = Haval";
    private static final String sqlBySalary = "select * from users where salary > 10000";
    private static final String sqlByAddress = "select * from users where address = Казань";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Scanner scanner = new Scanner(System.in);

        UserRepository userRepository = new UsersRepositoryJdbcImpl(connection);
        System.out.println("Сколько пользователей вы хотите добавить?");
        int countUsers = scanner.nextInt();
        for (int i = 0; i < countUsers; i++) {
            System.out.println("Введите id пользователя: ");
            Long id = scanner.nextLong();
            System.out.println("Введите имя пользователя: ");
            String firstName = scanner.next();
            System.out.println("Введите фамилию пользователя: ");
            String lastName = scanner.next();
            System.out.println("Введите возраст пользователя: ");
            int age = scanner.nextInt();
            System.out.println("Введите адрес пользователя: ");
            String address = scanner.next();
            System.out.println("Введите машину пользователя: ");
            String car = scanner.next();
            System.out.println("Введите зарплату пользователя: ");
            int salary = scanner.nextInt();
            User user = new User(id, firstName, lastName, age, address, car, salary);
            userRepository.save(user);
        }
        System.out.println("Добавлео " + countUsers + " пользователей");


        Statement statement = connection.createStatement();
        ResultSet resultByCar = statement.executeQuery(sqlByCar);

        while (resultByCar.next()) {
            System.out.println(resultByCar.getInt("id") + " " +
                    resultByCar.getString("name") + " " +
                    resultByCar.getString("car"));
        }

        ResultSet resultByAddress = statement.executeQuery(sqlByAddress);

        while (resultByAddress.next()) {
            System.out.println(resultByAddress.getInt("id") + " " +
                    resultByAddress.getString("name") + " " +
                    resultByAddress.getString("address"));
        }
        ResultSet resultBySalary = statement.executeQuery(sqlBySalary);
        while (resultBySalary.next()) {
            System.out.println(resultByAddress.getInt("id") + " " +
                    resultByAddress.getString("name") + " " +
                    resultByAddress.getString("salary"));

        }


    }
}
