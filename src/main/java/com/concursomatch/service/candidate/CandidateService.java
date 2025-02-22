package com.concursomatch.service.candidate;

import com.concursomatch.domain.candidate.Candidate;
import com.concursomatch.domain.candidate.dto.CandidateDTO;
import com.concursomatch.domain.role.Role;
import com.concursomatch.repository.candidate.CandidateRepository;
import com.concursomatch.service.role.RoleService;
import com.concursomatch.util.assembler.CandidateAssembler;
import com.concursomatch.util.assembler.RoleAssembler;
import com.concursomatch.util.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RoleService roleService;

    public CandidateDTO create(CandidateDTO candidateDTO) {
        Set<Role> roles = RoleAssembler.toEntitySet(roleService.createRolesIfNotExists(candidateDTO.getRoles()));
        Candidate entity = candidateRepository.save(CandidateAssembler.toEntity(candidateDTO, roles));
        return CandidateAssembler.toDTO(entity);
    }

    public CandidateDTO findById(String id) {
        Candidate entity = candidateRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with ID: " + id));
        return CandidateAssembler.toDTO(entity);
    }

    public CandidateDTO update(CandidateDTO candidateDTO) {
        Candidate existingCandidate = candidateRepository.findById(UUID.fromString(candidateDTO.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with ID: " + candidateDTO.getId()));
        Set<Role> roles = RoleAssembler.toEntitySet(roleService.createRolesIfNotExists(candidateDTO.getRoles()));
        Candidate updatedCandidate = CandidateAssembler.toEntity(candidateDTO, roles);
        return CandidateAssembler.toDTO(candidateRepository.save(updatedCandidate));
    }

    public CandidateDTO deleteById(String id) {
        Candidate entity = candidateRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with ID: " + id));
        candidateRepository.delete(entity);
        return CandidateAssembler.toDTO(entity);
    }
}
