package spring.cloud.converter;

import org.springframework.data.r2dbc.convert.EnumWriteSupport;
import spring.cloud.model.entity.component.ContactType;

public class EnumConverter {

    public static class ContactTypeEnumConverter extends EnumWriteSupport<ContactType> {

    }

}
