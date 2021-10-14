package cwall.club.core.Service;

import cwall.club.common.Config.BaseConfig;
import cwall.club.common.DTO.ReportDTO;
import cwall.club.common.Exception.SalaryException;
import cwall.club.common.Item.Salary;
import cwall.club.common.Item.SignInfo;
import cwall.club.common.Item.UserContext;
import cwall.club.common.Item.UserInfo;
import cwall.club.common.VO.CompanyVO;
import cwall.club.common.VO.EmployeeInfoVO;
import cwall.club.common.VO.ProjectVO;
import cwall.club.common.VO.SignInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private BaseConfig baseConfig;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private SignInfoService signInfoService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private Drawer drawer;

    public String drawImage(String cid, int code, ReportDTO reportDTO, Long uid){
        String title = "未设置";
        String[][] data = new String[][]{{"0","0","0","0","0","0"}};
        UserInfo user = userInfoService.get(uid);
        EmployeeInfoVO employeeInfo = employeeService.getEmployeeInfo(Long.valueOf(cid), user.getId());
        List<CompanyVO> company = companyService.getCompany(Arrays.asList(Long.valueOf(cid)));
        if (company.size() == 0 || employeeInfo == null){
            throw new SalaryException(500, "数据异常,无法生成报告");
        }
        long start = reportDTO.getStartTime().getTime()/(1000*60*60*24);
        long end = reportDTO.getEndTime().getTime()/(1000*60*60*24);
        if (end < start){
            throw new SalaryException(500, "开始时间大于结束时间");
        }
        List<SignInfo> signInfos = signInfoService.get(employeeInfo.getId()).stream().filter(signInfo -> signInfo.getTime().getTime()/(1000*60*60*24)>=start && signInfo.getTime().getTime()/(1000*60*60*24)<=end).collect(Collectors.toList());
        switch (code){
            case 0:
                title = "Total work time";
                data = new String[][]{
                        {
                                "WorkTime","",signInfos.size()+" day","",signInfos.stream().mapToInt(SignInfo::getLen).sum()+" hour",""
                        },
                        {
                                "EmployeeInfo","",employeeInfo.getName(),"","",""
                        },
                        {
                                "CompanyInfo","",company.get(0).getName(),"",company.get(0).getDescription(),""
                        },
                        {
                                "StartTime   "+reportDTO.getStartTime().toString(),"","","","","EndTime   "+reportDTO.getEndTime().toString()
                        }
                };
                break;
            case 1:
                if (reportDTO.getPid() == null){
                    throw new SalaryException(500, "数据异常,无法生成报告");
                }
                signInfos = signInfos.stream().filter(signInfo -> signInfo.getPid() != null && signInfo.getPid().equals(Long.valueOf(reportDTO.getPid()))).collect(Collectors.toList());
                ProjectVO projectVO = projectService.get(Long.valueOf(reportDTO.getPid()));
                title = "Project work time " + projectVO.getId();
                data = new String[][]{
                        {
                                "WorkTime","",signInfos.size()+" day","",signInfos.stream().mapToDouble(SignInfo::getLen).sum()+" hour",""
                        },
                        {
                                "ProjectInfo","",projectVO.getName(),"",projectVO.getDescription(),""
                        },
                        {
                                "EmployeeInfo","",employeeInfo.getName(),"","",""
                        },
                        {
                                "CompanyInfo","",company.get(0).getName(),"",company.get(0).getDescription(),""
                        },
                        {
                                "StartTime   "+reportDTO.getStartTime().toString(),"","","","","EndTime   "+reportDTO.getEndTime().toString()
                        }
                };
                break;
            case 2:
                title = "RelaxInfo";
                data = new String[][]{
                        {
                                "RelaxTime","",end-start+1-signInfos.size()+" day","","",""
                        },
                        {
                                "EmployeeInfo","",employeeInfo.getName(),"","",""
                        },
                        {
                                "CompanyInfo","",company.get(0).getName(),"",company.get(0).getDescription(),""
                        },
                        {
                                "StartTime   "+reportDTO.getStartTime().toString(),"","","","","EndTime   "+reportDTO.getEndTime().toString()
                        }
                };
                break;
            case 3:
                List<Salary> salaries = salaryService.getSalary(start, end, employeeInfo.getId());
                data = new String[salaries.size()+4][6];
                for (int i=0;i<salaries.size();i++){
                    BigDecimal   b   =  new BigDecimal(salaries.get(i).getSalary());
                    double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
                    data[i] = new String[]{"SalaryInfo ","",f1+"","","SendTime  "+salaries.get(i).getTime().toString(),""};
                }
                data[data.length-1] = new String[]
                        {
                                "StartTime   "+reportDTO.getStartTime().toString(),"","","","","EndTime   "+reportDTO.getEndTime().toString()
                        };
                data[data.length-2] = new String[]
                        {
                                "CompanyInfo","",company.get(0).getName(),"",company.get(0).getDescription(),""
                        };
                data[data.length-3] = new String[]
                        {
                                "EmployeeInfo","",employeeInfo.getName(),"","",""
                        };

                BigDecimal   b   =  new BigDecimal(salaries.stream().mapToDouble(Salary::getSalary).sum());
                double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
                data[data.length-4] = new String[]
                        {
                                "TotalSalary","",f1+" yuan","","",""
                        };
                title = "Salary";
        }
        String name = cid +"_"+user.getId()+"_"+code+"_"+new Date(System.currentTimeMillis()).getTime();
        try {
            drawer.init(data, name,title);
            return baseConfig.getUrl() + name +".png";
        } catch (Exception e) {
            throw new SalaryException(500, "生成图片失败,请联系管理员");
        }
    }
}
@Component
class Drawer{
    @Autowired
    private FileService fileService;

