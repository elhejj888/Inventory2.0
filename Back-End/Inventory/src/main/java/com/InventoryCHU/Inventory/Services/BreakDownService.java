    package com.InventoryCHU.Inventory.Services;

    import com.InventoryCHU.Inventory.FileManager.model.BreakDownReport;
    import com.InventoryCHU.Inventory.Models.BreakDown;
    import com.InventoryCHU.Inventory.Models.Item;
    import com.InventoryCHU.Inventory.Models.Software;
    import com.InventoryCHU.Inventory.Models.UserInfo;
    import com.InventoryCHU.Inventory.Repository.BreakDownRepository;
    import org.python.antlr.ast.Str;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Service;

    import java.time.LocalDateTime;
    import java.util.*;

    @Service
    public class BreakDownService {
        @Autowired
        BreakDownRepository breakDownRepository;

        @Autowired
        ItemService itemService;

        @Autowired
        SoftwareService softwareService;

        @Autowired
        UserInfoService userInfoService;

        public List<BreakDown> getAllBreakDowns(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            String userName = authentication.getName();
            Optional<UserInfo> user = userInfoService.getUser(userName);
            Optional<List<BreakDown>> breakDowns = null;
            for(GrantedAuthority authority : authorities){
                String role = authority.getAuthority();
                System.out.println(role);
                if(role.equals("Responsable Hardware") ){
                    return breakDownRepository.findAll();
                }
                else if(role.contains("Technicien")){
                    breakDowns = breakDownRepository.findBreakDownByTechnicianId((user.get().getId()));
                    Optional<List<BreakDown>> breakDowns1 = breakDownRepository.findByTechnicianIdIsNull();
                    breakDowns.get().addAll(breakDowns1.get());
                    return breakDowns.get();
                }
                else {
                    breakDowns = breakDownRepository.findAllByDeclaredBy((user.get().getId()));

                }
            }
            return breakDowns.get();


        }

        public Optional<BreakDown> getBreakDownById(String Id){
            return breakDownRepository.findById(Id);
        }

        public Object createBreakDown(BreakDown breakDown){
            Object item = itemService.fixItem(breakDown.itemId);
            Object software = softwareService.fixSoftware(breakDown.itemId);
            if(item instanceof Item ){
                Map<String , Object> rt = new HashMap<>();
                rt.put("item" , item);
                breakDownRepository.save(breakDown);
                rt.put("panne" , breakDown);
                return rt;
            }
            if(software instanceof Software ){
                Map<String, Object> rt = new HashMap<>();
                rt.put("Software",software);
                breakDownRepository.save(breakDown);
                rt.put("Panne" , breakDown);
                return rt;
            }
            return "Error Selecting the Item , please Insert the Item First..!";
        }

        public void deleteBreakDown(String Id){
            breakDownRepository.deleteById(Id);
        }

        public Optional<BreakDown> findBreakDownByItemId(String productId){ return breakDownRepository.findBreakDownByItemId(productId); }
        public Object assignBreakDown(String breakDown , int techId){
            Optional<BreakDown> OpBreakDown = breakDownRepository.findById(breakDown);
            if(OpBreakDown.isPresent()) {
                BreakDown breakDown1 = OpBreakDown.get();
                breakDown1.technicianId = techId;
                return breakDownRepository.save(breakDown1);
            }

            return "Erreur : Panne non Trouvé..!";
        }

        public Object breakDownFixed(String breakdown , BreakDownReport report){
            Optional<BreakDown> optionalBreakDown = breakDownRepository.findById(breakdown);
            if(optionalBreakDown.isPresent()){
                BreakDown breakDown = optionalBreakDown.get();
                LocalDateTime dt = LocalDateTime.now();
                breakDown.fixedAt=dt;
                breakDown.isFixed = true;
                breakDown.report = report.getReport();
                breakDown.remarque = report.getRemarque();
                return breakDownRepository.save(breakDown);
            }
            return "Erreur : Panne non Trouvé..!";
        }

        public Object breakDownDeclined(String breakdown , BreakDownReport report){
            Optional<BreakDown> bd = breakDownRepository.findById(breakdown);
            if(bd.isPresent()){
                BreakDown breakDown = bd.get();
                breakDown.isDeclined=true;
                breakDown.report = report.getReport();
                breakDown.remarque = report.getRemarque();
                return breakDownRepository.save(breakDown);
            }
            return "Erreur : Panne non Trouvé..!";

        }

        public int countBreakDownsByItems(String itemId){
            return breakDownRepository.countBreakDownByItemId(itemId);
        }

    }
