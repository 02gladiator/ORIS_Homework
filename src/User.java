public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String address;
    private String car;
    private Integer salary;


    public User(Long id, String firstName, String lastName, Integer age, String address, String car, Integer salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
        this.car = car;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getCar() {
        return car;
    }

    public Integer getSalary() {
        return salary;
    }

}
