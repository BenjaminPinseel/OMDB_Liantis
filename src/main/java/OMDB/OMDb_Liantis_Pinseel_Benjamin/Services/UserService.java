package OMDB.OMDb_Liantis_Pinseel_Benjamin.Services;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.UserCreateDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.UserUpdateRequestDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Entities.User;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Exceptions.DataValidationException;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Exceptions.ResourceNotFoundException;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Helpers.Mapper;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Repositories.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

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
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserCreateDto>> violations = validator.validate(newUser);

        List<String> violationMessages = new ArrayList<>();
        for (ConstraintViolation<UserCreateDto> violation : violations) {
            violationMessages.add(violation.getMessage());
        }

        if (!violationMessages.isEmpty()) {
            throw new DataValidationException(violationMessages);
        } else {
            User user = mapper.mapUserCreateDtoToUser(newUser);
            userRepository.save(user);
        }
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
