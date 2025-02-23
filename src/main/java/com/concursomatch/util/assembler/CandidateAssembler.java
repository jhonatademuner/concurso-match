package com.concursomatch.util.assembler;

import com.concursomatch.domain.candidate.Candidate;
import com.concursomatch.domain.candidate.dto.CandidateDTO;
import com.concursomatch.domain.role.Role;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CandidateAssembler {

    public static Candidate toEntity(CandidateDTO dto, Set<Role> roles) {
        return Candidate.builder()
                .id(dto.getId() != null ? UUID.fromString(dto.getId()) : null)
                .name(dto.getName())
                .dateOfBirth(dto.getDateOfBirth())
                .citizenId(dto.getCitizenId())
                .roles(roles)
                .build();
    }

    public static CandidateDTO toDTO(Candidate candidate) {
        return CandidateDTO.builder()
                .id(candidate.getId() != null ? candidate.getId().toString() : null)
                .name(candidate.getName())
                .dateOfBirth(candidate.getDateOfBirth())
                .citizenId(candidate.getCitizenId())
                .roles(candidate.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static Set<Candidate> toEntitySet(Set<CandidateDTO> dtoSet, Set<Role> roles) {
        if (dtoSet == null) return Set.of();
        return dtoSet.stream()
                .map(dto -> toEntity(dto, roles))
                .collect(Collectors.toSet());
    }

    public static Set<CandidateDTO> toDTOSet(Set<Candidate> candidates) {
        if (candidates == null) return Set.of();
        return candidates.stream()
                .map(CandidateAssembler::toDTO)
                .collect(Collectors.toSet());
    }
}
