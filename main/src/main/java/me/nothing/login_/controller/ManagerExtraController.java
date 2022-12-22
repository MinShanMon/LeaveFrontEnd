// package me.nothing.login_.controller;

// public class ManagerExtraController {
//     @Autowired
//     private ExtraHourService extraHourService;

//     @Autowired
//     private LeaveService leaveService;

//     @RequestMapping(value="/overtime/pending")
//     public String PendingApproval(Model model, Authentication auth, HttpSession session){
//         _StaffDetails usersession = (_StaffDetails) auth.getPrincipal();
//         List<Staff> subordinates = leaveService.getSubordinate(usersession.getStaff().getStfId());
//         Map<Staff, List<ExtraHour>> subordinateToExtMap =  new HashMap<>();
//         for(Staff subo: subordinates){
//             List<ExtraHour> extList = leaveService.getpe
//         }
//     }
// }
