package org.acme.catalog.user.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.catalog.user.application.dto.UserDTO;
import org.acme.catalog.user.application.dto.usecases.CreateUserRequest;
import org.acme.catalog.user.domain.User;
import org.acme.catalog.user.infrastructure.mapper.UserMapper;
import org.acme.catalog.user.infrastructure.persistence.UserEntity;
import org.acme.catalog.user.infrastructure.persistence.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.ws.rs.NotFoundException;
import org.acme.catalog.user.application.dto.usecases.UpdateUserRequest;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(
            UserRepository userRepository,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDTO create(CreateUserRequest request) {

        if (userRepository.existsByDiscordId(request.discordId())) {
            throw new IllegalArgumentException(
                    "A user with this Discord ID already exists"
            );
        }

        User user = User.of(
                request.discordId(),
                request.username(),
                request.displayName(),
                LocalDateTime.now()
        );

        UserEntity entity = userMapper.toEntity(user);

        userRepository.persist(entity);

        return userMapper.toDTO(user);
    }

    public List<UserDTO> findAll() {

        return userRepository
                .listAll()
                .stream()
                .map(userMapper::toDomain)
                .map(userMapper::toDTO)
                .toList();
    }

    public UserDTO findByDiscordId(String discordId) {

        UserEntity entity = userRepository.findByDiscordId(discordId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "User not found: " + discordId
                        )
                );

        return userMapper.toDTO(
                userMapper.toDomain(entity)
        );



    }

    @Transactional
    public UserDTO update(
            String discordId,
            UpdateUserRequest request
    ) {

        UserEntity entity = userRepository.findByDiscordId(discordId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "User not found: " + discordId
                        )
                );

        entity.setUsername(request.username());
        entity.setDisplayName(request.displayName());

        User updatedUser = userMapper.toDomain(entity);

        return userMapper.toDTO(updatedUser);
    }


    @Transactional
    public void delete(String discordId) {

        UserEntity entity = userRepository.findByDiscordId(discordId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "User not found: " + discordId
                        )
                );

        userRepository.delete(entity);
    }

    @Transactional
    public void registerDiscordMember(
            String discordId,
            String username,
            String displayName
    ) {
        if (userRepository.existsByDiscordId(discordId)) {
            return;
        }

        CreateUserRequest request = new CreateUserRequest(
                discordId,
                username,
                displayName
        );

        create(request);
    }

}