package OMDB.Liantis_Pinseel_Benjamin.controllers;

import OMDB.Liantis_Pinseel_Benjamin.dto.PageDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.UserCreateDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.UserResponseDto;
import OMDB.Liantis_Pinseel_Benjamin.dto.UserUpdateRequestDto;
import OMDB.Liantis_Pinseel_Benjamin.entities.User;
import OMDB.Liantis_Pinseel_Benjamin.exceptions.ResourceNotFoundException;
import OMDB.Liantis_Pinseel_Benjamin.helpers.EncryptionUtils;
import OMDB.Liantis_Pinseel_Benjamin.mappers.UserMapper;
import OMDB.Liantis_Pinseel_Benjamin.services.UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserController {
    private final UserService userService;
    private final UserMapper mapper;
    private final EncryptionUtils encryptionUtils;

    /**
     * Retrieve a paginated list of users.
     *
     * @param page          The page number for pagination (default value: 0).
     * @param amountPerPage The number of users per page (default value: 10).
     * @return PageDto<UserResponseDto> containing a list of users.
     */
    @GetMapping()
    public PageDto<UserResponseDto> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int amountPerPage) {
        Page<User> userResult = this.userService.findAll(page, amountPerPage);
        return mapper.mapToPageDto(userResult);
    }

    /**
     * Retrieve a user by its unique identifier.
     *
     * @param id The unique identifier of the user.
     * @return UserResponseDto containing information about the user.
     * @throws ResourceNotFoundException if the user with the specified ID is not found.
     */
    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable String id) {
        User user = this.userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with this ID was not found."));
        return mapper.mapUserToUserResponseDto(user);

    }

    /**
     * Create a new user.
     *
     * @param newUser The UserCreateDto containing information for the new user.
     */
    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody @Valid UserCreateDto newUser) {
        this.userService.save(newUser);
    }

    /**
     * Update an existing user.
     *
     * @param id                   The unique identifier of the user to be updated.
     * @param userUpdateRequestDto The UserUpdateRequestDto containing updated information for the user.
     * @return UserResponseDto containing information about the updated user.
     */
    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable String id, @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto) {
        User updatedUser = this.userService.update(id, userUpdateRequestDto);

        return mapper.mapUserToUserResponseDto(updatedUser);
    }

    /**
     * Delete a user by its unique identifier.
     *
     * @param id The unique identifier of the user to be deleted.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        userService.deleteById(id);
    }

}
