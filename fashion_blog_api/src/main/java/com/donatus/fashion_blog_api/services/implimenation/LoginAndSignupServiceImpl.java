package com.donatus.fashion_blog_api.services.implimenation;

import com.donatus.fashion_blog_api.dto.LoginDTO;
import com.donatus.fashion_blog_api.dto.SignupDTO;
import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.entity.Roles;
import com.donatus.fashion_blog_api.entity.UserEntity;
import com.donatus.fashion_blog_api.exception.EmailAlreadyExistException;
import com.donatus.fashion_blog_api.exception.InvalidEmailException;
import com.donatus.fashion_blog_api.exception.WrongPasswordException;
import com.donatus.fashion_blog_api.repository.UserRepository;
import com.donatus.fashion_blog_api.services.LoginAndSignupServices;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LoginAndSignupServiceImpl implements LoginAndSignupServices {
    private static boolean isFirst = true;
    private final Roles role = new Roles();
    private final ModelMapper mapper = new ModelMapper();

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(13);


    @Override
    public UserResponseDTO registerAdmin(SignupDTO admin) {
        if (userRepo.existsByEmail(admin.getEmail())){
            throw new EmailAlreadyExistException("Email already exist!");
        }

        if (!admin.getPassword().equals(admin.getConfirmPassword())){
            throw new WrongPasswordException("Password mismatch!");
        }

        // Encrypt password
        admin.setPassword(encoder.encode(admin.getPassword()));

        UserEntity newUser = mapper.map(admin, UserEntity.class);

        if (isFirst){
            role.setName("SUPER_ADMIN");
        }
        else {
            role.setName("USER");
        }

        newUser.setRoles(new ArrayList<>(List.of(role)));

        UserEntity savedAdmin = userRepo.save(newUser);

        return mapper.map(savedAdmin, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO loginAdmin(LoginDTO admin) {

        UserEntity userEntity = userRepo.findUserEntityByEmail(admin.getEmail())
                .orElseThrow(()-> new InvalidEmailException("Email not registered!"));

        if (!encoder.matches(admin.getPassword(), userEntity.getPassword())){
            throw new WrongPasswordException("Invalid password!");
        }

        return mapper.map(userEntity, UserResponseDTO.class);
    }

    @Override
    public void removeAdmin(Long adminId) {
        userRepo.deleteById(adminId);
    }
}
