package cwall.club.common.VO;

import cwall.club.common.Item.Project;
import cwall.club.common.Util.ClassUtil;
import lombok.Data;

@Data
public class ProjectVO {
    String name;
    String description;
    Long cid;
    Long id;

    public static ProjectVO copyFrom(Project project) {
        ProjectVO projectVO = new ProjectVO();
        ClassUtil.copyOneFromOne(projectVO, project, projectVO.getClass(), project.getClass());
        projectVO.setId(project.getId());
        return projectVO;
    }
}
