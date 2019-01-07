package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.dto.PatientProfileDto;
import app.model.dto.UpdatePasswordDto;
import app.model.dto.UpdateProfileDto;
import app.model.dto.UserUpdatedResponseDto;
import app.model.entity.Patient;
import app.model.security.User;
import app.repository.PatientRepository;
import app.security.JwtTokenUtil;
import app.security.JwtUser;
import app.security.repository.UserRepository;

@RestController
@RequestMapping("/me")
public class ProfileController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getProfileInfo() {

        JwtUser principalID = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userRepository.findById(principalID.getId()).get();
        Patient patient = patientRepository.findByUser(user);

        PatientProfileDto profileDto = PatientProfileDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .birthDate(patient.getBirthDate())
                .pesel(patient.getPesel())
                .phoneNumber(patient.getPhoneNumber())
                .build();

        return ResponseEntity.ok(profileDto);
    }

    // TODO: update na profilu się pierdoli, do rozkminki
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity updateProfileInfo(@RequestHeader("Authorization") String authorizationHeader,
                                            @RequestBody UpdateProfileDto updateProfileRequest) {

        JwtUser principalID = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userRepository.findById(principalID.getId()).get();

        String oldToken = authorizationHeader.split("Bearer ")[1];

        // First update account-related data
        // TODO: verify if username/email exist in DB before
        // TODO: same while registering!
        user.setUsername(updateProfileRequest.getUsername());
        user.setEmail(updateProfileRequest.getEmail());
        user.setFirstname(updateProfileRequest.getFirstName());
        user.setLastname(updateProfileRequest.getLastName());
        user = userRepository.save(user);

        // Then update patient data
        Patient patient = patientRepository.findByUser(user);
        patient.setPhoneNumber(updateProfileRequest.getPhoneNumber());
        patient.setUser(user);
        patientRepository.save(patient);

        // Change authentication data

        // Finally, return new token
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        String newToken = jwtTokenUtil.generateToken(userDetails);
        UserUpdatedResponseDto userUpdatedResponse = new UserUpdatedResponseDto(newToken);

        return ResponseEntity.ok(userUpdatedResponse);
    }

    // TODO: jakakolwiek weryfikacja hasla (dlugosc, znaki specjalne etc)?
    @RequestMapping(value = "/update/pass", method = RequestMethod.POST)
    public ResponseEntity updatePassword(@RequestBody UpdatePasswordDto updatePasswordRequest) {

        JwtUser principalID = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userRepository.findById(principalID.getId()).get();

        String encodedPassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return ResponseEntity.ok("Password update successful");
    }
}