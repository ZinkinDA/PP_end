package WebSite.service;

import WebSite.model.Role;
import WebSite.model.User;
import WebSite.repository.RoleDao;
import WebSite.repository.UserDao;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleService roleRepository;

    public UserServiceImpl(UserDao userRepository, @Lazy PasswordEncoder bCryptPasswordEncoder, RoleService roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;

        /** Создание и созранение ролей **/
        Role user = new Role("ROLE_USER");
        Role admin = new Role("ROLE_ADMIN");

        roleRepository.save(user);
        roleRepository.save(admin);

        /** Создание пользователей **/

        // Пароль в BCrypt для 1 пользователя $2a$12$OOBMFFxKfPclYE.fK7agveJbAohzJuMj9p1HH58j28HVVdRy22rNS

        User user1 = new User("yashtlsht@mail.ru", "123");
        User user2 = new User("Alex@mail.ru","234");
        User user3 = new User("Irina@mail.ru", "345");
        user1.setFirstName("Дмитрий");
        user1.setLastName("Зинкин");

        user2.setFirstName("Алексей");
        user2.setLastName("Зинкин");

        user3.setFirstName("Ирина");
        user3.setLastName("Ручкина");

        /** Установка им ролей **/
        user1.setRole(roleRepository.findByName("ROLE_ADMIN"));
        user1.setRole(roleRepository.findByName("ROLE_USER"));
        user2.setRole(roleRepository.findByName("ROLE_ADMIN"));
        user3.setRole(roleRepository.findByName("ROLE_USER"));

        /** Сохранение пользователей **/
        saveUser(user1);
        saveUser(user2);
        saveUser(user3);

    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void saveUser(User user) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.saveAndFlush(user);
    }

    @Transactional
    public void updateUser(User user) {
        if (!user.getPassword().equals(Objects.requireNonNull(userRepository.findById(user.getId()).orElse(null)).getPassword())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }else {
            user.setPassword(Objects.requireNonNull(userRepository.findById(user.getId()).orElse(null)).getPassword());
        }

        userRepository.saveAndFlush(user);
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

}
