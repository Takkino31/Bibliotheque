import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserManager
 */
public class UserManager {
    private Map<Integer, User> users;

    public UserManager() {
        this.users = new HashMap<>();
    }

    public void addUser(String name, int id, boolean isEligible) {
        User newUser = new User(name, id, isEligible);
        users.put(id, newUser);
        System.out.println("L'utilisateur \"" + name + "\" a été ajouté à la bibliothèque.");
    }


    public User findUser(int id) throws LibraryException.UserNotFoundException {
        User user = users.get(id);
        if (user == null) {
            throw new LibraryException.UserNotFoundException("Aucun utilisateur trouvé avec l'ID " + id + ".");
        }
        return user;
    }

    public void setUserEligibility(int id, boolean eligible) throws LibraryException.UserNotFoundException {
        User user = findUser(id);
        user.setEligible(eligible);
        System.out.println("L'utilisateur \"" + user.getName() + "\" a été marqué " + (eligible ? "comme éligible" : "comme non éligible") + " pour emprunter des livres.");
    }

    public void deleteUser(int id) throws LibraryException.UserNotFoundException {
        User user = findUser(id);
        users.remove(id);
        System.out.println("L'utilisateur \"" + user.getName() + "\" a été supprimé avec succès.");
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}