    int fontTitleSize = 15;
    int row;
    int imageWidth = 1024;
    int rowHeight = 40;
    int maxLineLen = 50;
    int startHeight = 10;
    int startWidth = 10;
    Font font = new Font("微软雅黑",Font.BOLD,fontTitleSize);
    Font font2 = new Font("微软雅黑",Font.PLAIN,fontTitleSize);
    int[] lastWidth = new int[]{0,40,160,220,300,480};
    public Drawer(){
    }
    public void init(String[][] cellsValue,String name,String title) throws Exception {
        this.row = cellsValue.length;
        int remarkLoc = cellsValue[0].length - 1;
        int[] height = new int[row];
        int nowHeight = startHeight + rowHeight;
        List<Integer> lineHeights = new ArrayList<>();
        lineHeights.add(nowHeight);
        nowHeight += rowHeight;
        for (int j = 0; j < row; j++) {
            height[j] = nowHeight;
            int len = cellsValue[j][remarkLoc] == null ? 0 : cellsValue[j][remarkLoc].length();
            while (len > maxLineLen) {
                nowHeight += rowHeight;
                len -= maxLineLen;
            }
            lineHeights.add(nowHeight);
            nowHeight += rowHeight;
        }
        BufferedImage image = new BufferedImage(imageWidth, nowHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics(); //方法比较耗时
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, imageWidth, nowHeight);
        graphics.setColor(Color.black);
        for (int i : lineHeights) {
            graphics.drawLine(startWidth, i, startWidth + imageWidth - 20, i);
        }
        graphics.setFont(font);
        graphics.drawString(title, startWidth, startHeight + rowHeight - 10);
        for (int n = 0; n < cellsValue.length; n++) {
            for (int l = 0; l < cellsValue[n].length; l++) {
                if (cellsValue[n][l] == null) {
                    continue;
                }
                if (n == 0) {
                    graphics.setFont(font2);
                } else if (n > 0 && l > 0) {
                    graphics.setFont(font2);
                    graphics.setColor(Color.black);
                } else {
                    graphics.setFont(font2);
                    graphics.setColor(Color.BLACK);
                }
                if (l != 5) {
                    graphics.drawString(cellsValue[n][l], startWidth + lastWidth[l] + 5, height[n] - 10);
                } else {
                    int line = 0;
                    String str = cellsValue[n][l];
                    while (str != null && str.length() > maxLineLen) {
                        graphics.drawString(str.substring(line * maxLineLen, line * maxLineLen + maxLineLen), startWidth + lastWidth[l] + 5, height[n] + rowHeight * (line) - 10);
                        str = str.substring(++line * maxLineLen);
                    }
                    graphics.drawString(str, startWidth + lastWidth[l] + 5, height[n] + rowHeight * (line) - 10);
                }
            }
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        MultipartFile file = new MockMultipartFile("file", name + ".png", "text/plain", is);
        fileService.saveImage(file);
    }
}