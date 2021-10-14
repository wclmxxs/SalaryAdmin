package cwall.club.common.Item;

import cwall.club.common.DTO.ProjectDTO;
import cwall.club.common.Util.ClassUtil;
import cwall.club.common.Util.IDUtil;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "project")
public class Project extends BaseItem{
    String name;
    String description;
    Long cid;

    public static Project copyFromDTO(ProjectDTO projectDTO) {
        Project project = new Project();
        ClassUtil.copyOneFromOne(project, projectDTO, project.getClass(), projectDTO.getClass());
        return project;
    }
}
