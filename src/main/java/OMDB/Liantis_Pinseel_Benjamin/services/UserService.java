package OMDB.Liantis_Pinseel_Benjamin.services;

import OMDB.Liantis_Pinseel_Benjamin.dto.UserCreateDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.UserUpdateRequestDto;
import OMDB.Liantis_Pinseel_Benjamin.entities.User;
import OMDB.Liantis_Pinseel_Benjamin.exceptions.ResourceNotFoundException;
import OMDB.Liantis_Pinseel_Benjamin.mappers.UserMapper;
import OMDB.Liantis_Pinseel_Benjamin.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    /**
     * Retrieves all users with pagination support.
     *
     * @param page          The page number to retrieve.
     * @param amountPerPage The amount of users per page.
     * @return Page containing a list of User entities.
     */
    public Page findAll(int page, int amountPerPage) {
        PageRequest pageable = PageRequest.of(page, amountPerPage);
        return userRepository.findAll(pageable);
    }

    /**
     * Finds a user by their ID.
     *
     * @param id The ID of the user to be retrieved.
     * @return Optional containing the User entity if found.
     */
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    /**
     * Saves a new user.
     *
     * @param newUser UserCreateDto containing the details of the new user.
     */
    public void save(UserCreateDto newUser) {
        // Mapping UserCreateDto to User entity and saving it to the repository.
        User user = User.builder()
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .nickName(newUser.getNickName())
                .age(newUser.getAge())
                .email(newUser.getEmail())
                .build();
        userRepository.save(user);
    }

    /**
     * Updates an existing user.
     *
     * @param id          The ID of the user to be updated.
     * @param newUserData UserUpdateRequestDto containing the updated user data.
     * @return Updated User entity.
     * @throws ResourceNotFoundException if the user is not found.
     */
    public User update(String id, UserUpdateRequestDto newUserData) {
        User user = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with this ID was not found."));

        // Updating the user entity with new data and saving it to the repository.
        user.setFirstName(newUserData.getFirstName());
        user.setLastName(newUserData.getLastName());
        user.setNickName(newUserData.getNickName());
        user.setAge(newUserData.getAge());
        user.setEmail(newUserData.getEmail());
        userRepository.save(user);
        return user;
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to be deleted.
     */
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}
