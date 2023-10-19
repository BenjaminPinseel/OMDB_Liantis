package OMDB.OMDb_Liantis_Pinseel_Benjamin.Controllers;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.UserCreateDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.UserResponseDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.UserUpdateRequestDto;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Entities.User;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Exceptions.ResourceNotFoundException;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Helpers.EncryptionUtils;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Helpers.Mapper;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto.PageDto;

@RestController
@RequestMapping(value="/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Mapper mapper;
    private final EncryptionUtils encryptionUtils;

    @Value("${encrypted.api.key}")
    private String encryptedApiKey;


    @GetMapping("/getall")
    public PageDto<UserResponseDto> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int amountPerPage)
    {
      Page<User> userResult = this.userService.findAll(page,amountPerPage);
      return mapper.mapToPageDto(userResult);
    }
    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable String id){
        User user = this.userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with this ID was not found."));
        return mapper.mapUserToUserResponseDto(user);

    }
    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void post(@RequestBody UserCreateDto newUser){
        this.userService.save(newUser);
    }

    @PutMapping()
    public UserResponseDto put(@PathVariable String id, @RequestBody UserUpdateRequestDto userUpdateRequestDto)
    {
        User updatedUser = this.userService.update(id, userUpdateRequestDto);

        return mapper.mapUserToUserResponseDto(updatedUser);
    }
    @DeleteMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable String id){
        userService.deleteById(id);
    }

}
