import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UserRepository {

    private final Connection connection;

    private static final String SQL_SELECT_ALL_FROM_DRIVER = "SELECT * FROM driver";
    private static final String SQL_INSERT_INTO_DRIVER = "INSERT INTO driver VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM driver WHERE id = ?";
    private static final String SQL_UPDATE_DRIVER = "UPDATE driver SET first_name = ?, last_name = ?, age = ?, address = ?, car = ?, salary = ? WHERE id = ?";
    private static final String SQL_DELETE_DRIVER_BY_ID = "DELETE FROM driver WHERE id = ?";
    private static final String SQL_SELECT_ALL_BY_AGE = "SELECT * FROM driver WHERE age = ?";

    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER);

            List<User> result = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getString("address"),
                        resultSet.getString("car"),
                        resultSet.getInt("salary"));
                result.add(user);
            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User(
                        resultSet.getLong(1),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getString("address"),
                        resultSet.getString("car"),
                        resultSet.getInt("salary"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.empty();
    }

    @Override
    public void save(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_DRIVER);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setInt(4, user.getAge());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getCar());
            preparedStatement.setInt(7, user.getSalary());
            preparedStatement.execute();
            System.out.println("Новый пользователь добавлен!");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_DRIVER);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getCar());
            preparedStatement.setInt(6, user.getSalary());
            preparedStatement.setLong(7, user.getId());
            preparedStatement.executeUpdate();
            System.out.println("Пользователь обновлён!");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void remove(User user) {
        removeById(user.getId());
    }

    @Override
    public void removeById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_DRIVER_BY_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь удалён!");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAllByAge(Integer age) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_BY_AGE);
            preparedStatement.setInt(1, age);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> result = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong(1),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getString("address"),
                        resultSet.getString("car"),
                        resultSet.getInt("salary"));
                result.add(user);
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
