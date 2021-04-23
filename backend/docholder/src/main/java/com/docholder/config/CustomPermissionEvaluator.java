package com.docholder.config;

import com.docholder.model.Document;
import com.docholder.model.DocumentDto;
import com.docholder.model.User;
import com.docholder.model.UserRole;
import com.docholder.repository.DocumentRepository;
import com.docholder.service.DocumentService;
import com.docholder.service.DocumentServiceImpl;
import com.docholder.utilities.DocumentSecurityTransfer;
import com.docholder.utilities.Jwt;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

// PreAuthorizate/PostAuthorizate verification
@Component
@RequiredArgsConstructor
public class CustomPermissionEvaluator implements PermissionEvaluator {
    private final DocumentService documentService;
    private final Jwt jwt;

    @Override
    public boolean hasPermission(Authentication auth, Object obj, Object permission) {

//        System.out.println("auth: "+auth);
//        System.out.println("obj: "+obj);
//        System.out.println("permission: "+permission);

        if ((auth == null) || (obj == null) || !(permission instanceof String)){
            System.out.println("haven't permission (CustomPermissionEvaluator())");
            return false;
        }

        if(permission.toString().equals("putDocument") || permission.toString().equals("updateDocument")){
            try {
                return modifyDocumentPermission( (DocumentSecurityTransfer) obj);
            } catch (Exception e){
                System.out.println("catch error during hasPermission() at modifyDocument");
                return false;
            }
        }

        if(permission.toString().equals("readDocument")){
            try {
                return readDocumentPermission( (DocumentSecurityTransfer) obj);
            } catch (Exception e){
                System.out.println("catch error during hasPermission() at readDocument");
                return false;
            }
        }

//        User user = (User) targetDomainObject;
//        if (user.getRole() != UserRole.ADMINISTRATOR) return false;

        return false;
    }

//    if user role equals document modify role or DIRECTOR or ADMINISTRATOR then return true
    private boolean modifyDocumentPermission(DocumentSecurityTransfer documentSecurityTransfer){
        DocumentDto documentDto = documentSecurityTransfer.getDocumentDto();
        String token = documentSecurityTransfer.getToken();

        if( !jwt.isValid(token) ) return false;
        Map<String, Object> user = jwt.getData(token);

        if( !user.get("company_id").equals(documentDto.getCompany_id()) ) return false;

        return user.get("role").equals(documentDto.getRole_modify().toString())
                || user.get("role").equals("DIRECTOR")
                || user.get("role").equals("ADMINISTRATOR");
    }

    //    if user role equals document read role or DIRECTOR or ADMINISTRATOR then return true
    private boolean readDocumentPermission(DocumentSecurityTransfer documentSecurityTransfer){
        UUID id = documentSecurityTransfer.getId();
        String token = documentSecurityTransfer.getToken();
        Document document = documentService.getOneDocumentInfo(id);

        if (document.getRole_read() == UserRole.REGULAR_UNEMPLOYED) return true;

        if( !jwt.isValid(token) ) return false;
        Map<String, Object> user = jwt.getData(token);

        if( !user.get("company_id").equals(document.getCompany_id()) ) return false;

        return user.get("role").equals(document.getRole_read().toString())
                || user.get("role").equals("DIRECTOR")
                || user.get("role").equals("ADMINISTRATOR");
    }




//    I don't sure for what this method, therefore always return false
    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission) {
        System.out.println("second hasPermission");
        return false;
    }

}