package cwall.club.core.Service;

import cwall.club.common.DTO.ProjectDTO;
import cwall.club.common.Exception.SalaryException;
import cwall.club.common.Item.Project;
import cwall.club.common.Util.IDUtil;
import cwall.club.common.VO.ProjectVO;
import cwall.club.core.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;


    public ProjectVO create(ProjectDTO projectDTO){
        Project project = Project.copyFromDTO(projectDTO);
        project.setId(IDUtil.generateID());
        projectRepository.save(project);
        return ProjectVO.copyFrom(project);
    }

    public List<ProjectVO> getAll(Long cid){
        return projectRepository.findByCid(cid).stream().map(project -> ProjectVO.copyFrom(project)).collect(Collectors.toList());
    }

    public ProjectVO get(Long id){
        Optional<Project> project = projectRepository.findById(id);
        if (!project.isPresent()){
            throw new SalaryException(500, "项目不存在");
        }
        return ProjectVO.copyFrom(project.get());
    }
}
