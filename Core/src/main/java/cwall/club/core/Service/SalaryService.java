package cwall.club.core.Service;

import cwall.club.common.Exception.SalaryException;
import cwall.club.common.Item.EmployeeInfo;
import cwall.club.common.Item.Salary;
import cwall.club.common.Item.SignInfo;
import cwall.club.common.VO.OrderInfoVO;
import cwall.club.core.Repository.EmployeeRepository;
import cwall.club.core.Repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SignInfoService signInfoService;

    @Autowired
    private OrderService orderService;

    public List<Salary> getSalary(long start, long end, Long employeeId){
        return salaryRepository.findByEmployeeId(employeeId).stream().filter(salary -> salary.getTime().getTime()/(1000*60*60*24) >= start && salary.getTime().getTime()/(1000*60*60*24) <= end).collect(Collectors.toList());
    }

    public void sendHour() {
        List<EmployeeInfo> employeeInfos = employeeRepository.findByType('0');
        List<Salary> salaries = new ArrayList<>();
        for (EmployeeInfo employeeInfo: employeeInfos){
            Date date = new Date(System.currentTimeMillis());
            int hour = 0;
            List<Integer> collect = signInfoService.get(employeeInfo.getId()).stream().filter(signInfo -> signInfo.getTime().getTime()/(1000*60*60*24) >= date.getTime()/(1000*60*60*24) - 5 && signInfo.getTime().getTime()/(1000*60*60*24) <= date.getTime()/(1000*60*60*24)).map(signInfo -> signInfo.getLen()).collect(Collectors.toList());
            for (int i: collect){
                hour += i>8?((i-8)*1.5+8):i;
            }
            Salary salary = Salary.build(employeeInfo.getCid(), employeeInfo.getId(),
                    employeeInfo.getSalary() * hour * (1-employeeInfo.getTax()-employeeInfo.getOtherTax()),
                    employeeInfo.getPayType(),employeeInfo.getSocialCode());
            salaries.add(salary);
        }
        salaryRepository.saveAll(salaries);
        System.out.println("小时工发薪成功,发薪人数: "+employeeInfos.size());
    }

    public void send() {
        List<EmployeeInfo> employeeInfos = employeeRepository.findByTypeIn(Arrays.asList('1','2'));
        List<Salary> salaries = new ArrayList<>();
        for (EmployeeInfo employeeInfo: employeeInfos){
            Date date = new Date(System.currentTimeMillis());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int day = (int) signInfoService.get(employeeInfo.getId()).stream().filter(signInfo -> signInfo.getTime().getTime()/(1000*60*60*24) >= date.getTime()/(1000*60*60*24)-cal.get(Calendar.DAY_OF_MONTH) && signInfo.getTime().getTime()/(1000*60*60*24) <= date.getTime()/(1000*60*60*24)).count();
            double k = (day*1.0)/(getAttendance()*1.0);
            double sum = orderService.getEmployeeOrder(employeeInfo.getCid(), employeeInfo.getUid()).stream().filter(orderInfoVO -> orderInfoVO.getEndTime().getTime()/(1000*60*60*24) >= date.getTime()/(1000*60*60*24) - cal.get(Calendar.DAY_OF_MONTH) && orderInfoVO.getEndTime().getTime()/(1000*60*60*24) <= date.getTime()/(1000*60*60*24)).mapToDouble(OrderInfoVO::getPrice).sum();
            Salary salary = Salary.build(employeeInfo.getCid(), employeeInfo.getId(), (1-employeeInfo.getTax()-employeeInfo.getOtherTax())*(employeeInfo.getSalary() * k+sum * employeeInfo.getPercent()),employeeInfo.getPayType(),employeeInfo.getSocialCode());
            salaries.add(salary);
        }
        salaryRepository.saveAll(salaries);
        System.out.println("正式工发薪成功,发薪人数: "+employeeInfos.size());
    }

    public int getAttendance(){
        int count = 0;
        int UnWeek;
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String NowDate = df.format(date);

        String Days = NowDate.substring(8, 10);

        String item = Days.substring(0, 1);
        if(item.equals("0")){
            Days = Days.substring(1,2);
        }

        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = NowDate.substring(0,10);
        //String strDate = "2016-10-10";	//test by Jiro.Chen

        String item1 = strDate.substring(0, 8);
        String item2 = strDate.substring(8,10);

        for(int i= 1; i <= Integer.parseInt(item2); i++){
            strDate = item1 + String.valueOf(i);

            Date dDate = null;
            try {
                dDate = format1.parse(strDate);
            } catch (ParseException e) {
                throw new SalaryException(500,"计算错误");
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(dDate);
            if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                //System.out.println("YES");
                count++;
            }else{
                //System.out.println("NO");
                continue;
            }
        }

        UnWeek = Integer.parseInt(item2) - count;

        return UnWeek;
    }
}
