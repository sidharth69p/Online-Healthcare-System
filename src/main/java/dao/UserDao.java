package dao;
import model.User;
import util.AppException;
public interface UserDao {
    User findByEmailAndPassword(String email, String password) throws AppException;
    User findById(int id) throws AppException;
}
