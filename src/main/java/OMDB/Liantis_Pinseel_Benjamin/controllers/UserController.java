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
import org.hibernate.sql.Update;
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


    @GetMapping()
    public PageDto<UserResponseDto> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int amountPerPage) {
        Page<User> userResult = this.userService.findAll(page, amountPerPage);
        return mapper.mapToPageDto(userResult);
    }

    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable String id) {
        User user = this.userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with this ID was not found."));
        return mapper.mapUserToUserResponseDto(user);

    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody @Valid UserCreateDto newUser) {
        this.userService.save(newUser);
    }

    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable String id, @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto) {
        User updatedUser = this.userService.update(id, userUpdateRequestDto);

        return mapper.mapUserToUserResponseDto(updatedUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        userService.deleteById(id);
    }

}
