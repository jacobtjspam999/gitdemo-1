import java.util.*;

// Model Classes
class Student {
    private int id;
    private String name;
    private String gender;
    private int age;

    public Student(int id, String name, String gender, int age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}

class Address {
    private int id;
    private String city;
    private String state;
    private String country;
    private String pincode;

    public Address(int id, String city, String state, String country, String pincode) {
        this.id = id;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getPincode() {
        return pincode;
    }
}

// Services
interface StudentService {
    void addStudent(Student student);
    void updateStudent(Student student);
    void deleteStudent(int studentId);
    Student getStudent(int studentId);
}

class StudentServiceImpl implements StudentService {
    private Map<Integer, Student> students = new HashMap<>();

    @Override
    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }

    @Override
    public void updateStudent(Student student) {
        students.put(student.getId(), student);
    }

    @Override
    public void deleteStudent(int studentId) {
        students.remove(studentId);
    }

    @Override
    public Student getStudent(int studentId) {
        return students.get(studentId);
    }
}

interface AddressService {
    void addAddress(Address address);
    void updateAddress(Address address);
    void deleteAddress(int addressId);
    Address getAddress(int addressId);
}

class AddressServiceImpl implements AddressService {
    private Map<Integer, Address> addresses = new HashMap<>();

    @Override
    public void addAddress(Address address) {
        addresses.put(address.getId(), address);
    }

    @Override
    public void updateAddress(Address address) {
        addresses.put(address.getId(), address);
    }

    @Override
    public void deleteAddress(int addressId) {
        addresses.remove(addressId);
    }

    @Override
    public Address getAddress(int addressId) {
        return addresses.get(addressId);
    }
}

interface StudentAddressService {
    void addStudentAddress(int studentId, Address address);
    void updateStudentAddress(int studentId, Address address);
    void deleteStudentAddress(int studentId, int addressId);
    List<Address> getStudentAddresses(int studentId);
}

class StudentAddressServiceImpl implements StudentAddressService {
    private Map<Integer, List<Address>> studentAddresses = new HashMap<>();

    @Override
    public void addStudentAddress(int studentId, Address address) {
        studentAddresses.computeIfAbsent(studentId, k -> new ArrayList<>()).add(address);
    }

    @Override
    public void updateStudentAddress(int studentId, Address address) {
        List<Address> addresses = studentAddresses.get(studentId);
        if (addresses != null) {
            addresses.removeIf(a -> a.getId() == address.getId());
            addresses.add(address);
        }
    }

    @Override
    public void deleteStudentAddress(int studentId, int addressId) {
        List<Address> addresses = studentAddresses.get(studentId);
        if (addresses != null) {
            addresses.removeIf(a -> a.getId() == addressId);
        }
    }

    @Override
    public List<Address> getStudentAddresses(int studentId) {
        return studentAddresses.getOrDefault(studentId, new ArrayList<>());
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {
        StudentService studentService = new StudentServiceImpl();
        AddressService addressService = new AddressServiceImpl();
        StudentAddressService studentAddressService = new StudentAddressServiceImpl();

        // Add students
        Student student1 = new Student(1, "John Doe", "Male", 20);
        studentService.addStudent(student1);

        // Add addresses
        Address address1 = new Address(1, "New York", "NY", "USA", "10001");
        Address address2 = new Address(2, "Los Angeles", "CA", "USA", "90001");

        addressService.addAddress(address1);
        addressService.addAddress(address2);

        // Link addresses to students
        studentAddressService.addStudentAddress(1, address1);
        studentAddressService.addStudentAddress(1, address2);

        // Display student details
        System.out.println("Student: " + studentService.getStudent(1).getName());
        System.out.println("Addresses:");
        for (Address address : studentAddressService.getStudentAddresses(1)) {
            System.out.println(address.getCity() + ", " + address.getState() + ", " + address.getCountry());
        }
    }
}
