package OMDB.OMDb_Liantis_Pinseel_Benjamin.services;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.UserCreateDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.dto.UserUpdateRequestDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.entities.User;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.exceptions.ResourceNotFoundException;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.mappers.UserMapper;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.repositories.UserRepository;
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

    public Page findAll(int page, int amountPerPage) {
        PageRequest pageable = PageRequest.of(
                page, amountPerPage
        );
        return userRepository.findAll(pageable);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public void save(UserCreateDto newUser) {

        User user = User.builder()
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .nickName(newUser.getNickName())
                .age(newUser.getAge())
                .email(newUser.getEmail())
                .build();
        userRepository.save(user);
    }

    public User update(String id, UserUpdateRequestDto newUserData) {
        User user = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with this ID was not found."));

        user.setFirstName(newUserData.getFirstName());
        user.setLastName(newUserData.getLastName());
        user.setNickName(newUserData.getNickName());
        user.setAge(newUserData.getAge());
        userRepository.save(user);
        return user;
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}
